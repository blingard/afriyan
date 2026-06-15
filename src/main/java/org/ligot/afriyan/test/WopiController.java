package org.ligot.afriyan.test;

import org.ligot.afriyan.service.MinioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("public/wopi/files")
public class WopiController {
    private final MinioService minioService;
    private final WopiTokenService tokenService;
    private final DocumentRepository documentRepository;

    /**
     * Lock store en mémoire : fileId → lockId.
     * En production, utiliser Redis ou la BDD pour persister les locks.
     */
    private final ConcurrentHashMap<String, String> lockStore = new ConcurrentHashMap<>();

    public WopiController(MinioService minioService, WopiTokenService tokenService,
            DocumentRepository documentRepository) {
        this.minioService = minioService;
        this.tokenService = tokenService;
        this.documentRepository = documentRepository;
    }

    // ① Collabora demande les infos du fichier
    @GetMapping("/{fileId}")
    public ResponseEntity<Map<String, Object>> checkFileInfo(
            @PathVariable String fileId,
            @RequestParam String access_token) {

        WopiToken token = tokenService.validate(access_token);
        Document doc = documentRepository.findById(fileId).orElseThrow();

        return ResponseEntity.ok(Map.of(
                "BaseFileName", doc.getNom(),
                "Size", doc.getTaille(),
                "Version", String.valueOf(doc.getVersion()),
                "OwnerId", doc.getProprietaireId(),
                "UserId", token.getUserId(),
                "UserFriendlyName", token.getUserNom(),
                "UserCanWrite", token.canWrite(),
                "SupportsUpdate", true,
                "SupportsLocks", true,
                "UserCanNotWriteRelative", true));
    }

    // ② Collabora télécharge le fichier
    @GetMapping("/{fileId}/contents")
    public ResponseEntity<byte[]> getContents(
            @PathVariable String fileId,
            @RequestParam String access_token) throws Exception {

        tokenService.validate(access_token);

        // Récupérer le document en BD pour obtenir l'extension
        Document doc = documentRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("Document non trouvé: " + fileId));

        // La clé MinIO inclut l'extension (ex: uuid.docx)
        String minioKey = fileId + "." + doc.getExtension();
        byte[] content = minioService.getFileTest(minioKey);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
    }

    // ③ Collabora sauvegarde les modifications (PutFile)
    @PostMapping("/{fileId}/contents")
    public ResponseEntity<Map<String, Object>> saveContents(
            @PathVariable String fileId,
            @RequestParam String access_token,
            @RequestHeader(value = "X-WOPI-Override", required = false) String override,
            @RequestHeader(value = "X-WOPI-Lock", required = false) String lockId,
            @RequestBody byte[] content) throws Exception {

        System.out.println("[WOPI] PutFile pour fileId=" + fileId
                + " override=" + override + " lockId=" + lockId
                + " taille=" + content.length + " octets");

        WopiToken token = tokenService.validate(access_token);
        if (!token.canWrite()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Vérifier le lock si un lock existe sur le fichier
        String currentLock = lockStore.get(fileId);
        if (currentLock != null && lockId != null && !currentLock.equals(lockId)) {
            System.out.println("[WOPI] PutFile CONFLIT : lock attendu=" + currentLock + " reçu=" + lockId);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("X-WOPI-Lock", currentLock)
                    .build();
        }

        Document document = documentRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("Document non trouvé: " + fileId));

        minioService.saveFileTest(fileId + "." + document.getExtension(), content);

        // Mettre à jour la taille et version en base
        documentRepository.updateTailleEtVersion(fileId, content.length);

        System.out.println("[WOPI] PutFile OK pour fileId=" + fileId + " nouvelle taille=" + content.length);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        return ResponseEntity.ok(response);
    }

    // ④ Collabora gère les locks : LOCK, GET_LOCK, REFRESH_LOCK, UNLOCK
    @PostMapping("/{fileId}")
    public ResponseEntity<Map<String, Object>> handleLockOperations(
            @PathVariable String fileId,
            @RequestHeader(value = "X-WOPI-Override", defaultValue = "") String override,
            @RequestHeader(value = "X-WOPI-Lock", required = false) String lockId,
            @RequestHeader(value = "X-WOPI-OldLock", required = false) String oldLockId,
            @RequestParam String access_token) {

        System.out.println("[WOPI] Operation=" + override + " fileId=" + fileId
                + " lockId=" + lockId + " oldLockId=" + oldLockId);

        tokenService.validate(access_token);

        switch (override) {
            case "LOCK":
                return handleLock(fileId, lockId, oldLockId);
            case "GET_LOCK":
                return handleGetLock(fileId);
            case "REFRESH_LOCK":
                return handleRefreshLock(fileId, lockId);
            case "UNLOCK":
                return handleUnlock(fileId, lockId);
            default:
                System.out.println("[WOPI] Opération non gérée : " + override);
                return ResponseEntity.ok(Map.of("status", "ok"));
        }
    }

    /**
     * LOCK : Verrouille le fichier.
     * Si oldLockId est présent, c'est un UnlockAndRelock.
     */
    private ResponseEntity<Map<String, Object>> handleLock(String fileId, String lockId, String oldLockId) {
        String currentLock = lockStore.get(fileId);

        // Si le fichier est déjà verrouillé
        if (currentLock != null) {
            // Si le lock correspond, c'est un relock → on renouvelle
            if (currentLock.equals(lockId)) {
                System.out.println("[WOPI] LOCK renouvellement OK pour fileId=" + fileId);
                return ResponseEntity.ok().build();
            }
            // UnlockAndRelock : vérifier l'ancien lock
            if (oldLockId != null && currentLock.equals(oldLockId)) {
                lockStore.put(fileId, lockId);
                System.out.println("[WOPI] UnlockAndRelock OK pour fileId=" + fileId + " nouveau lock=" + lockId);
                return ResponseEntity.ok().build();
            }
            // Conflit : le fichier est verrouillé par un autre lock
            System.out.println("[WOPI] LOCK CONFLIT pour fileId=" + fileId
                    + " existant=" + currentLock + " demandé=" + lockId);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("X-WOPI-Lock", currentLock)
                    .body(Map.of("Lock", currentLock));
        }

        // Le fichier n'est pas verrouillé → on le verrouille
        lockStore.put(fileId, lockId);
        System.out.println("[WOPI] LOCK OK pour fileId=" + fileId + " lockId=" + lockId);
        return ResponseEntity.ok().build();
    }

    /**
     * GET_LOCK : Retourne le lock actuel du fichier.
     */
    private ResponseEntity<Map<String, Object>> handleGetLock(String fileId) {
        String currentLock = lockStore.getOrDefault(fileId, "");
        System.out.println("[WOPI] GET_LOCK pour fileId=" + fileId + " lock=" + currentLock);
        return ResponseEntity.ok()
                .header("X-WOPI-Lock", currentLock)
                .build();
    }

    /**
     * REFRESH_LOCK : Prolonge la durée du lock.
     */
    private ResponseEntity<Map<String, Object>> handleRefreshLock(String fileId, String lockId) {
        String currentLock = lockStore.get(fileId);

        if (currentLock == null) {
            // Pas de lock → on accepte quand même (tolérance)
            System.out.println("[WOPI] REFRESH_LOCK : pas de lock existant pour fileId=" + fileId + ", on accepte");
            lockStore.put(fileId, lockId);
            return ResponseEntity.ok().build();
        }

        if (!currentLock.equals(lockId)) {
            System.out.println("[WOPI] REFRESH_LOCK CONFLIT pour fileId=" + fileId
                    + " existant=" + currentLock + " demandé=" + lockId);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("X-WOPI-Lock", currentLock)
                    .body(Map.of("Lock", currentLock));
        }

        // Lock correspond → renouvellement réussi
        System.out.println("[WOPI] REFRESH_LOCK OK pour fileId=" + fileId);
        return ResponseEntity.ok().build();
    }

    /**
     * UNLOCK : Déverrouille le fichier.
     */
    private ResponseEntity<Map<String, Object>> handleUnlock(String fileId, String lockId) {
        String currentLock = lockStore.get(fileId);

        if (currentLock == null) {
            // Pas de lock → on accepte (tolérance)
            System.out.println("[WOPI] UNLOCK : pas de lock existant pour fileId=" + fileId + ", OK");
            return ResponseEntity.ok()
                    .header("X-WOPI-Lock", "")
                    .build();
        }

        if (!currentLock.equals(lockId)) {
            System.out.println("[WOPI] UNLOCK CONFLIT pour fileId=" + fileId
                    + " existant=" + currentLock + " demandé=" + lockId);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("X-WOPI-Lock", currentLock)
                    .body(Map.of("Lock", currentLock));
        }

        lockStore.remove(fileId);
        System.out.println("[WOPI] UNLOCK OK pour fileId=" + fileId);
        return ResponseEntity.ok()
                .header("X-WOPI-Lock", "")
                .build();
    }
}

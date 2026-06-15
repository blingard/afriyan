package org.ligot.afriyan.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller pour la génération de bulletins scolaires.
 *
 * POST /public/bulletins/generate
 *   Body JSON : { "templateId": "...", "eleveId": "...", "trimestre": 1 }
 *   → Génère un bulletin et retourne l'ID du document créé
 */
@RestController
@RequestMapping("public/bulletins")
public class BulletinController {

    private final BulletinGenerationService bulletinService;

    public BulletinController(BulletinGenerationService bulletinService) {
        this.bulletinService = bulletinService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateBulletin(
            @RequestBody Map<String, Object> body) {
        try {
            String templateId = (String) body.get("templateId");
            String eleveId = (String) body.get("eleveId");
            int trimestre = body.get("trimestre") instanceof Number
                    ? ((Number) body.get("trimestre")).intValue()
                    : Integer.parseInt(body.get("trimestre").toString());

            if (templateId == null || eleveId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "templateId et eleveId sont requis"));
            }

            String bulletinId = bulletinService.generateBulletin(templateId, eleveId, trimestre);

            return ResponseEntity.ok(Map.of(
                    "status", "generated",
                    "bulletinId", bulletinId
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

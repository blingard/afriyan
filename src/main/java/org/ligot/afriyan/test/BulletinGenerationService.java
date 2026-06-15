package org.ligot.afriyan.test;

import org.apache.poi.xwpf.usermodel.*;
import org.ligot.afriyan.service.MinioService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service de génération de bulletins scolaires.
 *
 * Prend un template .docx (stocké dans MinIO), remplace les placeholders
 * par les données de l'élève et génère dynamiquement le tableau des notes.
 *
 * Placeholders supportés dans le template :
 *   {{NOM_ELEVE}}, {{PRENOM_ELEVE}}, {{MATRICULE}}, {{CLASSE}},
 *   {{DATE_NAISSANCE}}, {{SEXE}}, {{ANNEE_SCOLAIRE}}, {{TRIMESTRE}},
 *   {{MOYENNE_GENERALE}}, {{NOTES_TABLE}}
 *
 * Le placeholder {{NOTES_TABLE}} sera remplacé par un tableau complet.
 */
@Service
public class BulletinGenerationService {

    private final MinioService minioService;
    private final EleveRepository eleveRepository;
    private final NoteRepository noteRepository;
    private final MatiereRepository matiereRepository;
    private final DocumentRepository documentRepository;

    public BulletinGenerationService(MinioService minioService,
                                     EleveRepository eleveRepository,
                                     NoteRepository noteRepository,
                                     MatiereRepository matiereRepository,
                                     DocumentRepository documentRepository) {
        this.minioService = minioService;
        this.eleveRepository = eleveRepository;
        this.noteRepository = noteRepository;
        this.matiereRepository = matiereRepository;
        this.documentRepository = documentRepository;
    }

    /**
     * Génère un bulletin pour un élève à partir d'un template.
     *
     * @param templateDocId ID du document template dans la BD
     * @param eleveId       ID de l'élève
     * @param trimestre     Trimestre (1, 2 ou 3)
     * @return ID du document bulletin généré
     */
    public String generateBulletin(String templateDocId, String eleveId, int trimestre) throws Exception {
        // 1. Récupérer le template depuis MinIO
        Document templateDoc = documentRepository.findById(templateDocId)
                .orElseThrow(() -> new RuntimeException("Template non trouvé: " + templateDocId));
        String minioKey = templateDocId + "." + templateDoc.getExtension();
        byte[] templateBytes = minioService.getFileTest(minioKey);

        // 2. Récupérer les données de l'élève
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé: " + eleveId));

        // 3. Récupérer les notes de l'élève pour ce trimestre
        List<Note> notes = noteRepository.findByEleveIdAndTrimestre(eleveId, trimestre);

        // 4. Récupérer les matières
        Map<String, Matiere> matiereMap = matiereRepository.findAll().stream()
                .collect(Collectors.toMap(Matiere::getId, m -> m));

        // 5. Calculer la moyenne générale
        double totalCoeffNote = 0;
        double totalCoeff = 0;
        for (Note note : notes) {
            double coeff = note.getCoefficient();
            totalCoeffNote += note.getNote() * coeff;
            totalCoeff += coeff;
        }
        double moyenneGenerale = totalCoeff > 0 ? totalCoeffNote / totalCoeff : 0;

        // 6. Ouvrir le document DOCX avec Apache POI
        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(templateBytes));

        // 7. Remplacer les placeholders dans les paragraphes
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            replacePlaceholdersInParagraph(paragraph, eleve, trimestre, moyenneGenerale);
        }

        // 8. Remplacer aussi dans les tableaux existants
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replacePlaceholdersInParagraph(paragraph, eleve, trimestre, moyenneGenerale);
                    }
                }
            }
        }

        // 9. Chercher et remplacer {{NOTES_TABLE}} par un vrai tableau
        insertNotesTable(document, notes, matiereMap, moyenneGenerale);

        // 10. Sauvegarder le document généré
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        document.close();
        byte[] bulletinBytes = out.toByteArray();

        // 11. Enregistrer dans MinIO et BD
        String bulletinId = UUID.randomUUID().toString();
        String bulletinName = "Bulletin_" + eleve.getNom() + "_" + eleve.getPrenom()
                + "_T" + trimestre + ".docx";
        String bulletinKey = bulletinId + ".docx";

        minioService.saveFileTest(bulletinKey, bulletinBytes);

        Document bulletinDoc = new Document();
        bulletinDoc.setId(bulletinId);
        bulletinDoc.setNom(bulletinName);
        bulletinDoc.setExtension("docx");
        bulletinDoc.setTaille(bulletinBytes.length);
        bulletinDoc.setVersion(1);
        bulletinDoc.setProprietaireId(eleve.getMatricule());
        documentRepository.save(bulletinDoc);

        return bulletinId;
    }

    private void replacePlaceholdersInParagraph(XWPFParagraph paragraph,
                                                 Eleve eleve, int trimestre,
                                                 double moyenneGenerale) {
        String text = paragraph.getText();
        if (text == null || text.isEmpty()) return;

        // Vérifier si le texte contient un placeholder
        if (!text.contains("{{")) return;

        // Remplacer dans chaque run pour préserver le formatage
        for (XWPFRun run : paragraph.getRuns()) {
            String runText = run.getText(0);
            if (runText == null) continue;

            runText = runText.replace("{{NOM_ELEVE}}", eleve.getNom() != null ? eleve.getNom() : "")
                    .replace("{{PRENOM_ELEVE}}", eleve.getPrenom() != null ? eleve.getPrenom() : "")
                    .replace("{{MATRICULE}}", eleve.getMatricule() != null ? eleve.getMatricule() : "")
                    .replace("{{CLASSE}}", eleve.getClasse() != null ? eleve.getClasse() : "")
                    .replace("{{DATE_NAISSANCE}}", eleve.getDateNaissance() != null ? eleve.getDateNaissance() : "")
                    .replace("{{SEXE}}", eleve.getSexe() != null ? eleve.getSexe() : "")
                    .replace("{{ANNEE_SCOLAIRE}}", eleve.getAnneeScolaire() != null ? eleve.getAnneeScolaire() : "")
                    .replace("{{TRIMESTRE}}", String.valueOf(trimestre))
                    .replace("{{MOYENNE_GENERALE}}", String.format("%.2f", moyenneGenerale));

            run.setText(runText, 0);
        }
    }

    private void insertNotesTable(XWPFDocument document, List<Note> notes,
                                   Map<String, Matiere> matiereMap,
                                   double moyenneGenerale) {
        // Trouver le paragraphe contenant {{NOTES_TABLE}}
        int targetPos = -1;
        for (int i = 0; i < document.getParagraphs().size(); i++) {
            String text = document.getParagraphs().get(i).getText();
            if (text != null && text.contains("{{NOTES_TABLE}}")) {
                targetPos = i;
                break;
            }
        }

        if (targetPos == -1) return; // Pas de placeholder trouvé

        // Supprimer le paragraphe placeholder
        document.removeBodyElement(document.getPosOfParagraph(document.getParagraphs().get(targetPos)));

        // Créer le tableau des notes
        XWPFTable table = document.createTable();

        // En-tête
        XWPFTableRow headerRow = table.getRow(0);
        setCellText(headerRow.getCell(0), "Matière");
        headerRow.addNewTableCell().setText("Note /20");
        headerRow.addNewTableCell().setText("Coeff.");
        headerRow.addNewTableCell().setText("Note × Coeff.");
        headerRow.addNewTableCell().setText("Appréciation");

        // Lignes de notes
        for (Note note : notes) {
            XWPFTableRow row = table.createRow();
            Matiere matiere = matiereMap.get(note.getMatiereId());
            String matiereName = matiere != null ? matiere.getNom() : "—";

            row.getCell(0).setText(matiereName);
            row.getCell(1).setText(String.format("%.2f", note.getNote()));
            row.getCell(2).setText(String.format("%.1f", note.getCoefficient()));
            row.getCell(3).setText(String.format("%.2f", note.getNote() * note.getCoefficient()));
            row.getCell(4).setText(note.getAppreciation() != null ? note.getAppreciation() : "");
        }

        // Ligne de moyenne générale
        XWPFTableRow avgRow = table.createRow();
        avgRow.getCell(0).setText("MOYENNE GÉNÉRALE");
        avgRow.getCell(1).setText(String.format("%.2f /20", moyenneGenerale));
        avgRow.getCell(2).setText("");
        avgRow.getCell(3).setText("");
        avgRow.getCell(4).setText(getAppreciationGenerale(moyenneGenerale));
    }

    private void setCellText(XWPFTableCell cell, String text) {
        cell.setText(text);
    }

    private String getAppreciationGenerale(double moyenne) {
        if (moyenne >= 16) return "Très Bien";
        if (moyenne >= 14) return "Bien";
        if (moyenne >= 12) return "Assez Bien";
        if (moyenne >= 10) return "Passable";
        if (moyenne >= 8)  return "Insuffisant";
        return "Très Insuffisant";
    }
}

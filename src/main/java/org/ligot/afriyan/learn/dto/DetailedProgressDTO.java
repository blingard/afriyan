package org.ligot.afriyan.learn.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetailedProgressDTO {

    private UUID enrollmentId;
    private FormationProgressDetail formation;

    public DetailedProgressDTO() {
    }

    public DetailedProgressDTO(UUID enrollmentId, FormationProgressDetail formation) {
        this.enrollmentId = enrollmentId;
        this.formation = formation;
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public FormationProgressDetail getFormation() {
        return formation;
    }

    public void setFormation(FormationProgressDetail formation) {
        this.formation = formation;
    }

    // Classe interne pour le détail de la formation
    public static class FormationProgressDetail {
        private UUID id;
        private String titre;
        private String description;
        private Boolean isCompleted; // true si tous les modules sont complétés
        private Double progressionPourcent;
        private List<ModuleProgressDetail> modules = new ArrayList<>();

        public FormationProgressDetail() {
        }

        public FormationProgressDetail(UUID id, String titre, String description, Boolean isCompleted, Double progressionPourcent, List<ModuleProgressDetail> modules) {
            this.id = id;
            this.titre = titre;
            this.description = description;
            this.isCompleted = isCompleted;
            this.progressionPourcent = progressionPourcent;
            this.modules = modules;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getCompleted() {
            return isCompleted;
        }

        public void setCompleted(Boolean completed) {
            isCompleted = completed;
        }

        public Double getProgressionPourcent() {
            return progressionPourcent;
        }

        public void setProgressionPourcent(Double progressionPourcent) {
            this.progressionPourcent = progressionPourcent;
        }

        public List<ModuleProgressDetail> getModules() {
            return modules;
        }

        public void setModules(List<ModuleProgressDetail> modules) {
            this.modules = modules;
        }
    }

    // Classe interne pour le détail d'un module
    public static class ModuleProgressDetail {
        private UUID id;
        private String titre;
        private String description;
        private Integer ordre;
        private Boolean withQuiz;
        private Boolean isCompleted; // true si tous les chapitres sont lus ET (quiz passé si quiz existe)
        private QuizProgressDetail quiz;
        private List<ChapterProgressDetail> chapitres = new ArrayList<>();

        public ModuleProgressDetail() {
        }

        public ModuleProgressDetail(UUID id, String titre, String description, Integer ordre, Boolean withQuiz, Boolean isCompleted, QuizProgressDetail quiz, List<ChapterProgressDetail> chapitres) {
            this.id = id;
            this.titre = titre;
            this.description = description;
            this.ordre = ordre;
            this.withQuiz = withQuiz;
            this.isCompleted = isCompleted;
            this.quiz = quiz;
            this.chapitres = chapitres;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getOrdre() {
            return ordre;
        }

        public void setOrdre(Integer ordre) {
            this.ordre = ordre;
        }

        public Boolean getWithQuiz() {
            return withQuiz;
        }

        public void setWithQuiz(Boolean withQuiz) {
            this.withQuiz = withQuiz;
        }

        public Boolean getCompleted() {
            return isCompleted;
        }

        public void setCompleted(Boolean completed) {
            isCompleted = completed;
        }

        public QuizProgressDetail getQuiz() {
            return quiz;
        }

        public void setQuiz(QuizProgressDetail quiz) {
            this.quiz = quiz;
        }

        public List<ChapterProgressDetail> getChapitres() {
            return chapitres;
        }

        public void setChapitres(List<ChapterProgressDetail> chapitres) {
            this.chapitres = chapitres;
        }
    }

    // Classe interne pour le détail d'un chapitre
    public static class ChapterProgressDetail {
        private UUID id;
        private String titre;
        private Integer ordre;
        private Integer dureeEstimee;
        private Boolean isCompleted; // true si l'utilisateur a lu ce chapitre

        public ChapterProgressDetail() {
        }

        public ChapterProgressDetail(UUID id, String titre, Integer ordre, Integer dureeEstimee, Boolean isCompleted) {
            this.id = id;
            this.titre = titre;
            this.ordre = ordre;
            this.dureeEstimee = dureeEstimee;
            this.isCompleted = isCompleted;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public Integer getOrdre() {
            return ordre;
        }

        public void setOrdre(Integer ordre) {
            this.ordre = ordre;
        }

        public Integer getDureeEstimee() {
            return dureeEstimee;
        }

        public void setDureeEstimee(Integer dureeEstimee) {
            this.dureeEstimee = dureeEstimee;
        }

        public Boolean getCompleted() {
            return isCompleted;
        }

        public void setCompleted(Boolean completed) {
            isCompleted = completed;
        }
    }

    // Classe interne pour le détail du quiz
    public static class QuizProgressDetail {
        private UUID id;
        private String titre;
        private Integer scoreMinimum;
        private Boolean isPassed; // true si l'utilisateur a passé le quiz avec succès
        private Double lastScore; // Dernier score obtenu

        public QuizProgressDetail() {
        }

        public QuizProgressDetail(UUID id, String titre, Integer scoreMinimum, Boolean isPassed, Double lastScore) {
            this.id = id;
            this.titre = titre;
            this.scoreMinimum = scoreMinimum;
            this.isPassed = isPassed;
            this.lastScore = lastScore;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public Integer getScoreMinimum() {
            return scoreMinimum;
        }

        public void setScoreMinimum(Integer scoreMinimum) {
            this.scoreMinimum = scoreMinimum;
        }

        public Boolean getPassed() {
            return isPassed;
        }

        public void setPassed(Boolean passed) {
            isPassed = passed;
        }

        public Double getLastScore() {
            return lastScore;
        }

        public void setLastScore(Double lastScore) {
            this.lastScore = lastScore;
        }
    }
}

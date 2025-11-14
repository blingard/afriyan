package org.ligot.afriyan.learn.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProgressEnrollement {


    private FormationsProgress progress;

    public ProgressEnrollement() {
    }

    public ProgressEnrollement(FormationsProgress progress) {
        this.progress = progress;
    }

    public FormationsProgress getProgress() {
        return progress;
    }

    public void setProgress(FormationsProgress progress) {
        this.progress = progress;
    }

    public static class FormationsProgress{
        private UUID id;
        private String titre;
        private ProgressStatus status;
        private List<ModulesProgress> modules=new ArrayList<>();

        public FormationsProgress() {
        }

        public FormationsProgress(UUID id, String titre, ProgressStatus status, List<ModulesProgress> modules) {
            this.id = id;
            this.titre = titre;
            this.status = status;
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

        public ProgressStatus getStatus() {
            return status;
        }

        public void setStatus(ProgressStatus status) {
            this.status = status;
        }

        public List<ModulesProgress> getModules() {
            return modules;
        }

        public void setModules(List<ModulesProgress> modules) {
            this.modules = modules;
        }
    }

    public static class ModulesProgress{
        private UUID id;
        private String titre;
        private ProgressStatus status;

        private List<ChapitresProgress> modules=new ArrayList<>();

        public ModulesProgress() {
        }

        public ModulesProgress(UUID id, String titre, ProgressStatus status, List<ChapitresProgress> modules) {
            this.id = id;
            this.titre = titre;
            this.status = status;
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

        public ProgressStatus getStatus() {
            return status;
        }

        public void setStatus(ProgressStatus status) {
            this.status = status;
        }

        public List<ChapitresProgress> getModules() {
            return modules;
        }

        public void setModules(List<ChapitresProgress> modules) {
            this.modules = modules;
        }
    }

    public static class ChapitresProgress{
        private UUID id;
        private String titre;
        private ProgressStatus status;

        public ChapitresProgress() {
        }

        public ChapitresProgress(UUID id, String titre, ProgressStatus status) {
            this.id = id;
            this.titre = titre;
            this.status = status;
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

        public ProgressStatus getStatus() {
            return status;
        }

        public void setStatus(ProgressStatus status) {
            this.status = status;
        }
    }

    public enum ProgressStatus{
        NOT_STARTED, FINISH, PROGRESS;
    }
}

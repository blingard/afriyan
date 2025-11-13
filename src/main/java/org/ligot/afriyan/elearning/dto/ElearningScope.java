package org.ligot.afriyan.elearning.dto;

import java.util.List;

public class ElearningScope {
    private FormationsDTO formation;
    private List<ElearningModuleScope> module;
    private boolean pass;

    public ElearningScope(FormationsDTO formation, List<ElearningModuleScope> module, boolean pass) {
        this.formation = formation;
        this.module = module;
        this.pass = pass;
    }

    public ElearningScope() {
    }

    public FormationsDTO getFormation() {
        return formation;
    }

    public void setFormation(FormationsDTO formation) {
        this.formation = formation;
    }

    public List<ElearningModuleScope> getModule() {
        return module;
    }

    public void setModule(List<ElearningModuleScope> module) {
        this.module = module;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }
}

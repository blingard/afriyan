package org.ligot.afriyan.elearning.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import java.util.List;

public class ElearningModuleScope {
    private ChapitresDTO module;

    private ElearningModuleScope moduleNext;
    private ElearningModuleScope modulePrevious;

    private List<ElearningChapterScope> chapitres;
    private boolean pass;

    public ElearningModuleScope() {
    }

    public ElearningModuleScope(ChapitresDTO module, ElearningModuleScope moduleNext, ElearningModuleScope modulePrevious, List<ElearningChapterScope> chapitres, boolean pass) {
        this.module = module;
        this.moduleNext = moduleNext;
        this.modulePrevious = modulePrevious;
        this.chapitres = chapitres;
        this.pass = pass;
    }

    public ChapitresDTO getModule() {
        return module;
    }

    public void setModule(ChapitresDTO module) {
        this.module = module;
    }

    public List<ElearningChapterScope> getChapitres() {
        return chapitres;
    }

    public void setChapitres(List<ElearningChapterScope> chapitres) {
        this.chapitres = chapitres;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public ElearningModuleScope getModuleNext() {
        return moduleNext;
    }

    public void setModuleNext(ElearningModuleScope moduleNext) {
        this.moduleNext = moduleNext;
    }

    public ElearningModuleScope getModulePrevious() {
        return modulePrevious;
    }

    public void setModulePrevious(ElearningModuleScope modulePrevious) {
        this.modulePrevious = modulePrevious;
    }
}

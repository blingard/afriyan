package org.ligot.afriyan.elearning.dto;

import java.util.List;

public class ElearningChapterScope {
    private ParagraphsDTO chapitre;
    private ElearningChapterScope chapitreNext;
    private ElearningChapterScope chapitrePrevious;
    private boolean pass;

    public ElearningChapterScope(ParagraphsDTO chapitre, ElearningChapterScope chapitreNext, ElearningChapterScope chapitrePrevious, boolean pass) {
        this.chapitre = chapitre;
        this.chapitreNext = chapitreNext;
        this.chapitrePrevious = chapitrePrevious;
        this.pass = pass;
    }

    public ElearningChapterScope() {
    }

    public ParagraphsDTO getChapitre() {
        return chapitre;
    }

    public void setChapitre(ParagraphsDTO chapitre) {
        this.chapitre = chapitre;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public ElearningChapterScope getChapitreNext() {
        return chapitreNext;
    }

    public void setChapitreNext(ElearningChapterScope chapitreNext) {
        this.chapitreNext = chapitreNext;
    }

    public ElearningChapterScope getChapitrePrevious() {
        return chapitrePrevious;
    }

    public void setChapitrePrevious(ElearningChapterScope chapitrePrevious) {
        this.chapitrePrevious = chapitrePrevious;
    }
}

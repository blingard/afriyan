package org.ligot.afriyan.elearning.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "chapters_quizz")
public class ChapitresQuizz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long quizzId;

    @Column(nullable = false)
    private Long chapterId;

    private boolean finish;
    public ChapitresQuizz() {
    }

    public ChapitresQuizz(Long id, Long quizzId, Long chapterId, boolean finish) {
        this.id = id;
        this.quizzId = quizzId;
        this.chapterId = chapterId;
        this.finish = finish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuizzId() {
        return quizzId;
    }

    public void setQuizzId(Long quizzId) {
        this.quizzId = quizzId;
    }

    public Long getChaptreId() {
        return chapterId;
    }

    public void setChaptreId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}

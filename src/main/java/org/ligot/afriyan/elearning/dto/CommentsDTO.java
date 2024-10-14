package org.ligot.afriyan.elearning.dto;

import java.time.Instant;

public class CommentsDTO {
    private Long id;

    private String author;

    private String content;

    private Instant dateCreation;

    private boolean status;

    public CommentsDTO() {
    }

    public CommentsDTO(Long id, String author, String content, Instant dateCreation, boolean status) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.dateCreation = dateCreation;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

package org.ligot.afriyan.elearning.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "paragraph")
public class Paragraphs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TypeParagraph type;

    private String description;

    @Column(length = -1)
    private String content;

    private boolean status;

    public Paragraphs() {
    }

    public Paragraphs(Long id, TypeParagraph type, String description, String content, boolean status) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.content = content;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeParagraph getType() {
        return type;
    }

    public void setType(TypeParagraph type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Paragraphs{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}

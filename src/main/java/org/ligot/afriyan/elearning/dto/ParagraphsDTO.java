package org.ligot.afriyan.elearning.dto;

import org.ligot.afriyan.elearning.entities.TypeParagraph;
import org.springframework.web.multipart.MultipartFile;

public class ParagraphsDTO {
    private Long id;

    private TypeParagraph type;

    private String content;

    private boolean status;

    private String description;

    public ParagraphsDTO() {
    }

    public ParagraphsDTO(Long id, TypeParagraph type, String content, boolean status, String description) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.status = status;
        this.description = description;
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


}

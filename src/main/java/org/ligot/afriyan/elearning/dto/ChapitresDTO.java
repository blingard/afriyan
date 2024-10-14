package org.ligot.afriyan.elearning.dto;

import java.util.HashSet;
import java.util.Set;

public class ChapitresDTO {
    private Long id;

    String title;
    boolean status;

    String orderParagraph;

    private Set<ParagraphsDTO> paragraphes = new HashSet<>(0);

    private Set<CommentsDTO> comments = new HashSet<>(0);
    public ChapitresDTO() {
    }

    public ChapitresDTO(Long id, String title, boolean status, String orderParagraph, Set<ParagraphsDTO> paragraphes, Set<CommentsDTO> comments) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.orderParagraph = orderParagraph;
        this.paragraphes = paragraphes;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<ParagraphsDTO> getParagraphes() {
        return paragraphes;
    }

    public void setParagraphes(Set<ParagraphsDTO> paragraphes) {
        this.paragraphes = paragraphes;
    }

    public Set<CommentsDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentsDTO> comments) {
        this.comments = comments;
    }

    public String getOrderParagraph() {
        return orderParagraph;
    }

    public void setOrderParagraph(String orderParagraph) {
        this.orderParagraph = orderParagraph;
    }

    @Override
    public String toString() {
        return "ChapitresDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", orderParagraph='" + orderParagraph + '\'' +
                ", paragraphes=" + paragraphes +
                ", comments=" + comments +
                '}';
    }
}

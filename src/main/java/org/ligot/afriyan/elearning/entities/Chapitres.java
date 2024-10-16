package org.ligot.afriyan.elearning.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chapters")
public class Chapitres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String orderParagraph;
    private boolean status;

     @OneToMany(fetch = FetchType.LAZY)
     @OrderBy("id")
    private Set<Paragraphs> paragraphes = new HashSet<>(0);


    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("id")
    private Set<Comments> comments = new HashSet<>(0);

    public Chapitres() {
    }


    public Chapitres(Long id, String title, String orderParagraph, boolean status, Set<Paragraphs> paragraphes, Set<Comments> comments) {
        this.id = id;
        this.title = title;
        this.orderParagraph = orderParagraph;
        this.status = status;
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

    public Set<Paragraphs> getParagraphes() {
        return paragraphes;
    }

    public void setParagraphes(Set<Paragraphs> paragraphes) {
        this.paragraphes = paragraphes;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
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
        return "Chapitres{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", orderParagraph='" + orderParagraph + '\'' +
                ", status=" + status +
                ", paragraphes=" + paragraphes +
                ", comments=" + comments +
                '}';
    }
}

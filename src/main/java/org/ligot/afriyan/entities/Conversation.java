package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "SENDER")
    private String sender;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "dateenvois")
    @CurrentTimestamp
    private Date dateEnvoie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(Date dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }
}

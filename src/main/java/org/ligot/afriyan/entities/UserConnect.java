package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserConnect {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "date_connection")
    private Date dateConnection= Date.from(Instant.now());
    @Column(name = "login_user")
    private String user;

    public UserConnect(Date dateConnection, String user) {
        this.dateConnection = dateConnection;
        this.user = user;
    }
}

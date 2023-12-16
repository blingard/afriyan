package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;

@Entity
@Table(name = "responses")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_response")
    private TypeResponse typeResponse;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<ModelResponse> values = new HashSet<>(0);
    @Enumerated(EnumType.STRING)
    @Column(name = "type_user")
    private TypeUserSondage typeUser;

    public Answer() {
    }
    public Answer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeResponse getTypeResponse() {
        return typeResponse;
    }

    public void setTypeResponse(TypeResponse typeResponse) {
        this.typeResponse = typeResponse;
    }

    public Set<ModelResponse> getValues() {
        return values;
    }

    public void setValues(Set<ModelResponse> values) {
        this.values = values;
    }

    public TypeUserSondage getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUserSondage typeUser) {
        this.typeUser = typeUser;
    }
}

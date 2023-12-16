package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.sondage.entities.Answer;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Long> {
    @Query("SELECT COUNT(*) FROM Answer a WHERE a.typeResponse = :typeResponse AND a.typeUser = :typeUser")
    Long findStatParticipationUserResponseType(@Param("typeResponse") TypeResponse typeResponse, @Param("typeUser") TypeUserSondage typeUser);


}

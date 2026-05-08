package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.ServiceEntity;
import org.ligot.afriyan.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findServiceEntitiesByCentrePartenaire(CentrePartenaire centrePartenaire);

    @Query("""
    SELECT ser
    FROM ServiceEntity ser
    WHERE ser.centrePartenaire.id = :id
      AND ser.centrePartenaire.status = :status
""")
    List<ServiceEntity> findServiceEntitiesByCentrePartenaire(
            @Param("id") Long id,
            @Param("status") Status status
    );


    @Query("""
    SELECT ser
    FROM ServiceEntity ser
    WHERE ser.id = :id
      AND ser.centrePartenaire.status = :status
""")
    Optional<ServiceEntity> findServiceEntitiesByIdAndUSRAJActive(
            @Param("id") Long id,
            @Param("status") Status status
    );
}

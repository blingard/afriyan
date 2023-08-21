package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.ParamTypeEnum;
import org.ligot.afriyan.entities.Parametres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IParametresRepository extends JpaRepository<Parametres, Long> {
    List<Parametres> findAllByStatusTrueAndParamTypeEnum(ParamTypeEnum paramTypeEnum);
    List<Parametres> findAllByParamTypeEnum(ParamTypeEnum paramTypeEnum);
    Optional<Parametres> findByStatusTrueAndParamTypeEnum(ParamTypeEnum paramTypeEnum);
}

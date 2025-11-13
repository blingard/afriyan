package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.ParamTypeEnum;
import org.ligot.afriyan.entities.Parametres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface IParametresRepository extends JpaRepository<Parametres, Long> {
    List<Parametres> findAllByStatusTrueAndParamTypeEnum(ParamTypeEnum paramTypeEnum);
    List<Parametres> findAllByParamTypeEnum(ParamTypeEnum paramTypeEnum);
    Page<Parametres> findAllByParamTypeEnum(ParamTypeEnum paramTypeEnum, Pageable pageable);
    Optional<Parametres> findByStatusTrueAndParamTypeEnum(ParamTypeEnum paramTypeEnum);
    Optional<Parametres> findByParamTypeEnum(ParamTypeEnum paramTypeEnum);
}

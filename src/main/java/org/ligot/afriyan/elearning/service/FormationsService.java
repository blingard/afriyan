package org.ligot.afriyan.elearning.service;

import org.ligot.afriyan.elearning.dto.FormationsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FormationsService {
    void create(FormationsDTO formationsDTO)throws Exception;
    void update(Long idFormation, FormationsDTO formationsDTO)throws Exception;
    FormationsDTO findById(Long idFormation)throws Exception;
    FormationsDTO findByIdUser(Long idFormation)throws Exception;
    List<FormationsDTO>  findAllByIdUser(Long idUser)throws Exception;
    List<FormationsDTO>  findFinishByIdUser(Long idUser)throws Exception;
    List<FormationsDTO>  findNotFinishByIdUser(Long idUser)throws Exception;
    FormationsDTO findByIdAdmin(Long idFormation)throws Exception;
    Page<FormationsDTO> findAll(int page, int size)throws Exception;
    List<FormationsDTO> findAll()throws Exception;
    List<FormationsDTO> findAllActive()throws Exception;
    void enable(Long id)throws Exception;
    void finishFormation(Long idUser, Long idFormation)throws Exception;
    void addQuizz(Long idFormation, Long idQuizz )throws Exception;
    void disable(Long id)throws Exception;
}

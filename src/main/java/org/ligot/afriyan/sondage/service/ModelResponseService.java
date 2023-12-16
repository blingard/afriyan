package org.ligot.afriyan.sondage.service;

import org.ligot.afriyan.sondage.dto.ModelResponseDTO;
import org.ligot.afriyan.sondage.entities.ModelResponse;

public interface ModelResponseService {
    ModelResponse save(ModelResponseDTO modelResponseDTO);
    ModelResponse update(Long id, ModelResponseDTO modelResponseDTO) throws Exception;
    ModelResponseDTO findById(Long id) throws Exception;
    ModelResponse findEntityById(Long id);
}

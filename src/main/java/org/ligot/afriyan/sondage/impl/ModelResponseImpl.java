package org.ligot.afriyan.sondage.impl;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.sondage.dto.ModelResponseDTO;
import org.ligot.afriyan.sondage.entities.ModelResponse;
import org.ligot.afriyan.sondage.mapper.ModelResponseMapper;
import org.ligot.afriyan.sondage.repo.ModelResponseRepo;
import org.ligot.afriyan.sondage.service.ModelResponseService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ModelResponseImpl implements ModelResponseService {
    private final ModelResponseRepo repo;
    private final ModelResponseMapper mapper;

    public ModelResponseImpl(ModelResponseRepo repo, ModelResponseMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ModelResponse save(ModelResponseDTO modelResponseDTO) {
        modelResponseDTO.setId(null);
        return repo.save(mapper.toEntity(modelResponseDTO));
    }

    @Override
    public ModelResponse update(Long id, ModelResponseDTO modelResponseDTO) throws Exception {
        ModelResponse modelResponse = repo.findById(id).orElseThrow(()->new Exception("ModelResponse not found"));
        if (!Objects.equals(modelResponse.getId(), modelResponseDTO.getId()))
            throw new Exception("ModelResponse: data not match");
        mapper.update(modelResponseDTO, modelResponse);
        return repo.save(modelResponse);
    }

    @Override
    public ModelResponseDTO findById(Long id) throws Exception {
        ModelResponse modelResponse = repo.findById(id).orElseThrow(()->new Exception("ModelResponse not found"));
        return mapper.toDTO(modelResponse);
    }

    @Override
    public ModelResponse findEntityById(Long id){
        return repo.findById(id).orElse(null);
    }
}

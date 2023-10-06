package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MediatechDTO;

import java.util.List;

public interface IMediatech {
    List<MediatechDTO> findAll();
    List<MediatechDTO> findAllActive();
    void activeOrDesable(Long id);
}

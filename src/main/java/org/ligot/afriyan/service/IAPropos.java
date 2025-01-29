package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.AProposDTO;

public interface IAPropos {
    void save(AProposDTO aProposDTO)throws Exception;
    void update(Long id, AProposDTO aProposDTO)throws Exception;
    AProposDTO get();
    AProposDTO get(Long id)throws Exception;

}

package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface IServiceEntity {

    ServiceDTO findById(Long id) throws Exception ;
    ServiceDTO save(ServiceDTO serviceDto) throws Exception ;
    Page<ServiceDTO> list(int page) throws Exception ;
    ServiceDTO update(ServiceDTO serviceDto, Long id) throws Exception ;
    void delete(Long id) throws Exception ;
    void sendSMS(Set<UtilisateurDTO> utilisateurs, String message) throws Exception ;

}

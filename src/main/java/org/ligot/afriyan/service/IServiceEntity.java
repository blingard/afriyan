package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ServiceDTO;
import org.springframework.data.domain.Page;


public interface IServiceEntity {

    ServiceDTO findById(Long id) throws Exception ;
    ServiceDTO save(ServiceDTO serviceDto) throws Exception ;
    Page<ServiceDTO> list(int page) throws Exception ;
    ServiceDTO update(ServiceDTO serviceDto, Long id) throws Exception ;
    void delete(Long id) throws Exception ;

}

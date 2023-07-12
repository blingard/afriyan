package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.entities.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceEntity create(ServiceDTO dto);
    ServiceDTO toDTO(ServiceEntity entity);
}

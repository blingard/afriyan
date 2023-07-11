package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ServiceDto;
import org.ligot.afriyan.entities.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ServiceMapper {

    ServiceEntity create(ServiceDto dto);
    ServiceDto toDTO(ServiceEntity entity);
}

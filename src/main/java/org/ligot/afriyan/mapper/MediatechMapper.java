package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.MediatechDTO;
import org.ligot.afriyan.entities.Mediatech;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface MediatechMapper {

    MediatechDTO toDTO(Mediatech mediatech);
    Mediatech create(MediatechDTO mediatechDTO);
    @Mapping(source = "text", target = "text", ignore = true)
    @Mapping(source = "path", target = "path", ignore = true)
    @Mapping(source = "id", target = "id", ignore = true)
    void update(MediatechDTO mediatechDTO, @MappingTarget Mediatech mediatech);
}

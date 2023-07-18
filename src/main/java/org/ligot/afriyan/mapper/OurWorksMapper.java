package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.OurWorksDTO;
import org.ligot.afriyan.entities.OurWorks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface OurWorksMapper {
    OurWorksDTO toDTO(OurWorks ourWorks);
    OurWorks create(OurWorksDTO ourWorksDTO);
    @Mapping(source = "id", target = "id",ignore = true)
    @Mapping(source = "ourWorksType", target = "ourWorksType",ignore = true)
    void update(OurWorksDTO ourWorksDTO, @MappingTarget OurWorks ourWorks);
}

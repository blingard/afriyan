package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.SlidersDTO;
import org.ligot.afriyan.Dto.SlidersSmartDTO;
import org.ligot.afriyan.entities.Sliders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SlidersMapper {

    SlidersDTO toDTO(Sliders certificates);

    @Mapping(target = "hasContent", source = "content", qualifiedByName = "mapHasContent")
    SlidersSmartDTO toDTOSmart(Sliders certificates);

    @Named("mapHasContent")
    default boolean mapHasContent(String content) {
        return content != null && !content.trim().isBlank();
    }
    Sliders create(SlidersDTO articlesDTO);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "photo", target = "photo", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    void update(SlidersDTO certificatesDTO, @MappingTarget Sliders certificates);
}

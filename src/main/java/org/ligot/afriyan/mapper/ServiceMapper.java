package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.entities.Produit;
import org.ligot.afriyan.entities.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceEntity create(ServiceDTO dto);
    ServiceDTO toDTO(ServiceEntity entity);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "centrePartenaire", target = "centrePartenaire", ignore = true)
    @Mapping(source = "dateCreation", target = "dateCreation", ignore = true)
    void update(ServiceDTO produitDTO, @MappingTarget ServiceEntity produit);
}

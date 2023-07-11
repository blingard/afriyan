package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    private Long id;
    @NotNull
    private String libelle;
    @NotNull
    private String description;
    @NotNull
    private Date dateCreation=new Date();
}

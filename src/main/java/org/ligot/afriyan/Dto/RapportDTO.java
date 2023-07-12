package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Status;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RapportDTO {

    private Long id;
    @NotNull
    private String entete;
    @NotNull
    private String cotenu;
    @NotNull
    private Date date;
    @NotNull
    private Status status;
}

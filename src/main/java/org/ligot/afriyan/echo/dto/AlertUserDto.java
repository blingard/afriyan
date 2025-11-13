package org.ligot.afriyan.echo.dto;

import org.ligot.afriyan.Dto.UtilisateurDTO;

public record AlertUserDto(AlertsDTO alert, UtilisateurDTO utilisateur) {
}

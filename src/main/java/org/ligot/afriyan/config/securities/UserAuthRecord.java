package org.ligot.afriyan.config.securities;

import org.ligot.afriyan.entities.Utilisateur;

import java.util.Map;

public record UserAuthRecord(Map<String, Object> extraClaims) {
}

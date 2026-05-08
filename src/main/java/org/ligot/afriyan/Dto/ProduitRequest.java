package org.ligot.afriyan.Dto;

public record ProduitRequest (
        Long serviceId,
        String libelle,
        String description,
        Double prix)
{}

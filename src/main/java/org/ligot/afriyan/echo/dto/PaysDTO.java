package org.ligot.afriyan.echo.dto;

import java.util.UUID;


public class PaysDTO {
    private UUID id;

    private String name;

    private String abbr;

    public PaysDTO(UUID id, String name, String abbr) {
        this.id = id;
        this.name = name;
        this.abbr = abbr;
    }

    public PaysDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}

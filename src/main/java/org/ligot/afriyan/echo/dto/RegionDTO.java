
package org.ligot.afriyan.echo.dto;

import java.util.UUID;

public class RegionDTO {
    private UUID id;

    private String name;

    private PaysDTO pays;

    public RegionDTO(UUID id, String name, PaysDTO pays) {
        this.id = id;
        this.name = name;
        this.pays = pays;
    }

    public RegionDTO() {
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

    public PaysDTO getPays() {
        return pays;
    }

    public void setPays(PaysDTO pays) {
        this.pays = pays;
    }
}

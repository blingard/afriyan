
package org.ligot.afriyan.echo.dto;

import java.util.UUID;

public class DepartementDTO {
    private UUID id;

    private String name;

    private RegionDTO region;

    public DepartementDTO(UUID id, String name, RegionDTO region) {
        this.id = id;
        this.name = name;
        this.region = region;
    }

    public DepartementDTO() {
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

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }
}

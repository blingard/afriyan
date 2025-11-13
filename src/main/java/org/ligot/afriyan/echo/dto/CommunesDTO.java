
package org.ligot.afriyan.echo.dto;


import java.util.UUID;

public class CommunesDTO {
    private UUID id;

    private String name;

    private String latitude;
    private String longitude;

    private DepartementDTO departement;


    public CommunesDTO(UUID id, String name, DepartementDTO departement, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.departement = departement;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CommunesDTO() {
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

    public DepartementDTO getDepartement() {
        return departement;
    }

    public void setDepartement(DepartementDTO departement) {
        this.departement = departement;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

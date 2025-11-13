package org.ligot.afriyan.echo.entities;

public enum LocalityType {
    CITY("city"),
    VILLAGE("village"),
    TOWN("town"),
    ;

    private String description;


    LocalityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static LocalityType get(String description){
        if(description.equals(CITY.getDescription()))
            return CITY;
        if(description.equals(VILLAGE.getDescription()))
            return VILLAGE;
        if(description.equals(TOWN.getDescription()))
            return TOWN;
        if(description.contains(CITY.getDescription()))
            return CITY;
        if(description.contains(VILLAGE.getDescription()))
            return VILLAGE;
        if(description.contains(TOWN.getDescription()))
            return TOWN;
        else
            return TOWN;
    }
}

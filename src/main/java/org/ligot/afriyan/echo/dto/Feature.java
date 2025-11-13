package org.ligot.afriyan.echo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {
    private String type;
    private Map<String, Object> properties;
    private Geometry geometry;

    public Feature() {
    }

    public Feature(String type, Map<String, Object> properties, Geometry geometry) {
        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}

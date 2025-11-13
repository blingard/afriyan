package org.ligot.afriyan.echo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ligot.afriyan.echo.dto.GeoJson;

import java.io.InputStream;

public class GeoJsonService {
    public GeoJsonService() {
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeoJson readGeoJsonFile(String name) throws RuntimeException {
        try {
            try (InputStream input = getClass().getClassLoader().getResourceAsStream(name+".geojson")) {
                if (input == null) {
                    throw new RuntimeException("Fichier GeoJSON "+name+" introuvable dans resources");
                }
                return objectMapper.readValue(input, GeoJson.class);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }
}

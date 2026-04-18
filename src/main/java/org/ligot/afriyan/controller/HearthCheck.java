package org.ligot.afriyan.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.ligot.afriyan.echo.service.CronService;

import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
public class HearthCheck {

    private final CronService cronService;

    public HearthCheck(CronService cronService) {
        this.cronService = cronService;
    }

    @GetMapping("/public/healthckeck")
    public Object healthCheck() throws IOException {
        /*PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        List<Donnees> donnees = new ArrayList<>();
        // Lire tous les fichiers dans resources/static/
        Resource[] resources = resolver.getResources("classpath:/static/*");

        for (Resource resource : resources) {
            donnees.add(new Donnees("/img/"+resource.getFilename(), "/img/"+resource.getFilename(),
                    "100px", "100px"));
            System.err.println("/img/"+resource.getFilename());
        }*/
        //database.getAll(id);
        //return donnees;
        return "HealtChecck";
    }



    public List<Person> readCsvFile(MultipartFile file) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
            PersonMappingStrategy strategy = new PersonMappingStrategy();

            CsvToBean<Person> csvToBean = new CsvToBeanBuilder<Person>(reader)
                    .withMappingStrategy(strategy)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV : " + file.getName(), e);
        }
    }




}

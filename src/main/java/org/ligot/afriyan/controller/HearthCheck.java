package org.ligot.afriyan.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.validation.constraints.Email;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Sexe;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.implement.UtilisateurService;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class HearthCheck {

    private final ApplicationContext context;

    public HearthCheck(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping("/test/healthckeck")
    public Object healthCheck() throws Exception {
        IUtilisateur iUtilisateur = context.getBean(UtilisateurService.class);

        return iUtilisateur.statusListSave();
        //return "HealtChecck";
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

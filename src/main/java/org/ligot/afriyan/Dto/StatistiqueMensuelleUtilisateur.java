package org.ligot.afriyan.Dto;

import java.util.ArrayList;
import java.util.List;

public class StatistiqueMensuelleUtilisateur {
    private Long year;
    private List<StatistiqueMensuelle> data = new ArrayList<>(0);

    public StatistiqueMensuelleUtilisateur() {
    }

    public StatistiqueMensuelleUtilisateur(Long year, List<StatistiqueMensuelle> data) {
        this.year = year;
        this.data = data;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public List<StatistiqueMensuelle> getData() {
        return data;
    }

    public void setData(List<StatistiqueMensuelle> data) {
        this.data = data;
    }
}

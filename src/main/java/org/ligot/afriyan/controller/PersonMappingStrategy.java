package org.ligot.afriyan.controller;

import com.opencsv.bean.ColumnPositionMappingStrategy;

public class PersonMappingStrategy extends ColumnPositionMappingStrategy<Person> {
    public PersonMappingStrategy() {
        this.setType(Person.class);
        this.setColumnMapping(new String[] {"id", "first", "last"});
    }
}

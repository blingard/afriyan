package org.ligot.afriyan.controller;

import com.opencsv.bean.CsvBindByName;

public class Person {
    @CsvBindByName(column = "id")
    private String id;
    @CsvBindByName(column = "first")
    private String first;
    @CsvBindByName(column = "last")
    private String last;

    public Person() {
    }

    public Person(String identifier, String firstName, String lastName) {
        this.id = identifier;
        this.first = firstName;
        this.last = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String identifier) {
        this.id = identifier;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String firstName) {
        this.first = firstName;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String lastName) {
        this.last = lastName;
    }
}

package org.ligot.afriyan.echo.dto;

public class Request {
    private String query;
    private String type;

    public Request() {
    }

    public Request(String query, String type) {
        this.query = query;
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

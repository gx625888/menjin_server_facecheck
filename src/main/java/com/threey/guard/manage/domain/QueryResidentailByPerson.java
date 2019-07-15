package com.threey.guard.manage.domain;

import java.io.Serializable;

public class QueryResidentailByPerson implements Serializable {
    private static final long serialVersionUID = 4700291136212797617L;

    private String person;
    private String house;
    private String residentail;
    private String build;
    private String unit;
    private String name;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getResidentail() {
        return residentail;
    }

    public void setResidentail(String residentail) {
        this.residentail = residentail;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

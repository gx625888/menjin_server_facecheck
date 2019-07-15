package com.threey.guard.api.domain;

import java.io.Serializable;

public class DeviceArea implements Serializable {
    private static final long serialVersionUID = -7759580703011864349L;

    private String unit;

    private String build;

    private String residentail;

    private String buildName;

    private String unitName;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getResidentail() {
        return residentail;
    }

    public void setResidentail(String residentail) {
        this.residentail = residentail;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}

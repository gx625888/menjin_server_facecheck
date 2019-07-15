package com.threey.guard.manage.domain;

import java.io.Serializable;

public class Device implements Serializable {
    private static final long serialVersionUID = 1539036292327175553L;

    private String id;

    private String deviceId;

    private int deviceType;

    private int deviceStatus;

    private String residentail;

    private String build;

    private String unit;

    private boolean isOnline;

    private String deviceTel;

    private String devicePhone;

    private String createUser;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDevicePhone() {
        return devicePhone;
    }

    public void setDevicePhone(String devicePhone) {
        this.devicePhone = devicePhone;
    }

    public String getDeviceTel() {
        return deviceTel;
    }

    public void setDeviceTel(String deviceTel) {
        this.deviceTel = deviceTel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}

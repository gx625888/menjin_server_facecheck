package com.threey.guard.api.domain;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

import java.io.Serializable;

public class CallInfo implements Serializable{


    private static final long serialVersionUID = 8678937879602078587L;

    private String phoneOne;

    private String phoneTwo;

    private String deviceTel;

    private String devicePhone;

    private int openType;

    public String getDevicePhone() {
        return devicePhone;
    }

    public void setDevicePhone(String devicePhone) {
        this.devicePhone = devicePhone;
    }

    public String getPhoneOne() {
        return phoneOne;
    }

    public void setPhoneOne(String phoneOne) {
        this.phoneOne = phoneOne;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public void setPhoneTwo(String phoneTwo) {
        this.phoneTwo = phoneTwo;
    }

    public String getDeviceTel() {
        return deviceTel;
    }

    public void setDeviceTel(String deviceTel) {
        this.deviceTel = deviceTel;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }
}

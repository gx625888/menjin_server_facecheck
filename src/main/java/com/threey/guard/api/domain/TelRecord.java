package com.threey.guard.api.domain;

/**
 * 第三方呼叫记录
 */
public class TelRecord {
    private long id ;
    private String deviceId;
    private String deviceTel;
    private String phone;
    private String house;
    private String callBackInfo;
    private String devicePhone;
    private int isOpen;
    private int openType;

    private int callNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceTel() {
        return deviceTel;
    }

    public void setDeviceTel(String deviceTel) {
        this.deviceTel = deviceTel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCallBackInfo() {
        return callBackInfo;
    }

    public void setCallBackInfo(String callBackInfo) {
        this.callBackInfo = callBackInfo;
    }

    public String getDevicePhone() {
        return devicePhone;
    }

    public void setDevicePhone(String devicePhone) {
        this.devicePhone = devicePhone;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getCallNum() {
        return callNum;
    }

    public void setCallNum(int callNum) {
        this.callNum = callNum;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }
}

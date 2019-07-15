package com.threey.guard.wechat.domain;

public class WxBandVO extends WxBand {

    /**
     * 小区
     */
    private String residentialName ;

    /**
     * 楼栋
     */
    private String buildName;

    /**
     * 单元
     */
    private String unitName;

    /**
     * 楼层
     */
    private String levelName;

    /**
     * 房号
     */
    private String houseName;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备在线状态
     */
    private String deviceStatus;

    /**
     * 开门方式
     */
    private int houseOpenType;


    public String getResidentialName() {
        return residentialName;
    }

    public void setResidentialName(String residentialName) {
        this.residentialName = residentialName;
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

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getHouseOpenType() {
        return houseOpenType;
    }

    public void setHouseOpenType(int houseOpenType) {
        this.houseOpenType = houseOpenType;
    }
}

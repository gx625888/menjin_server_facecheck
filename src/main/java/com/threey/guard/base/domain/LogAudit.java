package com.threey.guard.base.domain;

import java.io.Serializable;

/**
 * 操作日志信息类
 */
public class LogAudit implements Serializable {
    private long id;
    private String userId;
    private String optTime;
    private String optType;
    private int optTypeInt;
    private String userName;
    private String info;
    private String ipInfo;
    private String mid ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getOptTypeInt() {
        return optTypeInt;
    }

    public void setOptTypeInt(int optTypeInt) {
        this.optTypeInt = optTypeInt;
    }

    public String getIpInfo() {
        return ipInfo;
    }

    public void setIpInfo(String ipInfo) {
        this.ipInfo = ipInfo;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}

package com.threey.guard.manage.domain;

import java.io.Serializable;

public class AddCard implements Serializable {
    private static final long serialVersionUID = -9044870910555364822L;

    private String id;

    private String community;

    private String residentail;

    private int addNum;

    private int sendNum;

    private String createUserCompany;

    private String createUserProvince;

    private String createUserCity;

    private String createUserResidentail;

    public String getCreateUserCompany() {
        return createUserCompany;
    }

    public void setCreateUserCompany(String createUserCompany) {
        this.createUserCompany = createUserCompany;
    }

    public String getCreateUserProvince() {
        return createUserProvince;
    }

    public void setCreateUserProvince(String createUserProvince) {
        this.createUserProvince = createUserProvince;
    }

    public String getCreateUserCity() {
        return createUserCity;
    }

    public void setCreateUserCity(String createUserCity) {
        this.createUserCity = createUserCity;
    }

    public String getCreateUserResidentail() {
        return createUserResidentail;
    }

    public void setCreateUserResidentail(String createUserResidentail) {
        this.createUserResidentail = createUserResidentail;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResidentail() {
        return residentail;
    }

    public void setResidentail(String residentail) {
        this.residentail = residentail;
    }

    public int getAddNum() {
        return addNum;
    }

    public void setAddNum(int addNum) {
        this.addNum = addNum;
    }
}

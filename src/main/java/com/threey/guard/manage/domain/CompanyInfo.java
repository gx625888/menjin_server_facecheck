package com.threey.guard.manage.domain;

import java.io.Serializable;

public class CompanyInfo implements Serializable {
    private static final long serialVersionUID = 4236956862738444610L;

    private String id;

    private String companyName;

    private String platformName;

    private String companyLogo;

    private String person;

    private String phone;

    private String province;

    private String city;

    private String adress;

    private String msgDot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMsgDot() {
        return msgDot;
    }

    public void setMsgDot(String msgDot) {
        this.msgDot = msgDot;
    }
}

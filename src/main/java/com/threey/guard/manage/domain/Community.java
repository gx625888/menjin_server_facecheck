package com.threey.guard.manage.domain;

import java.io.Serializable;

public class Community implements Serializable {

    private static final long serialVersionUID = -922618606502710652L;

    private String id;

    private String name;

    private String street;

    private String province;

    private String city;

    private String county;

    private String remark;

    private String createUser;

    private String createUserProvince;

    private String createUserCity;

    private String createUserCompany;

    private String createUserResidentail;

    public String getCreateUserResidentail() {
        return createUserResidentail;
    }

    public void setCreateUserResidentail(String createUserResidentail) {
        this.createUserResidentail = createUserResidentail;
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

    public String getCreateUserCompany() {
        return createUserCompany;
    }

    public void setCreateUserCompany(String createUserCompany) {
        this.createUserCompany = createUserCompany;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

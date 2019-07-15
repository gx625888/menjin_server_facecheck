package com.threey.guard.manage.domain;

import java.io.Serializable;

public class Residentail implements Serializable {


    private static final long serialVersionUID = 4458075512627709043L;

    private String id;

    private String name;

    private String province;

    private String city;

    private String county;

    private String content;

    private String createDate;

    private String community;

    private String wyCompany;

    private String wyTelphone;

    private String wyPerson;

    private String wyPhone;

    private String address;

    private int flage;

    private String createUser;

    private String createUserProvince;

    private String createUserCity;

    private String createUserCompany;

    private String createUserResidentail;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getWyCompany() {
        return wyCompany;
    }

    public void setWyCompany(String wyCompany) {
        this.wyCompany = wyCompany;
    }

    public String getWyTelphone() {
        return wyTelphone;
    }

    public void setWyTelphone(String wyTelphone) {
        this.wyTelphone = wyTelphone;
    }

    public String getWyPerson() {
        return wyPerson;
    }

    public void setWyPerson(String wyPerson) {
        this.wyPerson = wyPerson;
    }

    public String getWyPhone() {
        return wyPhone;
    }

    public void setWyPhone(String wyPhone) {
        this.wyPhone = wyPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFlage() {
        return flage;
    }

    public void setFlage(int flage) {
        this.flage = flage;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

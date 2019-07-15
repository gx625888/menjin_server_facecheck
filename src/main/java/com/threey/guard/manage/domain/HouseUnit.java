package com.threey.guard.manage.domain;

/**
 * 楼栋住户
 */
public class HouseUnit {




    private long id;
    private String name;
    private long unitId;
    private String phone;
    private String phoneTwo;
    private String phoneThr;
    private int status;
    private int level;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public void setPhoneTwo(String phoneTwo) {
        this.phoneTwo = phoneTwo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhoneThr() {
        return phoneThr;
    }

    public void setPhoneThr(String phoneThr) {
        this.phoneThr = phoneThr;
    }
}

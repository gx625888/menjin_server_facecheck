package com.threey.guard.manage.domain;

import java.io.Serializable;

public class Plan implements Serializable {
    private static final long serialVersionUID = 2414838544295614535L;

    private String id;

    private int planType;

    private int remindType;

    private int remindCycle;

    private int remindRange;

    private long frequency;

    private String createUser;

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

    public int getPlanType() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType = planType;
    }

    public int getRemindType() {
        return remindType;
    }

    public void setRemindType(int remindType) {
        this.remindType = remindType;
    }

    public int getRemindCycle() {
        return remindCycle;
    }

    public void setRemindCycle(int remindCycle) {
        this.remindCycle = remindCycle;
    }

    public int getRemindRange() {
        return remindRange;
    }

    public void setRemindRange(int remindRange) {
        this.remindRange = remindRange;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
}

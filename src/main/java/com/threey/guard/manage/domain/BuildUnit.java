package com.threey.guard.manage.domain;

import java.io.Serializable;

public class BuildUnit implements Serializable {


    private static final long serialVersionUID = 8889544642715156513L;

    private String id;

    private String name;

    private String parentId;

    private int type;

    /**
     * 小区id
     */
    private String residentailId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResidentailId() {
        return residentailId;
    }

    public void setResidentailId(String residentailId) {
        this.residentailId = residentailId;
    }
}

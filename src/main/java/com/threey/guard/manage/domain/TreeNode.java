package com.threey.guard.manage.domain;

import java.io.Serializable;
import java.util.List;

public class TreeNode implements Serializable{

    private static final long serialVersionUID = 3416115308998493155L;

    private String name;

    private String href;

    private String id;

    private int type;

    private List<TreeNode> children;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}

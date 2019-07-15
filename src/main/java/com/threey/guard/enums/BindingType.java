package com.threey.guard.enums;

/**
 * BindingType.java
 *
 * @auth mulths@126.com
 * @date 2019/03/21
 */
public enum  BindingType {

    WX(1, "微信"), MAPP(2, "小程序"), SELF_APP(3, "自有APP"), OUT_APP(4, "外部APP");

    private Integer type;
    private String name;

    BindingType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer type() {
        return type;
    }

    public String getName() {
        return name;
    }
}

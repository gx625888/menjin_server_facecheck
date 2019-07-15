package com.threey.guard.mapp.domain;

/**
 * WxAppBindingPhone.java
 *
 * @auth mulths@126.com
 * @date 2019/02/18
 */
public class WxAppBindingPhone {

    private Long id;

    private String appId;

    private String openId;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

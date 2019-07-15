package com.threey.guard.wechat.domain;

import java.util.List;

public class SNSUserInfo
{
  private String openId;
  private String nickname;
  private int sex;
  private String country;
  private String province;
  private String city;
  private String headImgUrl;
  private List<String> privilegeList;

  public String getOpenId()
  {
    return this.openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public int getSex() {
    return this.sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getProvince() {
    return this.province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getHeadImgUrl() {
    return this.headImgUrl;
  }

  public void setHeadImgUrl(String headImgUrl) {
    this.headImgUrl = headImgUrl;
  }

  public List<String> getPrivilegeList() {
    return this.privilegeList;
  }

  public void setPrivilegeList(List<String> privilegeList) {
    this.privilegeList = privilegeList;
  }
}
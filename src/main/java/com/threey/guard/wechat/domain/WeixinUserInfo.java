package com.threey.guard.wechat.domain;

public class WeixinUserInfo
{
  private String openId;
  private int subscribe;
  private String subscribeTime;
  private String nickname;
  private int sex;
  private String country;
  private String province;
  private String city;
  private String language;
  private String headImgUrl;

  public String getOpenId()
  {
    return this.openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public int getSubscribe() {
    return this.subscribe;
  }

  public void setSubscribe(int subscribe) {
    this.subscribe = subscribe;
  }

  public String getSubscribeTime() {
    return this.subscribeTime;
  }

  public void setSubscribeTime(String subscribeTime) {
    this.subscribeTime = subscribeTime;
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

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getHeadImgUrl() {
    return this.headImgUrl;
  }

  public void setHeadImgUrl(String headImgUrl) {
    this.headImgUrl = headImgUrl;
  }
}
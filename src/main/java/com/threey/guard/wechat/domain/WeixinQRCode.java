package com.threey.guard.wechat.domain;

public class WeixinQRCode
{
  private String ticket;
  private int expireSeconds;

  public String getTicket()
  {
    return this.ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public int getExpireSeconds() {
    return this.expireSeconds;
  }

  public void setExpireSeconds(int expireSeconds) {
    this.expireSeconds = expireSeconds;
  }
}
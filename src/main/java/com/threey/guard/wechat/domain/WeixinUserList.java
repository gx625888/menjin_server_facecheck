package com.threey.guard.wechat.domain;

import java.util.List;

public class WeixinUserList
{
  private int total;
  private int count;
  private List<String> openIdList;
  private String nextOpenId;

  public int getTotal()
  {
    return this.total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getCount() {
    return this.count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<String> getOpenIdList() {
    return this.openIdList;
  }

  public void setOpenIdList(List<String> openIdList) {
    this.openIdList = openIdList;
  }

  public String getNextOpenId() {
    return this.nextOpenId;
  }

  public void setNextOpenId(String nextOpenId) {
    this.nextOpenId = nextOpenId;
  }
}
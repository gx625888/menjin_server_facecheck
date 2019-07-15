package com.threey.guard.wechat.domain;

public class WeixinMedia
{
  private String type;
  private String mediaId;
  private int createdAt;

  public String getType()
  {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMediaId() {
    return this.mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public int getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(int createdAt) {
    this.createdAt = createdAt;
  }
}
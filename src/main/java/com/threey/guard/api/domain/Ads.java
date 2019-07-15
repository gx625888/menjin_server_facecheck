package com.threey.guard.api.domain;

/**
 * <desc>
 * Created by The Moss on 2018/9/14.
 */

public class Ads {

    public static final int TYPE_IMG = 0;
    public static final int TYPE_VIDEO = 1;

    private int type;//0图，1视频
    private  String url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Ads() {
    }

    public Ads(int type, String url) {

        this.type = type;
        this.url = url;
    }
}

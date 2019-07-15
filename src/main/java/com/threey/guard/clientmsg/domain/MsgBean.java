package com.threey.guard.clientmsg.domain;

/**
 * 对应推送表实体类
 */
public class MsgBean {

    //1更新客户端2更新广告3更新用户信息4开门
    public final static int TYPE_APP = 1;
    public final static int TYPE_ADS = 2;
    public final static int TYPE_USER = 3;
    public final static int TYPE_OPEN = 4;
    public final static int TYPE_OPEN_L = 41;
    public final static int TYPE_OPEN_R = 42;
    public final static int TYPE_REBOOT = 5;

    //
    public final static int STATUS_SUCCESS = 1;



    private long id;

    private String deviceId;

    private int msgType;
    private int status;
    private int tryCount;

    private String optUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTryCount() {
        return tryCount;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    @Override
    public String toString() {
        return "MsgBean{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", msgType=" + msgType +
                ", status=" + status +
                ", tryCount=" + tryCount +
                ", optUser='" + optUser + '\'' +
                '}';
    }
}

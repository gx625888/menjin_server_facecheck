package com.threey.guard.tel.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * 呼叫失败 挂机推送
 */
@XmlRootElement(name="request")
public class CallBackResultXmlEntity implements Serializable {
    /**
     *
     *
     *
     *<?xml version="1.0" encoding="UTF-8"?> <request>
     *  <appId>b23abb6d451346efa13370172d1921ef</appId>
     *  <callId>api1234059445aDbbJxIdbT</callId>
     *  <accountSid>c5dc4b87f33ef2ef37c8e974793ad8e5</accountSid>
     *  <caller>18769874345</caller>
     *  <called>13487684854</called>
     *  <callType>0/1/2/3/4/5/7</callType>
     * <type>2</type>
     * <startTime>20151028181023</startTime>
     * <ringDuration>10</ringDuration>
     * <number>1111</number>
     * <workNumber>Emicnet_001</workNumber>
     * <switchNumber>02566699808</switchNumber>
     * <taskId>448</taskId> <batchId>535</batchId>
     * <callerMobile>18769874345</callerMobile>
     * <calledMobile>13487684854</calledMobile>
     * <userData>FE87D3</userData>
     * </request>
     *
     *
     *
     */
    private String appId;
    private String callId;
    private String accountSid;
    private String caller;
    private String called;
    private String callType;
    private String type;
    private String startTime;
    private String ringDuration;
    private String number;
    private String workNumber;
    private String switchNumber;
    private String taskId;
    private String callerMobile;
    private String calledMobile;
    private String userData;

    @XmlElement(name = "appId")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @XmlElement(name = "callId")
    public void setCallId(String callId) {
        this.callId = callId;
    }

    @XmlElement(name = "accountSid")
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    @XmlElement(name = "caller")
    public void setCaller(String caller) {
        this.caller = caller;
    }

    @XmlElement(name = "called")
    public void setCalled(String called) {
        this.called = called;
    }

    @XmlElement(name = "callType")
    public void setCallType(String callType) {
        this.callType = callType;
    }

    @XmlElement(name = "type")
    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "startTime")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @XmlElement(name = "ringDuration")
    public void setRingDuration(String ringDuration) {
        this.ringDuration = ringDuration;
    }

    @XmlElement(name = "number")
    public void setNumber(String number) {
        this.number = number;
    }

    @XmlElement(name = "workNumber")
    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    @XmlElement(name = "switchNumber")
    public void setSwitchNumber(String switchNumber) {
        this.switchNumber = switchNumber;
    }

    @XmlElement(name = "taskId")
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @XmlElement(name = "callerMobile")
    public void setCallerMobile(String callerMobile) {
        this.callerMobile = callerMobile;
    }

    @XmlElement(name = "calledMobile")
    public void setCalledMobile(String calledMobile) {
        this.calledMobile = calledMobile;
    }

    @XmlElement(name = "userData")
    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getAppId() {
        return appId;
    }

    public String getCallId() {
        return callId;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public String getCaller() {
        return caller;
    }

    public String getCalled() {
        return called;
    }

    public String getCallType() {
        return callType;
    }

    public String getType() {
        return type;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getRingDuration() {
        return ringDuration;
    }

    public String getNumber() {
        return number;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public String getSwitchNumber() {
        return switchNumber;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getCallerMobile() {
        return callerMobile;
    }

    public String getCalledMobile() {
        return calledMobile;
    }

    public String getUserData() {
        return userData;
    }

    @Override
    public String toString() {
        return "CallBackResultXmlEntity{" +
                "appId='" + appId + '\'' +
                ", callId='" + callId + '\'' +
                ", accountSid='" + accountSid + '\'' +
                ", caller='" + caller + '\'' +
                ", called='" + called + '\'' +
                ", callType='" + callType + '\'' +
                ", type='" + type + '\'' +
                ", startTime='" + startTime + '\'' +
                ", ringDuration='" + ringDuration + '\'' +
                ", number='" + number + '\'' +
                ", workNumber='" + workNumber + '\'' +
                ", switchNumber='" + switchNumber + '\'' +
                ", taskId='" + taskId + '\'' +
                ", callerMobile='" + callerMobile + '\'' +
                ", calledMobile='" + calledMobile + '\'' +
                ", userData='" + userData + '\'' +
                '}';
    }
}

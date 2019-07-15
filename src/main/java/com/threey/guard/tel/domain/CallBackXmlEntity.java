package com.threey.guard.tel.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="request")
public class CallBackXmlEntity implements Serializable {
    /**
     *
     *
     *
     * <?xml version="1.0" encoding="UTF-8"?>
     *     <request>
     *          <appId>b23abb6d451346efa13370172d1921ef</appId>
     *          <callId>api1234059445aDbbJxIdbT</callId>
     *          <accountSid> c5dc4b87f33ef2ef37c8e974793ad8e5</accountSid>
     *          <caller>18769874345</caller>
     *          <called>13487684854</called>
     *          <keyFeedback>5</keyFeedback>
     *          <type>50</type>
     *          <number>1001</number>
     *          <workNumber>Emicnet_001</workNumber>
     *          <userData>FE87D3</userData>
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
    private String keyFeedback;
    private String type;
    private String number;
    private String workNumber;
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
    @XmlElement(name = "keyFeedback")
    public void setKeyFeedback(String keyFeedback) {
        this.keyFeedback = keyFeedback;
    }
    @XmlElement(name = "type")
    public void setType(String type) {
        this.type = type;
    }
    @XmlElement(name = "number")
    public void setNumber(String number) {
        this.number = number;
    }
    @XmlElement(name = "workNumber")
    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
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

    public String getKeyFeedback() {
        return keyFeedback;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public String getUserData() {
        return userData;
    }

    @Override
    public String toString() {
        return "CallBackXmlEntity{" +
                "appId='" + appId + '\'' +
                ", callId='" + callId + '\'' +
                ", accountSid='" + accountSid + '\'' +
                ", caller='" + caller + '\'' +
                ", called='" + called + '\'' +
                ", keyFeedback='" + keyFeedback + '\'' +
                ", type='" + type + '\'' +
                ", number='" + number + '\'' +
                ", workNumber='" + workNumber + '\'' +
                ", userData='" + userData + '\'' +
                '}';
    }
}

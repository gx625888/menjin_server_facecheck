package com.threey.guard.tel.domain;

import java.io.Serializable;

public class CallResponse implements Serializable {
    private  Resp resp;

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }
   public static class Resp implements  Serializable{
        private int respCode;
        private CallBack callBack;

       public int getRespCode() {
           return respCode;
       }

       public void setRespCode(int respCode) {
           this.respCode = respCode;
       }

       public CallBack getCallBack() {
           return callBack;
       }

       public void setCallBack(CallBack callBack) {
           this.callBack = callBack;
       }
   }

   public static class CallBack implements  Serializable{
       private String callId;
       private String createTime;

       public String getCallId() {
           return callId;
       }

       public void setCallId(String callId) {
           this.callId = callId;
       }

       public String getCreateTime() {
           return createTime;
       }

       public void setCreateTime(String createTime) {
           this.createTime = createTime;
       }
   }
}




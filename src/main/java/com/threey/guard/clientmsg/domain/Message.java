package com.threey.guard.clientmsg.domain;

/**
 * <desc>
 * Created by The Moss on 2018/9/11.
 */

public class Message {
    /*指令*/
    int cmd;
    /*内容体*/
    String content;
    /*成功\失败*/
    int result;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

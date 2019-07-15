package com.threey.guard.clientmsg;

import com.threey.guard.clientmsg.domain.Msg;

/**
 * 消息队列
 */
public interface MsgQue {

    boolean offerMsg(Msg msg);
    Msg pollMsg();

    int queryStatus(String id);

    void setStatus(String id,int status);

    void clearStatus();

}

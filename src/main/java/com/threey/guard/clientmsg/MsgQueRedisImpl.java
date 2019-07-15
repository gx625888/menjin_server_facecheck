package com.threey.guard.clientmsg;

import com.threey.guard.clientmsg.domain.Msg;

/**
 * 使用redis实现消息队列
 */
public class MsgQueRedisImpl implements MsgQue{

    @Override
    public boolean offerMsg(Msg msg) {
        return false;
    }

    @Override
    public Msg pollMsg() {
        return null;
    }

    @Override
    public int queryStatus(String id) {
        return 0;
    }

    @Override
    public void setStatus(String id, int status) {

    }

    @Override
    public void clearStatus() {

    }
}

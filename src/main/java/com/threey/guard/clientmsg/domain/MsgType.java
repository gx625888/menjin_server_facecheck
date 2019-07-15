package com.threey.guard.clientmsg.domain;

public class MsgType {

    /**
     * 连接slave
     */
    public final static int LINK_SLAVE = -100;

    /**
     * 从slave中获取 连接的客户端
     */
    public final static int GET_CLIENT = -101;
    public final static int REMOVE_CLIENT = -103;

    /**
     * 发送消息到客户端
     */
    public final static int PUSH_MSG = -102;


    public final static String SERVER_MSG_TAG = "serverMsg-yuyy:";

}

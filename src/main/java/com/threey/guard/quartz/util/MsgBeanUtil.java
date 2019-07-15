package com.threey.guard.quartz.util;

import com.threey.guard.base.util.JsonUtil;
import com.threey.guard.clientmsg.domain.*;

public class MsgBeanUtil {

    /**
     * 转换消息
     * @param bean
     * @return
     */
    public static Msg parseMsg(MsgBean bean){
        Msg msg = new Msg();
        msg.setCmd(MsgType.PUSH_MSG);
        msg.setDest(bean.getDeviceId());

        Message message = new Message();
        message.setCmd(getCmd(bean.getMsgType()));
        if (message.getCmd()==RequestCmd.OPEN_DOOR
                ||message.getCmd()==RequestCmd.OPEN_DOOR_ONE
                ||msg.getCmd()==RequestCmd.OPEN_DOOR_TWO){//开门需要传送用户信息
            message.setContent(bean.getOptUser());
        }else {
            message.setContent("");
        }
        message.setResult(0);

        NettyBaseInfo<Message> nb = new NettyBaseInfo<>();
        nb.setCmd(NettyBaseInfo.CMD.message);//消息
        if (message.getCmd()==RequestCmd.REBOOT){
            nb.setCmd(3);//重启
        }
        nb.setMsg("");
        nb.setData(message);


        msg.setData(JsonUtil.getJsonString(nb));
        return msg;
    }


    private static int getCmd(int type){
        if (type ==MsgBean.TYPE_APP){
            return RequestCmd.UPDATE_VERSION;
        }
        if (type ==MsgBean.TYPE_ADS){
            return RequestCmd.UPDATE_ADS;
        }
        if (type ==MsgBean.TYPE_USER){
            return RequestCmd.UPDATE_ACCOUNTS;
        }
        if (type ==MsgBean.TYPE_OPEN){
            return RequestCmd.OPEN_DOOR;
        }
        if (type ==MsgBean.TYPE_OPEN_L){
            return RequestCmd.OPEN_DOOR_ONE;
        }
        if (type ==MsgBean.TYPE_OPEN_R){
            return RequestCmd.OPEN_DOOR_TWO;
        }
        if (type == MsgBean.TYPE_REBOOT){
            return RequestCmd.REBOOT;
        }

        return 0;
    }

    public static void main(String[] args) {
        System.out.println(RequestCmd.UPDATE_VERSION);
        System.out.println(RequestCmd.UPDATE_ADS);
        System.out.println(RequestCmd.UPDATE_ACCOUNTS);
        System.out.println(RequestCmd.OPEN_DOOR);
        System.out.println(RequestCmd.REBOOT);
    }
}

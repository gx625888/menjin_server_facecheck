package com.threey.guard.clientmsg;

import com.threey.guard.base.util.StringUtil;
import com.threey.guard.clientmsg.domain.Msg;
import com.threey.guard.clientmsg.domain.MsgType;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Executors;

/**
 * 发送消息工具类
 */
public class MsgUtil {

    private static MsgQue que = new MsgQueJdkQueImpl();
    private static boolean initable = true;

    /**
     * 发送消息
     * @param msg
     * @return  int 1 发送成功 其他发送失败
     */
    public static int  send(Msg msg){
        String id = StringUtil.getUuid();
        msg.setId(id);
        que.offerMsg(msg);
       return queryMsgStatus(id);
    }

    /**
     * 查询消息接收信息状态
     * @param msgId 消息id
     * @return
     */
    private static int queryMsgStatus(String msgId){
        return que.queryStatus(msgId);
    }

    public static  void updateMsgStatus(String msgId,int status){
        que.setStatus(msgId,status);
    }

    public static void init(){
        synchronized (MsgUtil.class){
            if (initable){
                    new Thread(new sendTask()).start();
                    initable = false;
            }
        }
    }

    public static void dispatchMsg(String[] msgarr,ChannelHandlerContext ctx){
        try{
            int optType = Integer.parseInt(msgarr[0]);
            switch (optType){
                case MsgType.GET_CLIENT://客户端注册
                    for (int i=1;i<msgarr.length;i++){
                        MsgRouteFact.instance.getInstance().registerClient(ctx.channel().id().asLongText(),msgarr[i]);
                    }

                    break;
                case MsgType.PUSH_MSG://更新消息状态
                    updateMsgStatus(msgarr[1],Integer.parseInt(msgarr[2]));
                    break;
                case MsgType.REMOVE_CLIENT://客户端离线
                    MsgRouteFact.instance.getInstance().unRegisterClient(msgarr[1]);
                    break;

                default:break;
            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    static class sendTask implements Runnable{

        @Override
        public void run() {
            while(true){
                Msg msg = que.pollMsg();
                if (null!=msg){
                    int result = MsgRouteFact.instance.getInstance().send(msg);
                    if (result!=0){//发送失败
                        MsgUtil.updateMsgStatus(msg.getId(),-1);
                    }
                    que.clearStatus();

                }
            }
        }
    }

}

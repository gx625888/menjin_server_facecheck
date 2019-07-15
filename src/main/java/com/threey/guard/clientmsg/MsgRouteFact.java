package com.threey.guard.clientmsg;

import com.google.gson.Gson;
import com.threey.guard.clientmsg.domain.Msg;
import com.threey.guard.clientmsg.domain.MsgType;
import io.netty.channel.ChannelHandlerContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * 消息路由器
 */
public enum MsgRouteFact {

    instance;

    private MsgRoute msgRoute;
    private MsgRouteFact(){
        msgRoute = new MsgRoute();

    }
    public MsgRoute getInstance(){
        return msgRoute;
    }

    public class MsgRoute{
        private MsgRoute(){

        }

        private  boolean initable = true;
        private  Map<String,ChannelHandlerContext> slaveMap = new ConcurrentHashMap<>();

        private Map<String ,String> slaveIdMap = new ConcurrentHashMap<>();

        private Map<String , Set<String>> clients = new ConcurrentHashMap<>();

        private Set<String> disConectedSlaves = new HashSet<>();

        private Gson gson = new Gson();

        public synchronized  void saveDisconectedSlave(String key){
            disConectedSlaves.add(key);
        }

        public synchronized void registerSlave(ChannelHandlerContext channelHandlerContext,String key){
            this.slaveMap.put(key,channelHandlerContext);
            this.clients.put(key,new HashSet<String>());
            this.disConectedSlaves.remove(key);
            slaveIdMap.put(channelHandlerContext.channel().id().asLongText(),key);
        }

        public synchronized void unRegisterSlave(String key,String channelId){
            this.slaveMap.remove(key);
            this.clients.remove(key);
            this.slaveIdMap.remove(channelId);
        }

        public synchronized void registerClient(String key ,String clientId){
            key = slaveIdMap.get(key);

            if (null!=clients.get(key)){
                clients.get(key).add(clientId);
            }
        }
        public synchronized void registerClients(String key ,Set<String> clientIds){
            if (null!=clients.get(key)){
                clients.get(key).addAll(clientIds);
            }
        }

        public synchronized void unRegisterClient(String client){
            String key = findClient(client);
            if(null!=key){
                this.clients.get(key).remove(client);
            }
        }

        public void updateMsgStatus(String msgId ,int status){
            MsgUtil.updateMsgStatus(msgId,status);
        }

        public  String findClient(String clientId){
            for (Map.Entry<String, Set<String>> entry : clients.entrySet()) {
               if (entry.getValue().contains(clientId)){
                   return entry.getKey();
               }
            }
            return null;
        }

        public List<String> getAllClient(){
            Set<String> result = new HashSet<>();
            for (Map.Entry<String, Set<String>> entry : clients.entrySet()) {
                result.addAll(entry.getValue());
            }
            return new ArrayList<>(result);
        }
        public Map<String,Object> info(){
            Map<String,Object> remap = new HashMap<>();
            remap.put("slaves",slaveIdMap);
            remap.put("slavesinf2",slaveMap);
            remap.put("clients",clients);
            return remap;
        }

        /**
         * 查询客户端在线状态
         * @param clientId
         * @return true在线 false 不在线
         *
         */
        public boolean isClientOnline(String clientId){
            return findClient(clientId)!=null;
        }
        /**
         * 发送消息 msg
         * @param msg
         * @return 0成功 其它失败
         */
        public int send(Msg msg){
            System.out.println("push-log-tag-->dest:"+msg.getDest()+"&msg:"+msg.getData());
            String key = findClient(msg.getDest());
            if (null==key){
                System.out.println("push-log-tag-->dest:"+msg.getDest()+"没有在线");
                return 1;//客户端已离线 未查找到
            }
            ChannelHandlerContext chc = this.slaveMap.get(key);
            chc.channel().writeAndFlush(MsgType.SERVER_MSG_TAG +gson.toJson(msg)+"\n");
            return 0;
        }

        /**
         * 心跳线程
         */
        public void initHartBeat() {
            synchronized (MsgRoute.class) {
                if (initable) {
                    new Thread(new HartBeatJob()).start();
                    initable = false;

                }
            }
        }

        class HartBeatJob implements Runnable{

            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10000);
                        /**
                         * 心跳
                         */
                        for (Map.Entry<String,ChannelHandlerContext> entry : slaveMap.entrySet()) {
                            entry.getValue().channel().writeAndFlush(MsgType.SERVER_MSG_TAG+"{cmd:0}\n");
                        }

                        //尝试重新连接断开的连接
                        for (String key :disConectedSlaves){
                            System.out.println(key+"重连!!!!!!");
                            if (!clients.containsKey(key)){
                                String[] temp = key.split(":");
                                new NettyClient(temp[0],Integer.parseInt(temp[1])).start();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }

}

package com.threey.guard.clientmsg;


import com.threey.guard.clientmsg.domain.Msg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MsgQueJdkQueImpl implements MsgQue{
    private static ArrayBlockingQueue<Msg> que = new ArrayBlockingQueue<>(2000);
    private static Map<String,Integer> resultMap = new ConcurrentHashMap<>();

    @Override
    public boolean offerMsg(Msg msg) {

        return que.offer(msg);
    }

    @Override
    public Msg pollMsg() {
        try {
            return que.poll(1000,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int queryStatus(String id) {
        try {
            int wait = 500;
            int maxcount =6;
            int count =0 ;
            while (count++<maxcount){
                Thread.sleep(wait);
                Integer status = resultMap.get(id);
                if (null!=status){
                    resultMap.remove(id);
                    return status;
                }
            }
            resultMap.put(id,-100);//需要被清除的状态
            return -1;

        }catch(Exception e){
            return -1;//unkonw
        }
    }

    @Override
    public void setStatus(String id, int status) {
        if (resultMap.containsKey(id)){//弃用的消息状态
            resultMap.remove(id);
        }else{
            resultMap.put(id,status);
        }

    }

    /**
     * 清理status里-100的数据
     */
    @Override
    public void clearStatus() {
        if (resultMap.size()>2000){
            Iterator<Map.Entry<String, Integer>> it = resultMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String,Integer> entry = it.next();
                if (entry.getValue()==-100){
                    it.remove();
                }
            }
        }

    }
}

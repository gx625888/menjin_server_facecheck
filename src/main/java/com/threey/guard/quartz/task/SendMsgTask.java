package com.threey.guard.quartz.task;

import com.threey.guard.base.util.CtxFactory;
import com.threey.guard.base.util.JsonUtil;
import com.threey.guard.clientmsg.MsgUtil;
import com.threey.guard.clientmsg.dao.SendMsgDao;
import com.threey.guard.clientmsg.domain.*;
import com.threey.guard.quartz.util.MsgBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class SendMsgTask implements Job {
    private static final Logger logger = Logger.getLogger(SendMsgTask.class);

    private int limit =20;
    private int tryCount =3;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SendMsgDao dao = CtxFactory.getCtx().getBean(SendMsgDao.class);

        List<MsgBean> msgs = dao.queryMsgs(limit,tryCount);

        if (CollectionUtils.isNotEmpty(msgs)){
            dealMesseges(msgs);
            dao.updateStatus(msgs);

        }else {
            logger.info("没有查询到需要推送的消息!!!!!");
        }
    }

    private void dealMesseges(List<MsgBean> msgs){

        for (MsgBean msg:msgs) {
            dealMessege(msg);
        }
    }

    private void dealMessege(MsgBean msg){
        logger.info("处理推送消息:"+msg);
        msg.setTryCount(msg.getTryCount()+1);
        msg.setStatus( MsgUtil.send(MsgBeanUtil.parseMsg(msg)));
    }





}

package com.threey.guard.quartz.task;

import com.threey.guard.base.util.CtxFactory;
import com.threey.guard.clientmsg.MsgRouteFact;
import com.threey.guard.clientmsg.MsgUtil;
import com.threey.guard.clientmsg.dao.SendMsgDao;
import com.threey.guard.clientmsg.domain.MsgBean;
import com.threey.guard.quartz.util.MsgBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class DeviceOnlineStatusTask implements Job {
    private static final Logger logger = Logger.getLogger(DeviceOnlineStatusTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SendMsgDao dao = CtxFactory.getCtx().getBean(SendMsgDao.class);
        dao.deleteOnlineDevice();
        dao.insertOnlineDevice(MsgRouteFact.instance.getInstance().getAllClient());
    }
}

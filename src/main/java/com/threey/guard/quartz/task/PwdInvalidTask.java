package com.threey.guard.quartz.task;

import com.threey.guard.api.dao.ClientApiDao;
import com.threey.guard.base.util.CtxFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 密码过期
 */
public class PwdInvalidTask implements Job {

    private int time =3 ;//过期时间 单位分钟
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("更新过期密码!!!!!");
        ClientApiDao dao = CtxFactory.getCtx().getBean(ClientApiDao.class);
        dao.invalidPwd(time);
    }
}

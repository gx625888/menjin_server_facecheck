package com.threey.guard.quartz.task;

import com.threey.guard.api.dao.ClientApiDao;
import com.threey.guard.base.util.CtxFactory;
import com.threey.guard.wechat.dao.ManagerWxBandDao;
import com.threey.guard.wechat.domain.WeChatPropertis;
import com.threey.guard.wechat.util.AppTokenUtil;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * 刷新微信token
 */
public class AccessTokenFreshTask implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("刷新微信token!!!!!");
        ManagerWxBandDao managerWxBandDao = CtxFactory.getCtx().getBean(ManagerWxBandDao.class);

        List<WeChatPropertis> list = managerWxBandDao.getAllWeChatPropertis();

        if (CollectionUtils.isEmpty(list)){
            return;
        }

        for (WeChatPropertis p:list) {
            AppTokenUtil.freshToken(p);
        }

    }
}

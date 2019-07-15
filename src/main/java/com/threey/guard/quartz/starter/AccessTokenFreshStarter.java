package com.threey.guard.quartz.starter;

import com.threey.guard.base.util.GETProperties;
import com.threey.guard.quartz.task.AccessTokenFreshTask;
import com.threey.guard.quartz.task.PwdInvalidTask;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 过期密码
 */
@Component
public class AccessTokenFreshStarter {

    private static final Logger logger = Logger.getLogger(AccessTokenFreshStarter.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private static int delayTime = 50;// 延时 单位秒

    public void init(){

        if (!"debug".equals(GETProperties.readValue("wechat.state"))) {//不是微信公众号环境不启动此任务 ,此任务最好只启动一个
            logger.info("刷新微信token启动");
            startTask();
        }
    }

    public void startTask(){
        try {
            logger.info("------刷新微信token启动startTask方法!------");
            //创建任务
            JobDetail job = JobBuilder.newJob(AccessTokenFreshTask.class).withIdentity("AccessTokenFreshTask").build();
            Date startTime = DateBuilder.nextGivenSecondDate(null, delayTime);

            StringBuilder cron = new StringBuilder("0 0 0/1 * * ? ");//每小时秒执行


            logger.info("------cron表达式：" + cron +"------");
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("AccessTokenFreshTaskTrigger")
                    .startAt(startTime)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron.toString()))
                    .build();

            //加到日程(schedule)中
            Scheduler sd = schedulerFactoryBean.getScheduler();
            sd.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.info("------刷新微信tokenstartTask方法出现异常!------");
            e.printStackTrace();
        }
    }
}

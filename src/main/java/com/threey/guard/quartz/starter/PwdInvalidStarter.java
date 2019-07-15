package com.threey.guard.quartz.starter;

import com.threey.guard.base.util.GETProperties;
import com.threey.guard.quartz.task.PwdInvalidTask;
import com.threey.guard.quartz.task.SendMsgTask;
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
public class PwdInvalidStarter {

    private static final Logger logger = Logger.getLogger(PwdInvalidStarter.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private static int delayTime = 30;// 延时 单位秒

    public void init(){

        if (!"debug".equals(GETProperties.readValue("wechat.state"))) {//不是微信公众号环境不启动此任务 ,此任务最好只启动一个
            logger.info("向客户端推送消息启动");
            startTask();
        }
    }

    public void startTask(){
        try {
            logger.info("------向客户端推送消息启动startTask方法!------");
            //创建任务
            JobDetail job = JobBuilder.newJob(PwdInvalidTask.class).withIdentity("PwdInvalidTask").build();
            Date startTime = DateBuilder.nextGivenSecondDate(null, delayTime);

            StringBuilder cron = new StringBuilder("0 0/1 * * * ? ");//每30秒执行


            logger.info("------cron表达式：" + cron +"------");
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("PwdInvalidTaskTrigger")
                    .startAt(startTime)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron.toString()))
                    .build();

            //加到日程(schedule)中
            Scheduler sd = schedulerFactoryBean.getScheduler();
            sd.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.info("------向客户端推送消息startTask方法出现异常!------");
            e.printStackTrace();
        }
    }
}

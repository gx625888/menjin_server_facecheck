package com.threey.guard.quartz.starter;

import com.threey.guard.base.util.GETProperties;
import com.threey.guard.quartz.task.DeviceOnlineStatusTask;
import com.threey.guard.quartz.task.SendMsgTask;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 向客户端推送消息
 */
@Component
public class DeviceStatusStarter {

    private static final Logger logger = Logger.getLogger(DeviceStatusStarter.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private static int delayTime = 59;// 延时 单位秒

    public void init(){
        if(Boolean.parseBoolean(GETProperties.readValue("slave.push.job"))) {//配置文件配置启动 此任务只能启动一个
            logger.info("向客户端推送消息启动");
            startTask();
        }


    }

    public void startTask(){
        try {
            logger.info("------向客户端推送消息启动startTask方法!------");
            //创建任务
            JobDetail job = JobBuilder.newJob(DeviceOnlineStatusTask.class).withIdentity("DeviceOnlineStatusTask").build();
            Date startTime = DateBuilder.nextGivenSecondDate(null, delayTime);

            StringBuilder cron = new StringBuilder("0 0/3 * * * ? *");//每3分钟执行


            logger.info("------cron表达式：" + cron +"------");
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("DeviceOnlineStatusTaskTrigger")
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

package com.threey.guard.quartz.starter;

import com.threey.guard.quartz.task.TestTask;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 测试定时任务启动器
 */
@Component
public class TestStarter {
    private static final Logger logger = Logger.getLogger(TestStarter.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private static int delayTime = 10;// 延时 单位秒

    public void init(){
       logger.info("测试任务启动");
       startTask();

    }

    public void startTask(){
        try {
            logger.info("------测试任务启动startTask方法!------");
            //创建任务
            JobDetail job = JobBuilder.newJob(TestTask.class).withIdentity("testJob").build();
            Date startTime = DateBuilder.nextGivenSecondDate(null, delayTime);

            StringBuilder cron = new StringBuilder("0 0/1 * * * ? ");


            logger.info("------cron表达式：" + cron +"------");
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("testJobTrigger")
                    .startAt(startTime)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron.toString()))
                    .build();

            //加到日程(schedule)中
            Scheduler sd = schedulerFactoryBean.getScheduler();
            sd.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.info("------测试任务启动startTask方法出现异常!------");
            e.printStackTrace();
        }
    }
}

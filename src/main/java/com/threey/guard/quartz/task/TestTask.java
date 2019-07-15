package com.threey.guard.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 测试定时任务
 */
public class TestTask implements Job {
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("test :"+new Date());
    }
}

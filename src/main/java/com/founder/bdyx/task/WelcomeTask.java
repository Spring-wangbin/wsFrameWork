package com.founder.bdyx.task;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;


/**
* @Description 测试job
* @author yang.xuefeng
* @version 创建时间：2019年12月24日 下午6:00:36
*/

@Service
public class WelcomeTask implements Job {


    public void sayWelcome() throws Exception {
        System.out.println("sayWelcome");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("WelcomeTask");
    }
}

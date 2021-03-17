package com.founder.bdyx.task;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.stereotype.Component;

/**
* @Description 测试job
* @author yang.xuefeng
* @version 创建时间：2019年12月24日 下午6:00:36
*/
@Component
public class WelcomeJob implements Job{
	/*@Autowired
	SimpMessagingTemplate template;*/

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("welcomeJob");
    }

}
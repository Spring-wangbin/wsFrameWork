package com.founder.bdyx.task;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.task.domain.QueryLogMode;
import com.founder.bdyx.webservice.core.domain.WebsvrLog;
import com.founder.bdyx.webservice.core.mapper.WebsvrLogMapper;

/**
* @Description 测试job
* @author yang.xuefeng
* @version 创建时间：2019年12月24日 下午6:00:36
*/
@Component
public class AutoSyncDataTask implements Job{
    @Autowired
    WebsvrLogMapper logMapper;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
//        System.out.println("welcomeJob");
//    	List<WebsvrConfig> list = wsService.getConfig("APC_QRY_doctor");
    	QueryLogMode query = new QueryLogMode();
    	
    	query.setQueryDate(new Date());;
    	
    	query.setDeleteFlag(0);
    	
    	List<Map<String,Object>> dataRs = logMapper.selectLogByDate(query);
    	
    	System.out.println(" size "  + dataRs.size());
    	
    	
    	List<WebsvrLog> logList = logMapper.queryWbLogByDate(query);
    	
    	System.out.println(" logListJson "  + JSON.toJSONString(logList));
    }

}
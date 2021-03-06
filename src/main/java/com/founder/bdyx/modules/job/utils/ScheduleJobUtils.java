package com.founder.bdyx.modules.job.utils;


import com.founder.bdyx.modules.job.model.SysTask;
import com.founder.bdyx.modules.job.vo.ScheduleJob;
import org.springframework.beans.BeanUtils;

public class ScheduleJobUtils {
	public static ScheduleJob entityToData(SysTask scheduleJobEntity) {
		ScheduleJob scheduleJob = new ScheduleJob();
		/*scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getDescription());
		scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
		scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		scheduleJob.setSpringBean(scheduleJobEntity.getSpringBean());*/
		BeanUtils.copyProperties(scheduleJobEntity,scheduleJob);
		return scheduleJob;
	}
}
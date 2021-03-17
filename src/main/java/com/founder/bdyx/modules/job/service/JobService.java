package com.founder.bdyx.modules.job.service;

import com.founder.bdyx.modules.job.model.SysTask;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @author yang.xuefeng
 */
public interface JobService {
	
	SysTask get(Long id);

	int save(SysTask taskScheduleJob);
	
	int update(SysTask taskScheduleJob);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	void initSchedule() throws SchedulerException;

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId) throws SchedulerException;

	List<SysTask> queryPage(SysTask sysTask,String keyword);
}

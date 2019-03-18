package com.army.armyadmin.job.service;

import com.army.armyadmin.common.service.IService;
import com.army.armyadmin.job.domain.JobLog;

import java.util.List;

public interface JobLogService extends IService<JobLog>{

	List<JobLog> findAllJobLogs(JobLog jobLog);

	void saveJobLog(JobLog log);
	
	void deleteBatch(String jobLogIds);
}

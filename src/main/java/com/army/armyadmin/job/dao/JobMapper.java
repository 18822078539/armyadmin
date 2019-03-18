package com.army.armyadmin.job.dao;

import com.army.armyadmin.common.config.MyMapper;
import com.army.armyadmin.job.domain.Job;

import java.util.List;

public interface JobMapper extends MyMapper<Job> {
	
	List<Job> queryList();
}
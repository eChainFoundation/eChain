package com.echain.service.impl;

import org.springframework.stereotype.Service;

import com.echain.helper.datasource.DataSourceContextHolder;
import com.echain.helper.datasource.DataSourceType;
import com.echain.service.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {

	@Override
	public boolean checkLogisticsRecord() {
		DataSourceContextHolder.setDbType(DataSourceType.DATA_SOURCE_1);
		return false;
	}

}

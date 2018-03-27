package com.echain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echain.dao.LogisticsRecordDao;
import com.echain.entity.LogisticsRecord;
import com.echain.helper.datasource.DataSourceContextHolder;
import com.echain.helper.datasource.DataSourceType;
import com.echain.service.ITransferDataService;

@Service
public class TransferDataServiceImpl implements ITransferDataService {

	@Autowired
	private LogisticsRecordDao recordDao;
	
	@Override
	public Boolean transferData(Long id) {
		Boolean bool = false;
		if(id != null && id > 0) {
			DataSourceContextHolder.setDbType(DataSourceType.DATA_SOURCE_1);
			LogisticsRecord record = this.recordDao.selectByPrimaryKey(id);
			if(record != null) {
				DataSourceContextHolder.setDbType(DataSourceType.DATA_SOURCE_2);
				Integer i = this.recordDao.insertSelective(record);
				if(i > 0)
					bool = true;
			}
		}
		
		return bool;
	}

}

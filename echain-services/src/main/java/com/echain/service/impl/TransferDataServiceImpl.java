package com.echain.service.impl;

import org.springframework.stereotype.Service;

import com.echain.service.ITransferDataService;

@Service
public class TransferDataServiceImpl implements ITransferDataService {

	
	@Override
	public Boolean transferData(Long id) {
		Boolean bool = false;
		if(id != null && id > 0) {
		}
		
		return bool;
	}

}

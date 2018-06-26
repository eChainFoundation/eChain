package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcUserDao extends MyMapper<EcUser> {

	EcUser selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	List<EcUser> selectSingleUploadUsers();
}
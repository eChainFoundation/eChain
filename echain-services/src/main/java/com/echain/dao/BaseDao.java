package com.echain.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.echain.entity.BaseEntity;
import com.echain.helper.TranscationExecutor;

public abstract class BaseDao<BeanClass extends BaseEntity> {

	protected static Logger LOGGER = Logger.getLogger(BaseDao.class); 
	
	@Autowired
	private SqlSessionTemplate sqlSession; 

	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	} 
	
	public Integer deleteByPrimaryKey(Long id) { 
		return this.getSqlSession().delete(getClassName().concat("deleteByPrimaryKey"), id);
	}
	
	
	public Integer cleanTable() { 
		return this.getSqlSession().delete(getClassName().concat("cleanTable"), 0);
	} 
	
	public Integer insert(BeanClass record) { 
		return this.getSqlSession().insert(getClassName().concat("insert"), record);
	}

	public Integer update(BeanClass record){
		return this.updateByPrimaryKeySelective(record);
	};
	  
	public Integer saveOrUpdate(BeanClass record){
		if(record.getId() == null){
			return this.insertSelective(record);
		}else{
			return this.updateByPrimaryKeySelective(record);
//			return this.updateByPrimaryKey(record);
		}
	};
	
	    
	public Integer insertSelective(BeanClass record) {
		return this.getSqlSession().insert(getClassName().concat("insertSelective"), record);
	}
 
	public BeanClass selectByPrimaryKey(Long id) {
		return this.getSqlSession().selectOne(getClassName().concat("selectByPrimaryKey"), id);
	} 
	 
	public Integer updateByPrimaryKeySelective(BeanClass record) { 
		return this.getSqlSession().update(getClassName().concat("updateByPrimaryKeySelective"), record);
	}
	
	public Integer updateByPrimaryKey(BeanClass record) { 
		return this.getSqlSession().update(getClassName().concat("updateByPrimaryKey"), record);
	}
	
	public abstract String getClassName();
	
	public List<BeanClass> selectByIdList(List<Long> idList) {
		if(idList == null || idList.isEmpty()){
			return new ArrayList<BeanClass>();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		return this.getSqlSession().selectList(getClassName()+"selectByIdList", map);
	}
	
	public List<BeanClass> listAll() { 
		return this.getSqlSession().selectList(getClassName()+"listAll");
	}
	
	public List<BeanClass> listSiteBaseAll() { 
		return this.getSqlSession().selectList(getClassName()+"listSiteBaseAll");
	}
	
	public Integer executeOriginalSql(final String statement){
		TranscationExecutor<Integer> executor = new TranscationExecutor<Integer>(){
			public Integer execute(){
			    Statement stmt = null;   
		        try {    
		            Connection conn =  getConnection();    
		            stmt = conn.createStatement();  
//		            LOGGER.debug("execute sql statement:" + statement);  
		           return  stmt.executeUpdate(statement);   
		        } catch (Exception ex) {    
		        	LOGGER.error(ex.getMessage(), ex);
		        }finally{    
		            try {
						stmt.close();
						stmt = null;
					} catch (Exception e) { 
					}    
		        }  
		        return 0; 
			}
		};
		
		return executor.run();
	}
}

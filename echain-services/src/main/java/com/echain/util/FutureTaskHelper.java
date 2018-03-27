package com.echain.util;

import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;  
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 




public class FutureTaskHelper {
	
	ScheduledExecutorService Threadpool = Executors.newScheduledThreadPool(10);
	protected static final Logger LOGGER = LoggerFactory.getLogger(FutureTaskHelper.class);
	
	private static  FutureTaskHelper helper = new FutureTaskHelper();
	 
	private FutureTaskHelper(){
		
	}
	
	class TaskReturn<RETURN> {
		RETURN rs ;
		Exception e;
	}
	
	/**
	 * 不进入 线程池的任务
	 * @param task
	 * @param millSeconds
	 * @return
	 * @throws Exception
	 */
	public <RETURN> RETURN executeUrgentTask(Task<RETURN> task,Integer millSeconds) throws Exception { 
	/*	FutureTaskHelper.getInstance().addTask(task); 
		RETURN result = FutureTaskHelper.getInstance().getReturnValue(task,millSeconds); */
		
		return executeTask(task,millSeconds,true);
	}
	
	
	/**
	 *运行http任务 超时就认为是网络超时
	 * @param task
	 * @param millSeconds
	 * @return
	 * @throws Exception
	 */
	public <RETURN> RETURN executeHttpUrgentTask(Task<RETURN> task,Integer millSeconds) throws Exception { 
 
		try { 
			RETURN rs = executeUrgentTask(task, millSeconds);
			return rs;
		} catch (java.util.concurrent.TimeoutException e) {
			 LOGGER.error(e.getMessage(), e);
			 throw new SocketTimeoutException(e.getMessage());
		}
	}
	
	private <RETURN> RETURN executeTask(final Task<RETURN> task,Integer millSeconds ,boolean isUrgent) throws Exception{
		FutureTask<TaskReturn<RETURN>> future =   
			       new FutureTask<TaskReturn<RETURN>>(new Callable<TaskReturn<RETURN>>() {//使用Callable接口作为构造参数   
			         public TaskReturn<RETURN> call() {  
			        		TaskReturn<RETURN> rs = new TaskReturn<RETURN>();
			        		try { 
			        			RETURN result = task.execute();
			        			rs.rs = result; 
							} catch (Exception e) {
								 rs.e = e;
							}  
			        		return rs;
	    }}); 
	    Thread t = new Thread(future);
	    if(isUrgent){
	        t.start();//紧急任务直接开始
	    }else{
	    	Threadpool.submit(t);//非紧急任务进入线程池
	    }
	
		try { 
			TaskReturn<RETURN> rs = future.get(millSeconds,TimeUnit.MILLISECONDS);
			if (rs.e != null) {
				future.cancel(true); 
				throw rs.e;
			}
			return rs.rs;
		} catch (Exception e) {
			 throw new SocketTimeoutException(e.getMessage());
		}finally{
			if(t.isAlive()){
				try {
					t.interrupt();
				} catch (Throwable e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 运行不太紧急的任务
	 * 进入线程池调度
	 * @param task
	 * @param millSeconds
	 * @return
	 * @throws Exception
	 */
	public <RETURN> RETURN executeUnurgentTask(Task<RETURN> task,Integer millSeconds) throws Exception { 
		 
		return executeTask(task,millSeconds,false);
	}
	/**
	 * 运行不抛出异常的非紧急任务
	 * @param task
	 * @param millSeconds
	 * @return
	 */
	public String executeUnurgentTaskWithoutException(Task<String> task,Integer millSeconds) { 
	 
	    String result;
		try {
			result =executeUnurgentTask(task,millSeconds);
		} catch (Exception e) {
			result = null;
		}
	    return result;
	}
	/**
	 * 运行不抛出异常的紧急任务
	 * @param task
	 * @param millSeconds
	 * @return
	 */
	public  <RETURN> RETURN executeUrgentTaskWithoutException(Task<RETURN> task,Integer millSeconds) { 
	 
		RETURN result;
		try {
			result =executeHttpUrgentTask(task,millSeconds);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			result = null;
		}
	    return result;
	}
	
	public static FutureTaskHelper getInstance(){
		 return helper;
	}
	
	public abstract static  class Task<RETURN>{
		public abstract RETURN execute() throws Exception; 
	}
	
	
 
	public static void main(String[] args) throws Exception { 
	 
	    String result = FutureTaskHelper.getInstance().executeUrgentTask(	new FutureTaskHelper.Task<String>(){ 
			@Override
			public String execute() throws Exception {
				//throw new Exception("");
				Thread.sleep(300);
				return "HELLO";
			} 
		}, 1000);
	    System.out.println(result);
	  //  System.exit(0);
	}
	
}

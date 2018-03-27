package com.echain.helper;
 
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock; 

import org.apache.log4j.Logger; 

/**
 * 缓存服务 
 * 只缓存某些简单的
 * @author wen
 *
 */
public class CacheHelper {
	public static Logger LOGGER = Logger.getLogger(CacheHelper.class);
	private class CodeBean{
		
		private Object code ;//验证码
		
		private Date datetime;//有效期
	}

	private static CacheHelper codeService =null;
	private  Timer timer   = new Timer(true);
	private CacheHelper (){
	   
		 
	}
	public static synchronized CacheHelper get(){
		if(codeService ==null){
			codeService = new CacheHelper();
			codeService.initCleanTask();
		}
		return codeService;
	}
	
	private void initCleanTask(){
		long period =  10*1000L;//多久检查一次
		timer.schedule( new RemindTask(),0, period);
	}
	
	class RemindTask extends TimerTask {
        public void run() {  
            removeUseLess(); 
        }
    }
	
	
	private Map<String,CodeBean> codeMap = new ConcurrentHashMap<String,CodeBean>();
	
	private  ReadWriteLock lock = new ReentrantReadWriteLock(false);//读写锁
	/**
	 * 根据key 到一个code
	 * @param emailOrPhoneNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCachedValue(String key){
		key = processValue(key);
		lock.readLock().lock(); 
		CodeBean bean = this.codeMap.get(key);
		Object code = null;
		if(bean == null){
			code= null;
		}else{
			if(bean.datetime.getTime() >= System.currentTimeMillis()){//还在有效期内
				code= bean.code;
			}else{
				code= null;
			    this.codeMap.remove(key);
			}
			
		}
		lock.readLock().unlock();
		return (T)code;
	}
	/**
	 * 删除code 避免太多
	 * @param emailOrPhoneNumber
	 */
	public void removeCachedValue(String key){
		key = processValue(key);
		lock.writeLock().lock();
	     this.codeMap.remove(key);
		lock.writeLock().unlock();
	}
	/**
	 * 生成一个验证码
	 * @param key
	 * @return
	 */
	public String generateCode(String key,Integer validTimeInMill){
		key = processValue(key);
		String code = this.getCachedValue(key);
		lock.writeLock().lock();
		if(code == null){ 
			code = this.randomGenerateCode();
		    this.putCustomizedCode(key, code, validTimeInMill);
		}
		lock.writeLock().unlock();
		return code;
	}
	
 
	
	/**
	 *生成一个定制的验证码
	 *默认是10分钟
	 * @param key
	 * @return
	 */
	public boolean putCustomizedCode(String key,String code){ 
		 return this.putCustomizedCode(key, code, 10*60*1000);
	}
	
	/**
	 *生成一个定制的验证码
	 * @param emailOrPhoneNumber
	 * @return
	 */
	public boolean putCustomizedCode(String key,Object code,Integer validTimeInMill){ 
		lock.writeLock().lock();
		key = processValue(key);
		if(code != null){  
			CodeBean bean = new CodeBean();
			bean.code = code; 
		    bean.datetime =new Date(new Date().getTime()+validTimeInMill);//有效期 
			this.codeMap.put(key, bean);
		}
		lock.writeLock().unlock();
		return true;
	}
	
	/**
	 *如果不存在key 就放入一个value 操作返回true 否则为false 
	 * @param emailOrPhoneNumber
	 * @return
	 */
	public boolean putCustomizedCodeIsNotExist(String key,Object code,Integer validTimeInMill){ 
		lock.writeLock().lock();
		try {
			 key = processValue(key);
			if (this.getCachedValue(key)!=null){
				return false;
			}else{
				putCustomizedCode(key,code,validTimeInMill);
			} 
		 
		} finally   {
			lock.writeLock().unlock();
		}
	
		return true;
	}
	
	private String processValue(String value ){
	 
		return    value.toLowerCase().trim();
	}
	/**
	 * 删除过了有效期的验证码
	 */
	private void removeUseLess(){ 
		lock.writeLock().lock();
		LOGGER.debug("map size:"+this.codeMap.size());
		Iterator<String> iter = this.codeMap.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			CodeBean bean = this.codeMap.get(key);
			if(bean.datetime.getTime() < System.currentTimeMillis()){
			 iter.remove(); 
			}
		}
		
		lock.writeLock().unlock();
	}
 
	
	public String randomGenerateCode(){
		char[] chars = "0123456789".toCharArray();
		StringBuilder builder = new StringBuilder("");
		for(int i =0 ; i< 6;i++){
			builder.append(new Random().nextInt(chars.length));
		}
		return builder.toString();
	}
	 
	public static void main(String[] args) throws Exception {
		String key = "1";
		CacheHelper.get().putCustomizedCode(key, "2", 3000);
		/*System.out.println(CacheHelper.get().getCachedValue(key));
		
		System.out.println();*/
	}

}

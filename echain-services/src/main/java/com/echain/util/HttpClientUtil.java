package com.echain.util;

import java.nio.charset.Charset;
import java.util.List;

//import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;



// import com.rock.framework.core.Log;

/**
 * Description:该类主要用来模拟http post 请求，
 * DateTime: 2016/6/17 10:56
 */

public class HttpClientUtil {

	protected Logger logger = Logger.getLogger(this.getClass());

	private static int timeout = 10000;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * 发送GET请求
	 */
	public static String doGetSimple(String url) throws Exception {

		String resData = "";
		try (CloseableHttpClient httpclient = HttpClients.createMinimal(new BasicHttpClientConnectionManager());) {
			// 创建默认的httpClient实例.

			// 创建HttpGet
			HttpGet httpget = new HttpGet(url);
			// ---------设置超时时间---------
			// RequestConfig config = RequestConfig.custom()
			// .setSocketTimeout(3000)
			// .setConnectTimeout(3000)
			// .build();
			// httpget.setConfig(config);
			// ---------设置超时时间---------

			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);

			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 请求结束，返回结果
			if (entity != null) {
				resData = EntityUtils.toString(entity, Charset.forName("UTF-8"));
			}
		} catch (Exception e) {
			throw e;
		}
		return resData;
	}

	/**
	 * 发送GET请求
	 */
	public static String doGet(String url) throws Exception {

		String resData = "";
		try (CloseableHttpClient httpclient = HttpClients.createMinimal(new BasicHttpClientConnectionManager());) {
			// 创建默认的httpClient实例.
			// httpclient = HttpClients.createDefault();

			// 创建HttpGet
			HttpGet httpget = new HttpGet(url);
			// ---------设置超时时间---------
			// RequestConfig config = RequestConfig.custom()
			// .setSocketTimeout(3000)
			// .setConnectTimeout(3000)
			// .build();
			// httpget.setConfig(config);
			// ---------设置超时时间---------

			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);

			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 请求结束，返回结果
			if (entity != null) {
				resData = EntityUtils.toString(entity, Charset.forName("UTF-8"));
			}
		} catch (Exception e) {
			throw e;
		}
		return resData;
	}

	/**
	 * 发送GET请求
	 */
	public static String doGetII(String url) throws Exception {
		String resData = "";
		try (CloseableHttpClient httpclient = HttpClients.createMinimal(new BasicHttpClientConnectionManager());) {
			// 创建默认的httpClient实例.
			// httpclient = HttpClients.createDefault();

			// 创建HttpGet
			HttpGet httpget = new HttpGet(url);
			// ---------设置超时时间---------
			// RequestConfig config = RequestConfig.custom()
			// .setSocketTimeout(3000)
			// .setConnectTimeout(3000)
			// .build();
			// httpget.setConfig(config);
			// ---------设置超时时间---------

			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);

			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 请求结束，返回结果
			if (entity != null) {
				resData = EntityUtils.toString(entity, Charset.forName("gbk"));
			}
		} catch (Exception e) {
			throw e;
		}
		return resData;
	}
	
	public static String sendPost(String url, String params) throws Exception {
		System.out.println("执行httpclient  post.... ");
		String resData = "";
		try (CloseableHttpClient httpClient = HttpClients.createMinimal(new BasicHttpClientConnectionManager());) {

			HttpPost method = new HttpPost(url);
			StringEntity entity = new StringEntity(params, "utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			method.setEntity(entity);
			HttpResponse result = httpClient.execute(method);
			// 请求结束，返回结果
			resData = EntityUtils.toString(result.getEntity());

		} catch (Exception e) {
			throw e;
		}
		return resData;
	}

	/**
	 * form 表单形式
	 * 
	 * @param url
	 * @param paramList
	 * @throws Exception
	 */
	public  String sendFormPost(String url, List<NameValuePair> paramList) throws Exception {
		System.out.println("执行httpclient  sendFormPost.... ");
		   CloseableHttpClient httpRequest = null;
		String resData = "";
		try {
			// 创建默认的httpClient实例.
			httpRequest = HttpClients.createDefault();
			// 创建HttpPost
			HttpPost httppost = new HttpPost(url);
			// //---------设置超时时间---------
			RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
			httppost.setConfig(config);
			// ---------设置超时时间---------
			// 适用正常的表单提交
			httppost.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
			try (CloseableHttpResponse response = httpRequest.execute(httppost);) {
				int statusCode = response.getStatusLine().getStatusCode();
				logger.info("执行 http post request 返回STATUS CODE :" + statusCode);
				// 请求结束，返回结果
				resData = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
			}

		} catch (Exception e) {
			throw e;
		}

		return resData;
	}
	
	public static String sendJson(String url,String json) throws Exception{
	    String resData = "";
	    try{
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpPost httpPost = new HttpPost(url);
	        StringEntity entity = new StringEntity(json,"utf-8");
	        entity.setContentEncoding("UTF-8");
	        entity.setContentType("application/json;charset=utf-8");
	        httpPost.setEntity(entity);
	        try(CloseableHttpResponse result = httpClient.execute(httpPost);){
	            // 请求结束，返回结果
	            resData = EntityUtils.toString(result.getEntity());
	        }
	    }catch (Exception e){
	    	
	    }
	    return resData;
	}

	public static void main(String[] args) throws Exception {
//		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//		String uid = "35dcc809-ea42-4e39-996a-a4b185b6c6cd";
//		String mobiles = "18801488476";
//		String timestamp = new SimpleDateFormat("yyyMMddHHmmss").format(new Date());
//		System.out.println(timestamp);
//		String content = "Test 测试hello world";
//		String secretKey = "PCybGgRUmuS6WWR2XybX";
//		// String mac =
//		// DigestUtils.md5Hex(uid+mobiles+timestamp+content+secretKey);
//		formparams.add(new BasicNameValuePair("userId", uid));
//		// formparams.add(new BasicNameValuePair("mac", mac));
//		formparams.add(new BasicNameValuePair("mobiles", mobiles));
//		formparams.add(new BasicNameValuePair("timestamp", timestamp));
//		formparams.add(new BasicNameValuePair("content", content));
//
//		String url = "http://10.117.";
		try {
			System.out.println(doGetSimple("http://s.weibo.com/weibo/%E7%8B%82%E9%BE%99%E6%88%98%E7%8B%BC"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*try {
			String result = sendFormPost(url, formparams);
			System.out.println("result" + result);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}

package com.nebulae.utils;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;

public class HttpConnectUtils {
	
	/**
	 * get方式
	 * 
	 * @param url 请求地址
	 * @param token
	 * @return
	 */
	public static String httpGetUrl(String url, String token) {
		HttpClient httpClient = null;
		GetMethod getMethod = null;
		String responseMsg="";
		if (!StringUtils.isEmpty(url)) {
			httpClient = new HttpClient();
			getMethod = new GetMethod(url);
			if (!StringUtils.isEmpty(token)) {
				List<Header> headers = new ArrayList<Header>();
				headers.add(new Header("accept","application/json"));
				headers.add(new Header("Authorization", token));
				httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			}
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			try {
				int status = httpClient.executeMethod(getMethod);
				if(status!=HttpStatus.SC_OK) {
					throw new RuntimeException(String.format("远程服务器%s调用失败! \n%s", url, getMethod.getStatusLine()));
				}
				ByteArrayOutputStream out=new ByteArrayOutputStream();
				InputStream in = getMethod.getResponseBodyAsStream();
				int len=0;
				byte[] buf=new byte[1024];
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				responseMsg = out.toString("UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(getMethod!=null) {
					getMethod.releaseConnection();
				}
			}

		} else {
			responseMsg="请求地址不能为空";
		}
		return responseMsg;
	}
	
	/**
	 * post方式
	 * @param url
	 * @param code
	 * @param type
	 * @return
	 */
	public static String httpPostUrl(String url,Map<String, String> params,String token) {
		String responseMsg = "";
		HttpClient httpClient =null; 
		PostMethod postMethod =null;
		if(!StringUtils.isEmpty(url)) {
			httpClient=new HttpClient();
			httpClient.getParams().setContentCharset("GBK");
			postMethod=new PostMethod(url);
			if(!StringUtils.isEmpty(token)){
				List<Header> headers=new ArrayList<Header>();
				headers.add(new Header("accept","application/json"));
				headers.add(new Header("Authorization",token));
				httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			}
			for (String key : params.keySet()) {
				postMethod.setParameter(key, params.get(key));
			}
			try {
				postMethod.releaseConnection();
				int status= httpClient.executeMethod(postMethod);
				if(status != HttpStatus.SC_OK){
					throw new RuntimeException(String.format("远程服务器%s调用失败! \n%s", url, postMethod.getStatusLine()));
				}
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				InputStream in = postMethod.getResponseBodyAsStream();
				int len = 0;
				byte[] buf = new byte[1024];
				while((len=in.read(buf))!=-1){
					out.write(buf, 0, len);
				}
				responseMsg = out.toString("UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				postMethod.releaseConnection();
			}
		}else {
			responseMsg="请求地址不能为空";
		}
		return responseMsg;
	}
	
	public static String doPost(String url,String param,String token) {
		String responseMsg="";
		if(!StringUtils.isEmpty(url)) {
			try {
				byte[] b=param.getBytes("UTF-8");
				if(b==null) {
					return "{\"success\":false,\"message\":\"参数不全\",\"recordcount\":0,\"datas\":[]}";
				}
				URL postUrl=new URL(url);
				HttpURLConnection httpConnection = (HttpURLConnection) postUrl.openConnection();
				httpConnection.setDoOutput(true);
			    httpConnection.setRequestMethod("POST");
			    httpConnection.setRequestProperty("accept", "application/json");
			    if(!StringUtils.isEmpty(token)) {
			    	httpConnection.setRequestProperty("Authorization", token);
			    }
			    httpConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			    OutputStream out=httpConnection.getOutputStream();
			    out.write(b);
			    out.flush();
			   if(httpConnection.getResponseCode()==200) {
				   BufferedReader buffer=new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),"UTF-8"));
				   responseMsg=buffer.readLine();
			   }
			   
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			responseMsg="请求地址不能为空";
		}
		
		return responseMsg;
	}
	
	//更新182旅馆信息
	public static String doOldPost(String url,String param) {
		String responseMsg="";
		if(!StringUtils.isEmpty(url)) {
			try {
				byte[] b=param.getBytes("UTF-8");
				if(b==null) {
					return "{\"success\":false,\"message\":\"参数不全\",\"recordcount\":0,\"datas\":[]}";
				}
				URL postUrl=new URL(url);
				HttpURLConnection httpConnection = (HttpURLConnection) postUrl.openConnection();
				httpConnection.setDoOutput(true);
			    httpConnection.setRequestMethod("POST");
			    httpConnection.setRequestProperty("accept", "application/json");
			    httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			    OutputStream out=httpConnection.getOutputStream();
			    out.write(b);
			    out.flush();
			   if(httpConnection.getResponseCode()==200) {
				   BufferedReader buffer=new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),"UTF-8"));
				   responseMsg=buffer.readLine();
			   }
			   
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			responseMsg="请求地址不能为空";
		}
		
		return responseMsg;
	}
	

}

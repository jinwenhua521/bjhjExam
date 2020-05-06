package com.nebulae.system.service;


import java.util.List;
import java.util.Map;


public interface CommonService {
	//获取系统时间
	public String getCurrentDateTime(String flag) throws Exception;
	//获取系统日期
	public String getCurrentDate(String flag) throws Exception;
	//获取UUID
	public String getUuid();
	//获取随机数
	public String getRandomNumber();
	//执行sql查询语句
	public List<Map> executeSqlQuery(String sql) throws Exception;

}

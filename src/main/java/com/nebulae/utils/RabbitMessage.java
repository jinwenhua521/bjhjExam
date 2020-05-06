package com.nebulae.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RabbitMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String dataType;
	
	private String publishTime;
	
	private List<?> datas=new ArrayList<Object>();
	
	
	
	public RabbitMessage(String dataType,String publishTime,List<?> datas) {
		super();  
		this.dataType = dataType;
		this.publishTime = publishTime;
		this.datas = datas;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public List<?> getDatas() {
		return datas;
	}

	public void setDatas(List<?> datas) {
		this.datas = datas;
	}

	
	
	

}

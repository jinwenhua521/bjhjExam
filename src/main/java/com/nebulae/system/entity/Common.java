package com.nebulae.system.entity;

import java.io.Serializable;


public class Common implements Serializable{
	
	/**
	 * @author LB
	 */
	private static final long serialVersionUID = 1L;
	private String dateTime;
	private String date;
	private String uuid;
	private String randomNumber;
	

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}
	
	
	

}

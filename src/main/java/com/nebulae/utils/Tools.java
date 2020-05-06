package com.nebulae.utils;

import java.util.UUID;

public class Tools {

	// 获取大写UUID
	public static String getID() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");// 生成fid
	}

	// 获取全UUID
	public static String getAllID() {
		return UUID.randomUUID().toString();
	}

	// 获取随机数
	public static String getRandomNumber() {
		return "" + (1 + Math.random() * (10 - 1 + 1));
	}

}

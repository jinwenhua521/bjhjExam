package com.nebulae.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nebulae.system.entity.Common;
import com.nebulae.system.mapper.CommonMapper;
import com.nebulae.system.service.CommonService;
import com.nebulae.utils.Tools;


@Service
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private CommonMapper commonMapper;

	@Override
	public String getCurrentDateTime(String flag) throws Exception {
		Common common=commonMapper.getCurrentDateTime(flag);
		String currentDateTime=null;
		if(!StringUtils.isEmpty(common.getDateTime())) {
			currentDateTime=common.getDateTime();
		}
		return currentDateTime;
	}

	@Override
	public String getCurrentDate(String flag) throws Exception {
		Common common=commonMapper.getCurrentDateTime(flag);
		String currentDate=null;
		if(!StringUtils.isEmpty(common.getDate())) {
			currentDate=common.getDate();
		}
		return currentDate;
	}

	@Override
	public String getUuid() {
		return Tools.getID();
	}

	@Override
	public String getRandomNumber() {
		return Tools.getRandomNumber();
	}


	@Override
	public List<Map> executeSqlQuery(String sql) throws Exception {
		List<Map> result=null;
		if(!StringUtils.isEmpty(sql)){
		result=commonMapper.executeSqlQuery(sql);
		}
		return result;
	}

}

package com.nebulae.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysLog;
import com.nebulae.system.mapper.SysLogMapper;
import com.nebulae.system.service.LogService;
import com.nebulae.utils.ExampleUtils;

@Service
public class LogServiceImpl implements LogService {
	
	@Autowired
	SysLogMapper sysLogMapper;

	@Override
	public int save(SysLog sysLog) {
		int count=0;
		if(sysLog!=null) {
			count=sysLogMapper.insertSelective(sysLog);
		}
		return count;
	}

	@Override
	public int delete(String id) {
		int count=0;
		if(!StringUtils.isEmpty(id)) {
			count=sysLogMapper.deleteByPrimaryKey(id);
		}
		return count;
	}

	@Override
	public int batchDelete(String[] id) {
		int count=0;
		if(id.length>0) {
			count=sysLogMapper.batchDelete(id);
		}
		return count;
	}

	@Override
	public List<SysLog> list(SysLog sysLog) {
		List<SysLog> list=null;
		if(sysLog!=null) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			if(!StringUtils.isEmpty(sysLog.getIp())) {
				criteria.andFieldEqualTo("IP", sysLog.getIp());
			}
			if(!StringUtils.isEmpty(sysLog.getUserName())) {
				criteria.andFieldLike("USER_NAME", sysLog.getUserName()+"%");
			}
			if(!StringUtils.isEmpty(sysLog.getMethod())) {
				criteria.andFieldEqualTo("METHOD", sysLog.getMethod());
			}
			if(!StringUtils.isEmpty(sysLog.getOperation())) {
				criteria.andFieldLike("OPERATION", sysLog.getOperation()+"%");
			}
			list=sysLogMapper.selectByExample(example);
		}
		return list;
	}

}

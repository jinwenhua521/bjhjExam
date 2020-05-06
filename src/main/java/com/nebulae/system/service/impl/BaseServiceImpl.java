package com.nebulae.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysBase;
import com.nebulae.system.mapper.SysBaseMapper;
import com.nebulae.system.service.BaseService;
import com.nebulae.utils.ExampleUtils;


@Service
public class BaseServiceImpl implements BaseService {
	
	@Autowired
	SysBaseMapper sysBaseMapper;

	@Override
	public SysBase getById(String id) {
		SysBase sysBase=null;
		if(!StringUtils.isEmpty(id)) {
			sysBase=sysBaseMapper.selectByPrimaryKey(id);
		}
		return sysBase;
	}

	@Override
	public int save(SysBase sysBase) {
		int count=0;
		if(sysBase!=null) {
			count=sysBaseMapper.insertSelective(sysBase);
		}
		return count;
	}

	@Override
	public int update(SysBase sysBase) {
		int count=0;
		if(sysBase!=null) {
			count=sysBaseMapper.updateByPrimaryKeySelective(sysBase);
		}
		return count;
	}

	@Override
	public int delete(String id) {
		int count=0;
		if(!StringUtils.isEmpty(id)) {
			count=sysBaseMapper.deleteByPrimaryKey(id);
		}
		return count;
	}

	@Override
	public int batchDelete(String[] id) {
		int count=0;
		if(id.length>0) {
			count=sysBaseMapper.batchDelete(id);
		}
		return count;
	}

	@Override
	public List<SysBase> listType() {
		return sysBaseMapper.listType();
	}

	@Override
	public List<SysBase> list(SysBase sysBase) {
		List<SysBase> list=null;
		if(sysBase!=null) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			if(!StringUtils.isEmpty(sysBase.getSid())) {
				criteria.andFieldEqualTo("SID",sysBase.getSid());
			}
			if(!StringUtils.isEmpty(sysBase.getName())) {
				criteria.andFieldLike("NAME","%"+sysBase.getName()+"%");
			}
			if(!StringUtils.isEmpty(sysBase.getType())) {
				criteria.andFieldEqualTo("TYPE",sysBase.getType());
			}
			if(!StringUtils.isEmpty(sysBase.getRemarks())) {
				criteria.andFieldEqualTo("REMARKS", sysBase.getRemarks());
			}
			if(!StringUtils.isEmpty(sysBase.getCode())) {
				criteria.andFieldEqualTo("Code", sysBase.getCode());
			}
			if(sysBase.getState()!=null) {
				criteria.andFieldEqualTo("STATE", sysBase.getState().toString());
			}
			list=sysBaseMapper.selectByExample(example);
			
		}
				
		return list;
	}

}

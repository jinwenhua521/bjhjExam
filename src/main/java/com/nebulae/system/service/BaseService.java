package com.nebulae.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysBase;

@Service
public interface BaseService {
	//根据id查询基础信息
	SysBase getById(String id);
	
	//保存数据
	int save(SysBase sysBase);
	
	//修改数据
	int update(SysBase sysBase);
	
	//删除数据
	int delete(String id);
	
	//批量删除数据
	int batchDelete(String[] id);
	
	
	List<SysBase> listType();
	
	List<SysBase> list(SysBase sysBase);

}

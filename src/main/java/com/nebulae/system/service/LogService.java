package com.nebulae.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysLog;

@Service
public interface LogService {

	// 保存数据
	int save(SysLog sysLog);

	// 删除数据
	int delete(String id);

	// 批量删除数据
	int batchDelete(String[] id);
	
	List<SysLog> list(SysLog sysLog);

}

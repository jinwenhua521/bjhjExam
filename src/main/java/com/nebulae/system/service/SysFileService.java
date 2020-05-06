package com.nebulae.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysFile;

@Service
public interface SysFileService {
	
	int save(SysFile sysFile);
	
	int update(SysFile sysFile);
	
	int delete(String id);
	
	SysFile getById(String id);
	
	//查询列表
	List<SysFile> getFileList(SysFile sysFile);
	
	//根据当前用户查询label
	List<SysFile> getLabel(String userId);

}

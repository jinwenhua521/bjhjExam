package com.nebulae.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysRole;

@Service
public interface RoleService {
	
	SysRole getById(String id);
	
	List<SysRole> list(SysRole role);
	
	int insert(SysRole role) throws Exception;
	
	int delete(String id) throws Exception;
	
	int update(SysRole role) throws Exception;
	
	int batchDelete(String[] id) throws Exception;
	
	//查询用户已经授权的角色
	List<SysRole> list(String userId);

}

package com.nebulae.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysRoleMenu;

@Service
public interface SysRoleMenuService {
	
	
	SysRoleMenu getById(String id);
	
	List<SysRoleMenu> list(SysRoleMenu role);
	
	int insert(SysRoleMenu role);
	
	int delete(String id);
	
	int update(SysRoleMenu role);
	
	int batchDelete(String[] id);

}

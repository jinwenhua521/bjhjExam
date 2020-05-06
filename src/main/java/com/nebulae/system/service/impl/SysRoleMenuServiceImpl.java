package com.nebulae.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysRoleMenu;
import com.nebulae.system.mapper.SysRoleMenuMapper;
import com.nebulae.system.service.SysRoleMenuService;
import com.nebulae.utils.ExampleUtils;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	
	@Autowired
	SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	public SysRoleMenu getById(String id) {
		SysRoleMenu roleMenu=null;
		if(!StringUtils.isEmpty(id)) {
			roleMenu=sysRoleMenuMapper.selectByPrimaryKey(id);
		}		
		return roleMenu;
	}

	@Override
	public List<SysRoleMenu> list(SysRoleMenu role) {
		List<SysRoleMenu> list=null;
		if(role!=null) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			if(!StringUtils.isEmpty(role.getRoleId())) {
				criteria.andFieldEqualTo("ROLE_ID", role.getRoleId());
			}
			if(!StringUtils.isEmpty(role.getMenuId())) {
				criteria.andFieldEqualTo("MENU_ID", role.getMenuId());
			}
			list=sysRoleMenuMapper.selectByExample(example);
		}
		return list;
	}

	@Override
	public int insert(SysRoleMenu role) {
		int count=0;
		if(role!=null) {
			count=sysRoleMenuMapper.insertSelective(role);
		}
		return count;
	}

	@Override
	public int delete(String id) {
		int count=0;
		if(!StringUtils.isEmpty(id)) {
			count=sysRoleMenuMapper.deleteByPrimaryKey(id);
		}
		return count;
	}

	@Override
	public int update(SysRoleMenu role) {
		int count=0;
		if(role!=null) {
			count=sysRoleMenuMapper.updateByPrimaryKeySelective(role);
		}
		return count;
	}

	@Override
	public int batchDelete(String[] id) {
		int count=0;
		if(id.length>0) {
			count=sysRoleMenuMapper.batchDelete(id);
		}
		return count;
	}

}

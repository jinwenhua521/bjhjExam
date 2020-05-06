package com.nebulae.system.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nebulae.system.entity.SysRole;
import com.nebulae.system.entity.SysRoleMenu;
import com.nebulae.system.entity.SysUserRole;
import com.nebulae.system.mapper.SysRoleMapper;
import com.nebulae.system.mapper.SysRoleMenuMapper;
import com.nebulae.system.mapper.SysUserRoleMapper;
import com.nebulae.system.service.RoleService;
import com.nebulae.utils.ExampleUtils;
import com.nebulae.utils.Tools;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	SysRoleMapper sysRoleMapper;
	
	@Autowired
	SysRoleMenuMapper sysRoleMenuMapper;
	
	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public SysRole getById(String id) {
		SysRole role=null;
		if(!StringUtils.isEmpty(id)) {
			role=sysRoleMapper.selectByPrimaryKey(id);
		}
		return role;
	}

	@Override
	public List<SysRole> list(SysRole role) {
		List<SysRole> list=null;
		if(role!=null) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			if(!StringUtils.isEmpty(role.getRoleName())) {
				criteria.andFieldLike("ROLE_NAME", "%"+role.getRoleName()+"%");
			}
			if(!StringUtils.isEmpty(role.getRemark())) {
				criteria.andFieldLike("REMARK", "%"+role.getRemark()+"%");
			}
			if(role.getStatus()!=null) {
				criteria.andFieldEqualTo("STATUS", role.getStatus().toString());
			}
			list=sysRoleMapper.selectByExample(example);
		}
		return list;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int insert(SysRole role) throws Exception{
		int count=0;
		if(role!=null) {
			List<String> menuIds = role.getMenuIds();
			String roleId = role.getRoleId();
			if(!StringUtils.isEmpty(roleId)) {
				ExampleUtils example=new ExampleUtils();
				ExampleUtils.Criteria criteria=example.createCriteria();
				criteria.andFieldEqualTo("ROLE_ID", roleId);
				sysRoleMenuMapper.deleteByExample(example);
				for (String menuId : menuIds) {
					if(!menuId.equals("0")) {
						SysRoleMenu rmDo = new SysRoleMenu();
						rmDo.setId(Tools.getID());
						rmDo.setRoleId(roleId);
						rmDo.setMenuId(menuId);
						sysRoleMenuMapper.insert(rmDo);
					}
				}
			}
			count=sysRoleMapper.insertSelective(role);
		}
		return count;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int delete(String id) throws Exception{
		int count=0;
		if(!StringUtils.isEmpty(id)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("ROLE_ID", id);
			sysRoleMenuMapper.deleteByExample(example);
			sysUserRoleMapper.deleteByExample(example);
			count=sysRoleMapper.deleteByPrimaryKey(id);
		}
		return count;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int update(SysRole role) throws Exception{
		int count=0;
		if(role!=null) {
			List<String> menuIds=role.getMenuIds();
			String roleId = role.getRoleId();
			if(menuIds.size()>0 && !StringUtils.isEmpty(roleId)) {
				ExampleUtils example=new ExampleUtils();
				ExampleUtils.Criteria criteria=example.createCriteria();
				criteria.andFieldEqualTo("ROLE_ID", role.getRoleId());
				sysRoleMenuMapper.deleteByExample(example);
				for (String menuId : menuIds) {
					if(!menuId.equals("0")) {
						SysRoleMenu rmDo = new SysRoleMenu();
						rmDo.setId(Tools.getID());
						rmDo.setRoleId(roleId);
						rmDo.setMenuId(menuId);
						sysRoleMenuMapper.insert(rmDo);
					}
				}
			}
			count=sysRoleMapper.updateByPrimaryKeySelective(role);
		}
		return count;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int batchDelete(String[] id) throws Exception{
		int count=0;
		if(id.length>0) {
			for(int i=0;i<id.length;i++) {
				ExampleUtils example=new ExampleUtils();
				ExampleUtils.Criteria criteria=example.createCriteria();
				criteria.andFieldEqualTo("ROLE_ID", id[i]);
				sysRoleMenuMapper.deleteByExample(example);
				sysUserRoleMapper.deleteByExample(example);
			}
			count=sysRoleMapper.batchDelete(id);
		}
		return count;
	}

	@Override
	public List<SysRole> list(String userId) {
		List<SysRole> list=null;
		if(!StringUtils.isEmpty(userId)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("USER_ID", userId);
			List<SysUserRole> userRole=sysUserRoleMapper.selectByExample(example);
			list=sysRoleMapper.selectByExample(null);
			for (SysRole role : list) {
	            role.setRoleSign("false");
	            for (SysUserRole roleId : userRole) {
	                if (Objects.equals(role.getRoleId(), roleId.getRoleId())) {
	                    role.setRoleSign("true");
	                    break;
	                }
	            }
	        }
		}
		return list;
	}

}

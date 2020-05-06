package com.nebulae.system.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nebulae.system.entity.SysDept;
import com.nebulae.system.entity.SysUser;
import com.nebulae.system.entity.SysUserRole;
import com.nebulae.system.entity.vo.SysUserVo;
import com.nebulae.system.mapper.SysDeptMapper;
import com.nebulae.system.mapper.SysUserMapper;
import com.nebulae.system.mapper.SysUserRoleMapper;
import com.nebulae.system.service.UserService;
import com.nebulae.utils.ExampleUtils;
import com.nebulae.utils.MD5Util;
import com.nebulae.utils.Tools;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	SysUserMapper sysUserMapper;

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	SysDeptMapper sysDeptMapper;

	@Override
	public SysUser getUserName(String userName) {
		SysUser sysUser=null;
		if(!StringUtils.isEmpty(userName)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("USER_NAME", userName);
			ExampleUtils.Criteria criteria1=example.createCriteria();
			criteria1.andFieldEqualTo("MOBILE", userName);
			example.or(criteria1);
			List<SysUser> list=sysUserMapper.selectByExample(example);
			if(list.size()>0) {
				sysUser=list.get(0);
				SysDept dept=sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
				if(dept!=null) {
					sysUser.setDeptFcode(dept.getDeptFcode());
					sysUser.setDeptFid(dept.getDeptFid());
					sysUser.setDeptFname(dept.getDeptFname());
					sysUser.setDeptCode(dept.getDeptCode());
					sysUser.setDeptName(dept.getDeptName());
				}
			}
		}
		return sysUser;
	}

	@Override
	public SysUser getUserId(String userId) {
		SysUser sysUser=null;
		if(!StringUtils.isEmpty(userId)) {
			sysUser=sysUserMapper.selectByPrimaryKey(userId);
		}
		return sysUser;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int save(SysUser user) throws Exception{
		int count=0;
		if(user!=null) {
			List<String> roles = user.getRoleIds();
			for (String roleId : roles) {
				SysUserRole ur = new SysUserRole();
				ur.setId(Tools.getID());
				ur.setUserId(user.getUserId());
				ur.setRoleId(roleId);
				sysUserRoleMapper.insert(ur);
			}

			if(!StringUtils.isEmpty(user.getPassword())) {
				user.setPassword(MD5Util.MD5EncodeStrUtf8(user.getPassword()));
			}else {
				user.setPassword(MD5Util.MD5EncodeStrUtf8(user.getUserName()));
			}
			user.setCreateDate(new Date());
			user.setStatus(new BigDecimal(1));
			count=sysUserMapper.insertSelective(user);
		}
		return count;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int update(SysUser user) throws Exception{
		int count=0;
		if(user!=null) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("USER_ID", user.getUserId());
			sysUserRoleMapper.deleteByExample(example);
			List<String> roles = user.getRoleIds();
			for (String roleId : roles) {
				SysUserRole ur = new SysUserRole();
				ur.setId(Tools.getID());
				ur.setUserId(user.getUserId());
				ur.setRoleId(roleId);
				sysUserRoleMapper.insert(ur);
			}
			count=sysUserMapper.updateByPrimaryKeySelective(user);
		}
		return count;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int delete(String userId) throws Exception{
		int count=0;
		if(!StringUtils.isEmpty(userId)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("USER_ID", userId);
			sysUserRoleMapper.deleteByExample(example);
			count=sysUserMapper.deleteByPrimaryKey(userId);

		}
		return count;
	}

	@Override
	public List<SysUser> getList(SysUser user) {
		List<SysUser> list=null;
		if(user!=null) {
			ExampleUtils example=new ExampleUtils();
			example.setOrderByClause("CREATE_DATE desc");
			ExampleUtils.Criteria criteria=example.createCriteria();
			if(!StringUtils.isEmpty(user.getUserName())) {
				criteria.andFieldEqualTo("USER_NAME", user.getUserName());
			}
			if(!StringUtils.isEmpty(user.getEmail())) {
				criteria.andFieldEqualTo("EMAIL", user.getEmail());
			}
			if(!StringUtils.isEmpty(user.getMobile())) {
				criteria.andFieldEqualTo("MOBILE", user.getMobile());
			}
			if(!StringUtils.isEmpty(user.getName())) {
				criteria.andFieldLike("NAME", "%"+user.getName()+"%");
			}
			if(!StringUtils.isEmpty(user.getDeptId())) {
				criteria.andFieldEqualTo("DEPT_ID", user.getDeptId());
			}
			list=sysUserMapper.selectByExample(example);
		}
		return list;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int batchDelete(String[] id) throws Exception{
		int count=0;
		if(id.length>0) {
			for(int i=0;i<id.length;i++) {
				ExampleUtils example=new ExampleUtils();
				ExampleUtils.Criteria criteria=example.createCriteria();
				criteria.andFieldEqualTo("USER_ID", id[i]);
				sysUserRoleMapper.deleteByExample(example);
			}
			count=sysUserMapper.batchDelete(id);
		}
		return count;
	}

	@Override
	public int resetPwd(SysUserVo userVo,SysUser user) throws Exception{
		if(Objects.equals(userVo.getUserDO().getUserId(), user.getUserId())) {
			if(Objects.equals(MD5Util.MD5EncodeStrUtf8(userVo.getPwdOld()),user.getPassword())){
				user.setPassword(MD5Util.MD5EncodeStrUtf8(userVo.getPwdNew()));
				return sysUserMapper.updateByPrimaryKeySelective(user);
			}else{
				throw new Exception("输入的旧密码有误！");
			}
		}else {
			throw new Exception("你修改的不是你登录的账号！");
		}
	}

	@Override
	public int resetPwd(SysUser user) {
		int count=0;
		if(user!=null) {
			count=sysUserMapper.updateByPrimaryKeySelective(user);
		}
		return count;
	}

	  @Override
	    public int updatePersonalImg(SysUser user) throws Exception {
		 int count=0;
			if(user!=null) {
				count= sysUserMapper.updateByPrimaryKeyWithBLOBs(user);
			}
			return count;
	    }

}

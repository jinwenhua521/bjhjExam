package com.nebulae.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.nebulae.system.entity.SysUser;
import com.nebulae.system.entity.vo.SysUserVo;

public interface UserService {
	
	//根据用户名查询用户信息
	SysUser getUserName(String userName);
	//根据用户id查询用户信息
	SysUser getUserId(String userId);
	//保存
	int save(SysUser user) throws Exception;
	//更新
	int update(SysUser user) throws Exception;
	//删除
	int delete(String userId)throws Exception;
	//批量删除
	int batchDelete(String[] id) throws Exception;
	//查询列表
	List<SysUser> getList(SysUser user);
	//修改密码
	int resetPwd(SysUserVo userVo,SysUser user) throws Exception;
	//重置密码
	int resetPwd(SysUser user);
	
	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
    int updatePersonalImg(SysUser user) throws Exception;
	
	

}

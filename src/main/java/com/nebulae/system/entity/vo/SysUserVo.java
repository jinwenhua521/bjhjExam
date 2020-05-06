package com.nebulae.system.entity.vo;

import com.nebulae.system.entity.SysUser;

public class SysUserVo {
	
	/**
     * 更新的用户对象
     */
    private SysUser userDO = new SysUser();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;
	public SysUser getUserDO() {
		return userDO;
	}
	public void setUserDO(SysUser userDO) {
		this.userDO = userDO;
	}
	public String getPwdOld() {
		return pwdOld;
	}
	public void setPwdOld(String pwdOld) {
		this.pwdOld = pwdOld;
	}
	public String getPwdNew() {
		return pwdNew;
	}
	public void setPwdNew(String pwdNew) {
		this.pwdNew = pwdNew;
	}
    
    

}

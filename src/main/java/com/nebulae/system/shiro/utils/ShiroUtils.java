package com.nebulae.system.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.nebulae.system.entity.SysUser;

public class ShiroUtils {
	
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	public static SysUser getUser() {
		return (SysUser)getSubjct().getPrincipal();
	}
	public static String getUserId() {
		return getUser().getUserId();
	}
	public static void logout() {
		getSubjct().logout();
	}

}

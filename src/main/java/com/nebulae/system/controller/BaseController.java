package com.nebulae.system.controller;

import org.springframework.stereotype.Controller;

import com.nebulae.system.entity.SysUser;
import com.nebulae.system.shiro.utils.ShiroUtils;


@Controller
public class BaseController {
	
	public SysUser getUser() {
		return ShiroUtils.getUser();
	}

	public String getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUserName();
	}

}

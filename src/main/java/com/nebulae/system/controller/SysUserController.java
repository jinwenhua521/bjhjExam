package com.nebulae.system.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nebulae.annotation.Log;
import com.nebulae.system.entity.SysRole;
import com.nebulae.system.entity.SysUser;
import com.nebulae.system.entity.vo.SysUserVo;
import com.nebulae.system.service.RoleService;
import com.nebulae.system.service.UserService;
import com.nebulae.utils.FileUtil;
import com.nebulae.utils.GridTable;
import com.nebulae.utils.JsonUtils;
import com.nebulae.utils.MD5Util;
import com.nebulae.utils.Query;
import com.nebulae.utils.R;
import com.nebulae.utils.Tools;

import io.swagger.annotations.Api;

@Api(value="用户管理",tags= {"用户管理"})
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	
	@GetMapping()
	@RequiresPermissions("system:user:user_list")
	String user() {
		return "system/user/user_list";
	}
	
	@Log("查询用户列表")
	@GetMapping("/list")
	@RequiresPermissions("system:user:user_list")
	public @ResponseBody GridTable list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		SysUser sysUser=JsonUtils.map2pojo(params, SysUser.class);
		// 查询列表数据
		PageHelper.startPage(Integer.parseInt(query.get("page").toString()), Integer.parseInt(query.get("limit").toString()));
		PageHelper.orderBy("USER_NAME desc");
		PageInfo<SysUser>  pageInfo=new PageInfo<SysUser>(userService.getList(sysUser));
		GridTable grid=new GridTable(pageInfo.getList(),pageInfo.getTotal());
		return grid;
	}
	
	@GetMapping("/add/{pId}")
	@RequiresPermissions("system:user:add")
	String add(Model model,@PathVariable("pId") String pId) {
		SysRole role=new SysRole();
		role.setStatus(new BigDecimal(1));
		List<SysRole> roles = roleService.list(role);
		model.addAttribute("roles", roles);
		model.addAttribute("userId",Tools.getID());
		model.addAttribute("deptId",pId);
		return "system/user/add";
	}
	
	@RequiresPermissions("system:user:add")
	@Log("保存用户")
	@PostMapping("/save")
	@ResponseBody
	R save(SysUser user) throws Exception{
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		boolean result=true;
		SysUser user=userService.getUserName(params.get("username").toString());
		if(user!=null) {
			result=false;
		}
		return result;
	}
	
	
	@RequiresPermissions("system:user:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") String id) {
		SysUser sysUser = userService.getUserId(id);
		model.addAttribute("user", sysUser);
		List<SysRole> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return "system/user/edit";
	}
	
	@RequiresPermissions("system:user:edit")
	@Log("更新用户")
	@PostMapping("/update")
	@ResponseBody
	R update(SysUser user) throws Exception{
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}
	
	@RequiresPermissions("system:user:remove")
	@Log("删除用户")
	@PostMapping("/remove")
	@ResponseBody
	R remove(String id) throws Exception{
		if (userService.delete(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("system:user:batchRemove")
	@Log("批量删除用户")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") String[] userIds) throws Exception{
		int r = userService.batchDelete(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	@RequiresPermissions("system:user:resetPwd")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") String userId, Model model) {
		model.addAttribute("userId", userId);
		return "system/user/reset_pwd";
	}
	
	@RequiresPermissions("system:user:resetPwd")
	@Log("重置密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(SysUser user) {
		    user.setPassword(MD5Util.encrypt(user.getPassword()));
			int r=userService.resetPwd(user);
		if(r>0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	@Log("修改密码")
	@PostMapping("/modifyPwd")
	@ResponseBody
	R modifyPwd(SysUserVo user) {
			try {
				userService.resetPwd(user,getUser());
				return R.ok();
			} catch (Exception e) {
				return R.error(1,e.getMessage());
			}
	}
	
	
	
	
	@GetMapping("/personal")
	String personal(Model model) {
		SysUser user  = userService.getUserId(getUserId());
		model.addAttribute("user",user);
		return "system/user/personal";
	}
	
	
	@ResponseBody
	@PostMapping("/uploadImg")
	R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {
		try {
			SysUser user=getUser();
			user.setPhoto(file.getBytes());
			userService.updatePersonalImg(user);
			return R.ok("更新头像成功！");
		} catch (Exception e) {
			return R.error("更新图像失败！");
		}
	}
	
	
	
	
	

}

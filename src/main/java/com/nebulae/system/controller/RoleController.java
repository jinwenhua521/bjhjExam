package com.nebulae.system.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nebulae.annotation.Log;
import com.nebulae.system.entity.SysRole;
import com.nebulae.system.service.RoleService;
import com.nebulae.utils.GridTable;
import com.nebulae.utils.JsonUtils;
import com.nebulae.utils.Query;
import com.nebulae.utils.R;
import com.nebulae.utils.Tools;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController{
	
	@Autowired
	RoleService roleService;
	
	@GetMapping()
	@RequiresPermissions("system:role:role_list")
	String getRole() {
		return "system/role/role_list";
	}
	
	
	@GetMapping("/list")
	@RequiresPermissions("system:role:role_list")
	public @ResponseBody GridTable list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		SysRole role=JsonUtils.map2pojo(params, SysRole.class);
		if(params.containsKey("search")) {
			role.setRoleName(params.get("search").toString());
		}
		// 查询列表数据
		PageHelper.startPage(Integer.parseInt(query.get("page").toString()), Integer.parseInt(query.get("limit").toString()));
		PageHelper.orderBy("CREATE_DATE desc");
		PageInfo<SysRole>  pageInfo=new PageInfo<SysRole>(roleService.list(role));
		GridTable grid=new GridTable(pageInfo.getList(),pageInfo.getTotal());
		return grid;
	}
	
	@Log("添加角色")
	@RequiresPermissions("system:role:add")
	@GetMapping("/add")
	String add() {
		return  "system/role/role_add";
	}
	
	@Log("保存角色")
	@RequiresPermissions("system:role:add")
	@PostMapping("/save")
	@ResponseBody
	R  save(SysRole role) throws Exception{
			role.setCreateId(getUserId());
			role.setCreateDate(new Date());
			role.setRoleId(Tools.getID());
			if (roleService.insert(role) > 0) {
				return R.ok();
			} else {
				return R.error(1, "保存失败");
			}
	}
	
	@Log("编辑角色")
	@RequiresPermissions("system:role:edit")
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id, Model model) {
		SysRole role = roleService.getById(id);
		model.addAttribute("role", role);
		return "system/role/role_edit";
	}
	
	@Log("更新角色")
	@RequiresPermissions("system:role:edit")
	@PostMapping("/update")
	@ResponseBody
	R update(SysRole role) throws Exception{
		if (roleService.update(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}
	
	
	@Log("删除角色")
	@RequiresPermissions("system:role:remove")
	@PostMapping("/remove")
	@ResponseBody
	R delete(String id) throws Exception{
		if (roleService.delete(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	@Log("批量删除角色")
	@RequiresPermissions("system:role:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") String[] ids) throws Exception{
		int r = roleService.batchDelete(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

}

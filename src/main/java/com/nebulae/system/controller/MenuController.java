package com.nebulae.system.controller;

import java.util.List;
import java.util.Map;

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
import com.nebulae.system.entity.MenuTree;
import com.nebulae.system.entity.SysBase;
import com.nebulae.system.entity.SysMenu;
import com.nebulae.system.service.MenuService;
import com.nebulae.utils.GridTable;
import com.nebulae.utils.JsonUtils;
import com.nebulae.utils.Query;
import com.nebulae.utils.R;

@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController{
	
	@Autowired
	private MenuService menuService;
	
	
	@GetMapping()
	@RequiresPermissions("system:menu:menu_tree")
	String getMenu() {
		return "system/menu/menu_list";
	}
	
	@PostMapping("/menuTree")
	@RequiresPermissions("system:menu:menu_tree")
	public @ResponseBody List<MenuTree> getMenuTree(@RequestParam String menuId) {
		return menuService.byParentMenuListTree(menuId);
	}
	
	
	@PostMapping("/checkMenuTree")
	public @ResponseBody List<MenuTree> getMenuTree(@RequestParam String menuId,@RequestParam String roleId) {
		return menuService.byParentOrRoleIdMenuTree(menuId, roleId);
	}
	
	
	@GetMapping("/list")
	public @ResponseBody GridTable list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		SysMenu sysMenu=JsonUtils.map2pojo(params, SysMenu.class);
		// 查询列表数据
		PageHelper.startPage(Integer.parseInt(query.get("page").toString()), Integer.parseInt(query.get("limit").toString()));
		PageHelper.orderBy("CREATE_DATE desc");
		PageInfo<SysMenu>  pageInfo=new PageInfo<SysMenu>(menuService.getList(sysMenu));
		GridTable grid=new GridTable(pageInfo.getList(),pageInfo.getTotal());
		return grid;
	}
	
	@Log("添加菜单")
	@RequiresPermissions("system:menu:add")
	@GetMapping("/add/{pId}/{pName}")
	String add(Model model,@PathVariable("pId") String pId,@PathVariable("pName")String pName) {
		model.addAttribute("pId", pId);
		if (pId.equals("0")) {
			model.addAttribute("pName", "菜单根目录");
		} else {
			model.addAttribute("pName", pName);
		}
		return "system/menu/menu_add";
	}
	
	@Log("保存菜单")
	@RequiresPermissions("system:menu:add")
	@PostMapping("/save")
	@ResponseBody
	R save(SysMenu menu) {
		menu.setCreateId(getUserId());
		if (menuService.insert(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}
	
	/**
	 * 批量删除
	 */
	@Log("批量删除菜单")
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:menu:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids) {
		if(menuService.batchDelete(ids)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	/**
	 *删除 
	 */
	@Log("删除菜单")
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:menu:remove")
	public R remove(String id) {
		if(menuService.delete(id)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 *编辑 
	 */
	@Log("编辑菜单")
	@PostMapping("/update")
	@ResponseBody
	@RequiresPermissions("system:menu:edit")
	public R update(SysMenu menu) {
		if(menuService.update(menu)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	@GetMapping("/edit/{id}/{pName}")
	@RequiresPermissions("system:menu:edit")
	String edit(@PathVariable("id") String id,@PathVariable("pName")String pName, Model model) {
		SysMenu menu = menuService.getById(id);
		model.addAttribute("SysMenu", menu);
		model.addAttribute("pName", pName);
		return "system/menu/menu_edit";
	}
	
	
	

}

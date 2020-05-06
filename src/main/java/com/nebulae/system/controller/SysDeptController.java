package com.nebulae.system.controller;

import java.util.Date;
import java.util.List;
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
import com.nebulae.system.entity.DeptTree;
import com.nebulae.system.entity.SysDept;
import com.nebulae.system.service.DeptService;
import com.nebulae.utils.GridTable;
import com.nebulae.utils.JsonUtils;
import com.nebulae.utils.Query;
import com.nebulae.utils.R;
import com.nebulae.utils.Tools;

@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController{
	
	@Autowired
	private DeptService deptService;
	
	
	@GetMapping()
	@RequiresPermissions("system:dept:dept_list")
	String SysDict() {
		return "system/dept/dept_list";
	}
	
	@PostMapping("/deptTree")
	public @ResponseBody List<DeptTree> getMenuTree(@RequestParam String deptId) {
		return deptService.getDeptTree(deptId);
	}
	
	@GetMapping("/list")
	public @ResponseBody GridTable list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		SysDept sysDept=JsonUtils.map2pojo(params, SysDept.class);
		// 查询列表数据
		PageHelper.startPage(Integer.parseInt(query.get("page").toString()), Integer.parseInt(query.get("limit").toString()));
		PageHelper.orderBy("CREATE_DATE desc");
		PageInfo<SysDept>  pageInfo=new PageInfo<SysDept>(deptService.list(sysDept));
		GridTable grid=new GridTable(pageInfo.getList(),pageInfo.getTotal());
		return grid;
	}
	
	@Log("添加部门")
	@RequiresPermissions("system:dept:add")
	@GetMapping("/add/{pId}/{pName}")
	String add(Model model,@PathVariable("pId") String pId,@PathVariable("pName")String pName) {
		model.addAttribute("pId", pId);
		String deptId=Tools.getID();
		model.addAttribute("deptId", deptId);
		if (pId.equals("0")) {
			model.addAttribute("pName", "组织机构树");
		} else {
			model.addAttribute("pName", pName);
		}
		SysDept dept=deptService.getById(pId);
		if(dept!=null) {
			model.addAttribute("deptFid",dept.getDeptFid());
			model.addAttribute("deptFname",dept.getDeptFname());
			model.addAttribute("deptFcode", dept.getDeptFcode());
		}else {
			model.addAttribute("deptFid","");
			model.addAttribute("deptFname","");
			model.addAttribute("deptFcode","");
		}
		return "system/dept/dept_add";
	}
	
	@Log("保存部门信息")
	@RequiresPermissions("system:dept:add")
	@PostMapping("/save")
	@ResponseBody
	R save(SysDept dept) {
		dept.setCreateId(getUserId());
		dept.setCreateDate(new Date());
		if (deptService.save(dept) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}
	
	/**
	 *编辑 
	 */
	@Log("编辑部门信息")
	@PostMapping("/update")
	@ResponseBody
	@RequiresPermissions("system:dept:edit")
	public R update(SysDept sysDept) {
		if(deptService.update(sysDept)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:dept:edit")
	String edit(@PathVariable("id") String id, Model model) {
		SysDept sysDept = deptService.getById(id);
		if(!StringUtils.isEmpty(sysDept.getDeptName())) {
			sysDept.setOldDeptName(sysDept.getDeptName());
		}
		if(!StringUtils.isEmpty(sysDept.getDeptCode())) {
			sysDept.setOldDeptCode(sysDept.getDeptCode());
		}
		model.addAttribute("SysDept", sysDept);
		return "system/dept/dept_edit";
	}
	
	/**
	 *删除 
	 */
	@Log("删除部门")
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:dept:remove")
	public R remove(String id) {
		if(deptService.delete(id)>0) {
			return R.ok();
		}
		return R.error();
	}
	

	/**
	 * 批量删除
	 */
	@Log("批量删除部门")
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:dept:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids) {
		if(deptService.batchDelete(ids)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	

}

package com.nebulae.system.controller;

import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.nebulae.utils.R;
import com.nebulae.utils.Tools;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nebulae.annotation.Log;
import com.nebulae.system.entity.SysBase;
import com.nebulae.system.service.BaseService;
import com.nebulae.utils.GridTable;
import com.nebulae.utils.JsonUtils;
import com.nebulae.utils.Query;


@Controller
@RequestMapping("/system/base")
public class SysBaseController extends BaseController{
	
	@Autowired
	private BaseService baseService;
	
	
	@GetMapping()
	@RequiresPermissions("system:base:base_list")
	String SysDict() {
		return "system/base/base_list";
	}
	
	@GetMapping("/type")
	@ResponseBody
	public List<SysBase> listType() {
		return baseService.listType();
	};
	
	@Log("查询数据字典列表")
	@GetMapping("/list")
	@RequiresPermissions("system:base:base_list")
	public @ResponseBody GridTable list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		SysBase sysBase=JsonUtils.map2pojo(params, SysBase.class);
		// 查询列表数据
		PageHelper.startPage(Integer.parseInt(query.get("page").toString()), Integer.parseInt(query.get("limit").toString()));
		PageHelper.orderBy("TYPE,SEQENC asc");
		PageInfo<SysBase>  pageInfo=new PageInfo<SysBase>(baseService.list(sysBase));
		GridTable grid=new GridTable(pageInfo.getList(),pageInfo.getTotal());
		return grid;
	}
	
	/**
	 * 删除
	 */
	@Log("删除数据字典")
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:base:remove")
	public R remove(String id) {
		if (baseService.delete(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 批量删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:base:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids) {
		if(baseService.batchDelete(ids)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 修改
	 */
	@Log("编辑数据字典")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:base:edit")
	public R update(SysBase sysBase) {
		sysBase.setCreateDate(new Date());
		if(baseService.update(sysBase)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:base:edit")
	String edit(@PathVariable("id") String id, Model model) {
		SysBase SysBase = baseService.getById(id);
		model.addAttribute("SysBase", SysBase);
		return "system/base/base_edit";
	}

	
	/**
	 *添加 
	 */
	@Log("添加数据字典")
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("system:base:add")
	public R add(SysBase sysBase) {
		 if(sysBase!=null) {
			 sysBase.setSid(Tools.getID());
			 sysBase.setCreateId(getUserId());
			 sysBase.setCreateDate(new Date());
			 if(baseService.save(sysBase)>0) {
				 return R.ok();
			 }
		 }
		return R.error();
	}
	

	@GetMapping("/add")
	@RequiresPermissions("system:base:add")
	String add() {
		return "system/base/base_add";
	}
	
   /**
    *保存 
    */
	
	
	/**
	 *根据id查询数据
	 * 
	 */
	@ResponseBody
	@GetMapping("/getId/{id}")
	@RequiresPermissions("system:base:getId")
	public SysBase getId(@PathVariable("id") String id) {
		return baseService.getById(id);
	}

}

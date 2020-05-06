package com.nebulae.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nebulae.system.entity.SysLog;
import com.nebulae.system.service.LogService;
import com.nebulae.utils.GridTable;
import com.nebulae.utils.JsonUtils;
import com.nebulae.utils.Query;
import com.nebulae.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/system/log")
@Api(value="日志管理",tags= {"日志管理"})
public class LogController extends BaseController{
	
	@Autowired
	LogService logService;
	
	@GetMapping()
	String log() {
		return "system/log/log";
	}
	
	@ApiOperation(value="查询日志列表",notes="根据ip,用户，描述查询日志信息",produces="application/json")
	@GetMapping("/list")
	@ResponseBody 
	GridTable list(@RequestParam Map<String, Object> params) {
	  Query query = new Query(params);
	   SysLog sysLog=JsonUtils.map2pojo(params, SysLog.class);
		// 查询列表数据
		PageHelper.startPage(Integer.parseInt(query.get("page").toString()), Integer.parseInt(query.get("limit").toString()));
		PageHelper.orderBy("OPERATIONDATE desc");
		PageInfo<SysLog>  pageInfo=new PageInfo<SysLog>(logService.list(sysLog));
		GridTable grid=new GridTable(pageInfo.getList(),pageInfo.getTotal());
		return grid;
	}
	
	@ApiOperation(value="根据id删除日志信息",notes="根据id删除日志信息",produces="application/json")
	@ApiImplicitParam(name="id主键",required = true, dataType = "String")
	@ResponseBody
	@PostMapping("/remove")
	R remove(String id) {
		if (!"admin".equals(getUsername())) {
			return R.error(1, "您没有权限删除日志信息");
		}
		if (logService.delete(id)>0) {
			return R.ok();
		}
		return R.error();
	}

	@ApiOperation(value="批量删除日志信息",notes="根据id批量删除日志信息",produces="application/json")
	@ApiImplicitParam(name="ids[]id主键数组",required = true, dataType = "Array[string]")
	@ResponseBody
	@PostMapping("/batchRemove")
	R batchRemove(@RequestParam("ids[]") String[] ids) {
		if (!"admin".equals(getUsername())) {
			return R.error(1, "您没有权限删除日志信息");
		}
		int r = logService.batchDelete(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	
	
	

}

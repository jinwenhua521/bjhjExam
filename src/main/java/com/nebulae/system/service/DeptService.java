package com.nebulae.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nebulae.system.entity.DeptTree;
import com.nebulae.system.entity.SysDept;

@Service
public interface DeptService {
	//根据id查询部门信息
	SysDept getById(String id);
	//保存部门信息
	int save(SysDept sysDept);
	//修改部门信息
	int update(SysDept sysDept);
	//删除部门信息
	int delete(String id);
	//批量删除
	int batchDelete(String[] ids);
	//查询部门信息
	List<SysDept> list(SysDept sysDept);
	
	//部门机构树
	List<DeptTree> getDeptTree(String parentId);
	
	//判断是否是父节点
	boolean isParentNode(String id);

}

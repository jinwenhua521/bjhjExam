package com.nebulae.system.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysMenu;
import com.nebulae.system.entity.MenuTree;
import com.nebulae.system.entity.Tree;


@Service
public interface MenuService {
	
	//查询菜单授权
	Set<String> listPerms(String userId);
	
	//根据用户Id查询角色菜单
	List<Tree<SysMenu>> listMenuTree(String userId);
	
	//根据parentid查询菜单
	List<MenuTree> byParentMenuListTree(String parentId);
	
	
	//根据角色id查询并勾选菜单
	List<MenuTree> byParentOrRoleIdMenuTree(String parentId,String roleId);
	
	//保存
	int insert(SysMenu sysMenu);
	
	//修改
	int update(SysMenu sysMenu);
	
	//删除
	int delete(String id);
	
	//批量删除数据
	int batchDelete(String[] id);
	
	//根据id判断是否是父节点
	boolean isParentNode(String id);
	
	//菜单列表
   List<SysMenu> getList(SysMenu sysMenu);	
   
   
   //
   SysMenu getById(String id);

}

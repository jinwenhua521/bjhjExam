package com.nebulae.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebulae.system.entity.MenuTree;
import com.nebulae.system.entity.SysMenu;
import com.nebulae.system.entity.SysRoleMenu;
import com.nebulae.system.entity.Tree;
import com.nebulae.system.mapper.SysMenuMapper;
import com.nebulae.system.mapper.SysRoleMenuMapper;
import com.nebulae.system.service.MenuService;
import com.nebulae.system.shiro.utils.BindTree;
import com.nebulae.utils.ExampleUtils;
import com.nebulae.utils.Tools;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	SysMenuMapper sysMenuMapper;
	
	@Autowired
	SysRoleMenuMapper sysRoleMenuMapper;
	

	@Override
	public Set<String> listPerms(String userId) {
		Set<String> permsSet = null;
		if(!StringUtils.isEmpty(userId)) {
			List<String> perms = sysMenuMapper.listUserPerms(userId);
			permsSet=new HashSet<String>();
			for (String perm : perms) {
				if (StringUtils.isNotBlank(perm)) {
					permsSet.addAll(Arrays.asList(perm.trim().split(",")));
				}
			}
		}
		return permsSet;
	}


	@Override
	public List<Tree<SysMenu>> listMenuTree(String userId) {
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<SysMenu>> list = null;
		if(!StringUtils.isEmpty(userId)) {
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		List<SysMenu> sysMenu = sysMenuMapper.listMenuByUserId(userId);
		for (SysMenu sysMenuDO : sysMenu) {
			Tree<SysMenu> tree = new Tree<SysMenu>();
			tree.setId(sysMenuDO.getMenuId().toString());
			tree.setParentId(sysMenuDO.getParentId().toString());
			tree.setText(sysMenuDO.getLabel());
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("url", sysMenuDO.getSurl());
			attributes.put("icon", sysMenuDO.getIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		list=BindTree.buildList(trees, "0");
		}
		return list;
	}


	@Override
	public List<MenuTree> byParentMenuListTree(String parentId) {
		List<MenuTree> listTree=null;
		if(!StringUtils.isEmpty(parentId)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("PARENT_ID", parentId);
			List<SysMenu>  list=sysMenuMapper.selectByExample(example);
			listTree=new ArrayList<MenuTree>();
			for (int i=0;i<list.size();i++) {
				MenuTree tree=new MenuTree();
				BeanUtils.copyProperties(list.get(i), tree);
				tree.setIsParent(isParentNode(tree.getMenuId()));
				if (tree.getType().intValue()== 0) {
					tree.setIcon("/js/appjs/system/menu/img/menu02.png");
				}
				if (tree.getType().intValue() == 1) {
					tree.setIcon("/js/appjs/system/menu/img/menu03.png");
				}
				if (tree.getType().intValue() == 2) {
					tree.setIcon("/js/appjs/system/menu/img/menu04.png");
				}
				tree.setOpen(false);
				listTree.add(tree);
			}
		}
		return listTree;
	}


	@Override
	public int insert(SysMenu sysMenu) {
		int count=0;
		if(sysMenu!=null) {
			sysMenu.setMenuId(Tools.getID());
			sysMenu.setCreateDate(new Date());
			count=sysMenuMapper.insertSelective(sysMenu);
		}
		return count;
	}


	@Override
	public int update(SysMenu sysMenu) {
		int count=0;
		if(sysMenu!=null) {
			count=sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
		}
		return count;
	}


	@Override
	public int delete(String id) {
		int count=0;
		if(!StringUtils.isEmpty(id)) {
			count=sysMenuMapper.deleteByPrimaryKey(id);
		}
		return count;
	}


	@Override
	public boolean isParentNode(String id) {
		boolean isParent=false;
		if(!StringUtils.isEmpty(id)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("PARENT_ID", id);
			List<SysMenu> list=sysMenuMapper.selectByExample(example);
			if(list.size()>0) {
				isParent=true;
			}
		}
		return isParent;
	}


	@Override
	public List<SysMenu> getList(SysMenu sysMenu) {
		List<SysMenu> list=null;
		if(sysMenu!=null) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			if(!StringUtils.isEmpty(sysMenu.getLabel())) {
				criteria.andFieldLike("LABEL", "%"+sysMenu.getLabel()+"%");
			}
			if(!StringUtils.isEmpty(sysMenu.getPerms())) {
				criteria.andFieldEqualTo("PERMS", sysMenu.getPerms());
			}
			if(!StringUtils.isEmpty(sysMenu.getParentId())) {
				criteria.andFieldEqualTo("PARENT_ID", sysMenu.getParentId());
			}
			if(!StringUtils.isEmpty(sysMenu.getMenuId())) {
				criteria.andFieldEqualTo("MENU_ID", sysMenu.getMenuId());
				ExampleUtils.Criteria criteria1=example.createCriteria();
				criteria1.andFieldEqualTo("PARENT_ID", sysMenu.getMenuId());
				example.or(criteria1);
			}
			if(!StringUtils.isEmpty(sysMenu.getMenuId()) || !StringUtils.isEmpty(sysMenu.getParentId())) {
				list=sysMenuMapper.selectByExample(example);
			}
		}		
		return list;
	}


	@Override
	public SysMenu getById(String id) {
		SysMenu sysMenu=null;
		if(!StringUtils.isEmpty(id)) {
			sysMenu=sysMenuMapper.selectByPrimaryKey(id);
		}
		return sysMenu;
	}


	@Override
	public int batchDelete(String[] id) {
		int count=0;
		if(id.length>0) {
			count=sysMenuMapper.batchDelete(id);
		}
		return count;
	}
	
	private boolean setCheck(String menuId,String roleId) {
		boolean check=false;
		ExampleUtils example=new ExampleUtils();
		ExampleUtils.Criteria criteria=example.createCriteria();
		criteria.andFieldEqualTo("ROLE_ID", roleId);
		criteria.andFieldEqualTo("MENU_ID", menuId);
		List<SysRoleMenu> list=sysRoleMenuMapper.selectByExample(example);
		if(list.size()>0) {
			check=true;
		}
		return check;
	}


	@Override
	public List<MenuTree> byParentOrRoleIdMenuTree(String parentId, String roleId) {
		List<MenuTree> listTree=null;
		if(!StringUtils.isEmpty(parentId) && !StringUtils.isEmpty(roleId)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("PARENT_ID", parentId);
			List<SysMenu>  list=sysMenuMapper.selectByExample(example);
			listTree=new ArrayList<MenuTree>();
			for (int i=0;i<list.size();i++) {
				MenuTree tree=new MenuTree();
				BeanUtils.copyProperties(list.get(i), tree);
				tree.setIsParent(isParentNode(tree.getMenuId()));
				tree.setChecked(this.setCheck(tree.getMenuId(), roleId));
				if (tree.getType().intValue()== 0) {
					tree.setIcon("/js/appjs/system/menu/img/menu02.png");
				}
				if (tree.getType().intValue() == 1) {
					tree.setIcon("/js/appjs/system/menu/img/menu03.png");
				}
				if (tree.getType().intValue() == 2) {
					tree.setIcon("/js/appjs/system/menu/img/menu04.png");
				}
				tree.setOpen(false);
				listTree.add(tree);
			}
		}
		return listTree;
	}

}

package com.nebulae.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebulae.system.entity.DeptTree;
import com.nebulae.system.entity.SysDept;
import com.nebulae.system.mapper.SysDeptMapper;
import com.nebulae.system.service.DeptService;
import com.nebulae.utils.ExampleUtils;

@Service
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	SysDeptMapper sysDeptMapper;

	@Override
	public SysDept getById(String id) {
		SysDept sysDept=null;
		if(!StringUtils.isEmpty(id)) {
			sysDept=sysDeptMapper.selectByPrimaryKey(id);
		}
		return sysDept;
	}

	@Override
	public int save(SysDept sysDept) {
		int count=0;
		if(sysDept!=null) {
			count=sysDeptMapper.insertSelective(sysDept);
		}
		return count;
	}

	@Override
	public int update(SysDept sysDept) {
		int count=0;
		if(sysDept!=null) {
			sysDeptMapper.updateByPrimaryKeySelective(sysDept);
			count=sysDeptMapper.updateByNewFIdSelective(sysDept);
		}
		return count;
	}

	@Override
	public int delete(String id) {
		int count=0;
		if(!StringUtils.isEmpty(id)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldLike("DEPT_FID", "%"+id+"%");
			count=sysDeptMapper.deleteByExample(example);
		}
		return count;
	}

	@Override
	public List<SysDept> list(SysDept sysDept) {
		List<SysDept> list=null;
		if(sysDept!=null) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			if(!StringUtils.isEmpty(sysDept.getDeptName())) {
				criteria.andFieldLike("DEPT_NAME", "%"+sysDept.getDeptName()+"%");
			}
			if(!StringUtils.isEmpty(sysDept.getParentId())) {
				criteria.andFieldEqualTo("PARENT_ID", sysDept.getParentId());
			}
			if(!StringUtils.isEmpty(sysDept.getDeptFid())) {
				criteria.andFieldLike("DEPT_FID", sysDept.getDeptFid()+"%");
			}
			if(!StringUtils.isEmpty(sysDept.getDeptFname())) {
				criteria.andFieldLike("DEPT_FNAME", sysDept.getDeptFname()+"%");
			}
			if(!StringUtils.isEmpty(sysDept.getDeptId())) {
				criteria.andFieldEqualTo("DEPT_ID", sysDept.getDeptId());
				ExampleUtils.Criteria criteria1=example.createCriteria();
				criteria1.andFieldEqualTo("PARENT_ID", sysDept.getDeptId());
				example.or(criteria1);
			}
			if(!StringUtils.isEmpty(sysDept.getDeptId()) || !StringUtils.isEmpty(sysDept.getParentId())) {
				list=sysDeptMapper.selectByExample(example);
			}
		}
		return list;
	}
	
	@Override
	public boolean isParentNode(String id) {
		boolean isParent=false;
		if(!StringUtils.isEmpty(id)) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("PARENT_ID", id);
			List<SysDept> list=sysDeptMapper.selectByExample(example);
			if(list.size()>0) {
				isParent=true;
			}
		}
		return isParent;
	}

	@Override
	public List<DeptTree> getDeptTree(String parentId) {
		List<DeptTree> deptTree=null;
		if(!StringUtils.isEmpty(parentId)) {
			ExampleUtils example=new ExampleUtils();
			example.setOrderByClause(" dept_code ,seqenc asc");
			ExampleUtils.Criteria criteria=example.createCriteria();
			criteria.andFieldEqualTo("PARENT_ID", parentId);
			List<SysDept> list=sysDeptMapper.selectByExample(example);
			deptTree=new ArrayList<DeptTree>();
			for (int i=0;i<list.size();i++) {
				DeptTree tree=new DeptTree();
				BeanUtils.copyProperties(list.get(i), tree);
				tree.setIsParent(isParentNode(tree.getDeptId()));
				tree.setOpen(false);
				deptTree.add(tree);
			}
		}
		return deptTree;
	}

	@Override
	public int batchDelete(String[] ids) {
		int count=0;
		if(ids.length>0) {
			for(String id:ids) {
				count=delete(id);
			}
		}
		return count;
	}

}

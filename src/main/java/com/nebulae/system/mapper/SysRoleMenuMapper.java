package com.nebulae.system.mapper;

import com.nebulae.system.entity.SysRoleMenu;
import com.nebulae.utils.ExampleUtils;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    List<SysRoleMenu> selectByExample(ExampleUtils example);

    SysRoleMenu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysRoleMenu record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysRoleMenu record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);
    
    int batchDelete(String[] ids);
    
    int batchSave(List<SysRoleMenu> list);
}
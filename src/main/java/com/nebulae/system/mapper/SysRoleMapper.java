package com.nebulae.system.mapper;

import com.nebulae.system.entity.SysRole;
import com.nebulae.utils.ExampleUtils;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    List<SysRole> selectByExample(ExampleUtils example);

    SysRole selectByPrimaryKey(String roleId);

    int updateByExampleSelective(@Param("record") SysRole record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysRole record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    int batchDelete(String[] ids);
}
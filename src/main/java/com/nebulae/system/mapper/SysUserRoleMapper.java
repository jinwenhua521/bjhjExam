package com.nebulae.system.mapper;

import com.nebulae.system.entity.SysUserRole;
import com.nebulae.utils.ExampleUtils;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    List<SysUserRole> selectByExample(ExampleUtils example);

    SysUserRole selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysUserRole record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysUserRole record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
    
    int batchSave(List<SysUserRole> list);
}
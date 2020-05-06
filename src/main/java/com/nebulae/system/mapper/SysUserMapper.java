package com.nebulae.system.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nebulae.system.entity.SysUser;
import com.nebulae.utils.ExampleUtils;

@Mapper
public interface SysUserMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectByExampleWithBLOBs(ExampleUtils example);

    List<SysUser> selectByExample(ExampleUtils example);

    SysUser selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") ExampleUtils example);

    int updateByExampleWithBLOBs(@Param("record") SysUser record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysUser record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKeyWithBLOBs(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    int batchDelete(String[] ids);
}
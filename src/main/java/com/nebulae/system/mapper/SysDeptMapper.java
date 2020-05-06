package com.nebulae.system.mapper;

import com.nebulae.system.entity.SysDept;
import com.nebulae.utils.ExampleUtils;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysDeptMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    List<SysDept> selectByExample(ExampleUtils example);

    SysDept selectByPrimaryKey(String deptId);

    int updateByExampleSelective(@Param("record") SysDept record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysDept record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);
    
    int updateByNewFIdSelective(SysDept record);
}
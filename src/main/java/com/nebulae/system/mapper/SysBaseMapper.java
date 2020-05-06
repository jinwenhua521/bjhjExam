package com.nebulae.system.mapper;

import com.nebulae.system.entity.SysBase;
import com.nebulae.utils.ExampleUtils;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SysBaseMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String sid);

    int insert(SysBase record);

    int insertSelective(SysBase record);

    List<SysBase> selectByExample(ExampleUtils example);

    SysBase selectByPrimaryKey(String sid);

    int updateByExampleSelective(@Param("record") SysBase record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysBase record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysBase record);

    int updateByPrimaryKey(SysBase record);
    
    List<SysBase> listType();
    
    int batchDelete(String[] ids);
}
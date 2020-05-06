package com.nebulae.system.mapper;

import com.nebulae.system.entity.SysLog;
import com.nebulae.utils.ExampleUtils;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SysLogMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String sid);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    List<SysLog> selectByExample(ExampleUtils example);

    SysLog selectByPrimaryKey(String sid);

    int updateByExampleSelective(@Param("record") SysLog record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysLog record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
    
    int batchDelete(String[] ids);
}
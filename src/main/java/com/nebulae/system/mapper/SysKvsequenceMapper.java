package com.nebulae.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.nebulae.system.entity.SysKvsequence;
import com.nebulae.utils.ExampleUtils;

public interface SysKvsequenceMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String k);

    int insert(SysKvsequence record);

    int insertSelective(SysKvsequence record);

    List<SysKvsequence> selectByExample(ExampleUtils example);

    SysKvsequence selectByPrimaryKey(String k);

    int updateByExampleSelective(@Param("record") SysKvsequence record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysKvsequence record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysKvsequence record);

    int updateByPrimaryKey(SysKvsequence record);
}
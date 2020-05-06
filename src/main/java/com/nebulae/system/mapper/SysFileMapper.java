package com.nebulae.system.mapper;

import com.nebulae.system.entity.SysFile;
import com.nebulae.utils.ExampleUtils;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SysFileMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String sid);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    List<SysFile> selectByExample(ExampleUtils example);

    SysFile selectByPrimaryKey(String sid);

    int updateByExampleSelective(@Param("record") SysFile record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysFile record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);
    
    List<SysFile> selectLabel(String userId);
}
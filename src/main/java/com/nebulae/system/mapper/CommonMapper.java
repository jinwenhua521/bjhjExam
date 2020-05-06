package com.nebulae.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nebulae.system.entity.Common;

@Mapper
public interface CommonMapper {

	Common  getCurrentDateTime(@Param("flag")String flag); 
	
	List<Map> executeSqlQuery(String sql);
	
}

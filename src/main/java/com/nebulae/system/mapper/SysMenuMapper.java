package com.nebulae.system.mapper;

import com.nebulae.system.entity.SysMenu;
import com.nebulae.utils.ExampleUtils;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysMenuMapper {
    int countByExample(ExampleUtils example);

    int deleteByExample(ExampleUtils example);

    int deleteByPrimaryKey(String menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectByExample(ExampleUtils example);
    
    List<String> listUserPerms(String userId);
    
    List<SysMenu> listMenuByUserId(String userId);

    SysMenu selectByPrimaryKey(String menuId);

    int updateByExampleSelective(@Param("record") SysMenu record, @Param("example") ExampleUtils example);

    int updateByExample(@Param("record") SysMenu record, @Param("example") ExampleUtils example);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    
    int batchDelete(String[] ids);
}
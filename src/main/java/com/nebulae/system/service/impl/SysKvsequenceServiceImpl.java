package com.nebulae.system.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysKvsequence;
import com.nebulae.system.mapper.SysKvsequenceMapper;
import com.nebulae.system.service.SysKvsequenceService;


@Service
public class SysKvsequenceServiceImpl implements SysKvsequenceService {
	
	@Autowired
	SysKvsequenceMapper sysKvsequenceMapper;

	@Override
	public String getNextSequence(String key, String format) {
		Assert.notNull(key);
		if(format.isEmpty()) {
			format="0";
		}
		SysKvsequence kv=null;
		try {
			kv=sysKvsequenceMapper.selectByPrimaryKey(key);
			if(kv==null) {
				kv=new SysKvsequence();
				kv.setK(key);
				kv.setV(new BigDecimal(0));
				sysKvsequenceMapper.insertSelective(kv);
			}else {
				kv.setV(kv.getV().add(new BigDecimal(1)));
				sysKvsequenceMapper.updateByPrimaryKeySelective(kv);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return key+new DecimalFormat(format).format(kv.getV());
	}

}

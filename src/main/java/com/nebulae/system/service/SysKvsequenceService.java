package com.nebulae.system.service;

import org.springframework.stereotype.Service;

@Service
public interface SysKvsequenceService {
	
	String getNextSequence(String key,String format);

}

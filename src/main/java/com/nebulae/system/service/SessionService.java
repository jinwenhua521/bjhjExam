package com.nebulae.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nebulae.system.entity.UserOnline;


@Service
public interface SessionService {
	List<UserOnline> list();
	
	boolean forceLogout(String sessionId);
}

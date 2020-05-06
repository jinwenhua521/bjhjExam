package com.nebulae.aspect;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.nebulae.utils.AESUtil;
import com.nebulae.utils.HttpContextUtils;
import com.nebulae.utils.JsonUtils;
import com.nebulae.utils.Tools;

import org.springframework.stereotype.Component;

import com.nebulae.annotation.Log;
import com.nebulae.system.entity.SysLog;
import com.nebulae.system.entity.SysUser;
import com.nebulae.system.mapper.SysLogMapper;
import com.nebulae.system.shiro.utils.ShiroUtils;

@Aspect
@Component
public class LogAspect {
	
	@Autowired
	SysLogMapper sysLogMapper;
	
	@Pointcut("@annotation(com.nebulae.annotation.Log)")
	public void logPointCut() {
		
	}
	
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		// 保存日志
		saveLog(point, time);
		return result;

	}
	
	private void saveLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLog sysLog = new SysLog();
		Log syslog = method.getAnnotation(Log.class);
		if (syslog != null) {
			// 注解上的描述
			sysLog.setOperation(syslog.value());
		}
		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		// 请求的参数
		Object[] args = joinPoint.getArgs();
		try {
			String params="";
			for (int i = 0; i < args.length; i++) {  
				if(methodName.equals("ajaxLogin") && i==1) {
					params+=AESUtil.encrypt(args[i].toString())+","; 
				}else {
					params+=args[i]+","; 
				}
	        }  
			sysLog.setParams("["+params.substring(0,params.length()-1)+"]");
		} catch (Exception e) {

		}
		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		// 设置IP地址
		sysLog.setIp(HttpContextUtils.getIpAddr(request));
		// 用户名
		SysUser currUser = ShiroUtils.getUser();
		if (currUser!=null) {
			sysLog.setSid(Tools.getID());
			sysLog.setUserId(ShiroUtils.getUserId());
			sysLog.setUserName(ShiroUtils.getUser().getUserName());
			sysLog.setName(ShiroUtils.getUser().getName());
			sysLog.setResponse(new BigDecimal(time));
			// 系统当前时间
			Date date = new Date();
			sysLog.setOperationdate(date);
			// 保存系统日志
			sysLogMapper.insertSelective(sysLog);
		} 
	}

}

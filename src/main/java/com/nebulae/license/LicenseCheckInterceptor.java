package com.nebulae.license;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.alibaba.fastjson.JSON;
import com.nebulae.utils.AESUtil;
import com.nebulae.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LicenseCheckInterceptor extends HandlerInterceptorAdapter{
	

	 @Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        LicenseVerify licenseVerify = new LicenseVerify();
	        boolean verifyResult = licenseVerify.verify();
	        if(verifyResult){
	            return true;
	        }else{
	            response.setHeader("content-type", "text/html;charset=utf-8");
	            response.setContentType("text/html;charset=utf-8");
	            response.getWriter().write(JSON.toJSONString(R.error(AESUtil.AESDecode("u89vCOUuPg+UMZM4lyxiRZb0AgNaorP3ueAe3S4ydqzJXn1iPezMWP22ZB40P9456CmnNmHyLnJBNHu/n7JXNQ8rcjWJx1VK5hOc2vVFrPtRsdG3hNSwbG3XfWqC36XS"))));
	            return false;
	        } 
	        
	    }

}

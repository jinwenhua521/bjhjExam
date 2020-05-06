package com.nebulae.system.controller;

import java.util.Map;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nebulae.utils.VerificationCode;

@Controller
@RequestMapping("/authCode")
public class VerificationCodeController {
	
	
	//生成验证码
		@RequestMapping("/getAuthCode")
		public void getAuthCode(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
			//设置response头信息
	        //禁止缓存
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        //获取图像验证码信息
	        Map<String,Object> map=VerificationCode.getGraphics(70, 28,4);
	        session.setAttribute("validateCode", map.get("validateCode"));
	        ImageIO.write((RenderedImage) map.get("image"), "JPEG", response.getOutputStream());
	        response.getOutputStream().flush();
		}

}

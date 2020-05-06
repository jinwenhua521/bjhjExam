package com.nebulae.system.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.util.StringUtils;

import com.nebulae.annotation.Log;
import com.nebulae.system.entity.SysMenu;
import com.nebulae.system.entity.SysUser;
import com.nebulae.system.entity.Tree;
import com.nebulae.system.service.MenuService;
import com.nebulae.system.service.UserService;
import com.nebulae.system.shiro.utils.ShiroUtils;
import com.nebulae.utils.MD5Util;
import com.nebulae.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;



@Controller
@Api(value="用户登录",tags= {"用户登录"})
public class LoginController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;

	@Autowired
	UserService userService;

	//@Autowired
	//private IndexService indexService;

	@GetMapping({ "/", "" })
	String welcome(Model model) {
		return "redirect:/login";
	}

	@GetMapping("userBase")
	String userBase(Model model) {
		model.addAttribute("SysUser", getUser());
		return "/system/user/headPortrait";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@ApiOperation(value="用户登录",notes="根据用户名，密码，验证码登录系统",produces="application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name="username用户名",required = true, dataType = "String"),
			@ApiImplicitParam(name="password密码",required = true, dataType = "String"),
			@ApiImplicitParam(name="vcode验证码",required = true, dataType = "String")
	})
	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password,String vcode,Boolean rememberMe) {
		Session session = SecurityUtils.getSubject().getSession();
		String sVcodeString=(String)session.getAttribute("validateCode");
		SecurityUtils.getSubject().logout();
		if(StringUtils.isEmpty(vcode)) {
			return R.error("验证码不能为空");
		}else {
		     if(!vcode.equals(sVcodeString)) {
		    	 return R.error("验证码错误");
		     }
		}
		UsernamePasswordToken token=null;
		try {
			if(rememberMe==null) {
				token = new UsernamePasswordToken(username,MD5Util.MD5EncodeStrUtf8(password));
			}else {
				token = new UsernamePasswordToken(username,MD5Util.MD5EncodeStrUtf8(password),rememberMe);
			}
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			//验证是否登录成功
			if(!subject.isAuthenticated()) {
				token.clear();
			}
			return R.ok();
		}catch(UnknownAccountException uae) {
			return R.error("帐号不存在");
		}catch(IncorrectCredentialsException ice){
			return R.error("密码不正确");
		}catch(LockedAccountException le) {
			return R.error("帐号已锁定");
		}catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<SysMenu>> menus = menuService.listMenuTree(getUserId());

		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		model.addAttribute("user", getUser());
		return "index";
	}

	//获取头像
	@GetMapping("/getPhoto")
	void photo(HttpServletRequest request,HttpServletResponse response) {
		SysUser user=userService.getUserId(getUserId());
		if(!StringUtils.isEmpty(user.getPhoto())) {
			response.setContentType("img/jpeg");
			response.setCharacterEncoding("utf-8");
			OutputStream outputStream=null;
			InputStream in=null;
			try {
				outputStream=response.getOutputStream();
				in=new ByteArrayInputStream(user.getPhoto());
				int length=0;
				byte[] buf=new byte[1024];
				while((length=in.read(buf, 0, 1024))!=-1){
					outputStream.write(buf,0,length);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					in.close();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Log("退出")
	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

	@GetMapping("/403")
	String error403() {
		return "403";
	}

}

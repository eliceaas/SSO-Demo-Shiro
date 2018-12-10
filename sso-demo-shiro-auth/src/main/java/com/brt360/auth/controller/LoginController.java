package com.brt360.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.brt360.common.redisDB.RoleDB;
import com.brt360.common.redisDB.UserDB;
import com.brt360.common.util.UriUtil;

@Controller
public class LoginController {
	
	@Autowired
	private UserDB userDB;
	
	@Autowired
	private RoleDB roleDB;
	
	@RequestMapping("/loginPage")
	public String loginPage(String redirect_url, Model model) {
		model.addAttribute("redirect_url", redirect_url);
		return "loginPage";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public JSONObject login(HttpServletResponse response, String redirect_url, String username, String password) {
		JSONObject result = new JSONObject();
		try {
			//登录
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
			subject.login(usernamePasswordToken);
			//用户信息存入session
			JSONObject user = new JSONObject();
			user.put("username", username);
			user.put("password", userDB.get(username));
			user.put("roles", roleDB.get(username));
			subject.getSession().setAttribute(UriUtil.getUserSessionKey(), user);
			//回调
			result.put("success", true);
			result.put("data", redirect_url);
			return result;
		} catch (Exception e) {
			//登陆失败
			result.put("success", false);
			result.put("data", e.getMessage());
			return result;
		}
	}
	
	@RequestMapping("/logout")
	public String logout(String redirect_url, Model model) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		subject.getSession().removeAttribute(UriUtil.getUserSessionKey());
		//回调地址
		model.addAttribute("redirect_url", redirect_url);
		return "loginPage";
	}
	
}

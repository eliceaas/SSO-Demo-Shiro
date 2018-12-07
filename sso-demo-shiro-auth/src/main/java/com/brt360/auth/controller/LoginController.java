package com.brt360.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
public class LoginController {
	
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
		//回调地址
		model.addAttribute("redirect_url", redirect_url);
		return "loginPage";
	}
	
	@RequestMapping("/403")
	public String unAuth() {
		return "403";
	}

}

package com.brt360.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
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
public class RegisterController {
	
	@Autowired
	private UserDB userDB;
	
	@Autowired
	private RoleDB roleDB;
	
	//跳转到注册页
	@RequestMapping("/registerPage")
	public String returnRegister(String redirect_url, Model model) {
		model.addAttribute("redirect_url", redirect_url);
		return "register";
	}
	
	//注册
	@RequestMapping("/register")
	@ResponseBody
	public JSONObject register(String username, String password) {
		JSONObject result = new JSONObject();
		try {
			//验证账号是否可用
			if (userDB.hasKey(username)) {
				result.put("success", false);
				result.put("data", "账号已被注册");
				return result;
			}
			
			//新增用户
			userDB.put(username, password);
			//放入USER权限
			List<String> roles = new ArrayList<String>();
			roles.add("ROLE_USER");
			roleDB.put(username, roles);
			
			//登录
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
			subject.login(usernamePasswordToken);
			
			//用户信息存入session
			JSONObject user = new JSONObject();
			user.put("username", username);
			user.put("password", userDB.get(username));
			user.put("roles", roleDB.get(username));
			subject.getSession().setAttribute(UriUtil.getUserSessionKey(), user);
			
			result.put("success", true);
			result.put("data", "注册成功");
			return result;
		} catch (AuthenticationException e) {
			//注册失败
			result.put("success", false);
			result.put("data", e.getMessage());
			return result;
		}
	}
	
}

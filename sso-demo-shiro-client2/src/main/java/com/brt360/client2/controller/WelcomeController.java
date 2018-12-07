package com.brt360.client2.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
	
	@RequestMapping("/welcome")
	public String welcome(Model model) {
		Subject subject = SecurityUtils.getSubject();
		model.addAttribute("username", subject.getPrincipal().toString());
		return "welcome";
	}

}

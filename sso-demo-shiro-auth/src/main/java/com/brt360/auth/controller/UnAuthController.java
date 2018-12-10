package com.brt360.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UnAuthController {
	
	@RequestMapping("/403")
	public String unAuth() {
		return "403";
	}

}

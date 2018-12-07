package com.brt360.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brt360.common.util.UriUtil;

@Controller
public class DispatherController {
	
	@RequestMapping("/dispathLogin")
	public void dispathLogin(HttpServletRequest request, HttpServletResponse response, String host) {
		try {
			//获取redirect_url
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String redirect_url = host + savedRequest.getRequestUrl();
			//dispath
			response.sendRedirect(UriUtil.getLoginPageUrl(redirect_url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
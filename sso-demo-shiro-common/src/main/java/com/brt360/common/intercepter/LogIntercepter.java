package com.brt360.common.intercepter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brt360.common.util.UriUtil;

@Aspect
@Component
public class LogIntercepter {
	
	private final static Logger logger = LoggerFactory.getLogger(LogIntercepter.class);
	
	//监控controller public方法
	@Pointcut("execution(public * com.brt360.*.controller..*.*(..))")
	public void logController() {}
	
	@Before("logController()")
	public void afterController(JoinPoint joinPoint) {
		//日期时间
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String now = LocalDateTime.now().format(formatter);
		
		//用户及其权限
		String principal;
		JSONArray roles;
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.getPrincipal() != null) {
			principal = subject.getPrincipal().toString();
			JSONObject user = (JSONObject) subject.getSession().getAttribute(UriUtil.getUserSessionKey());
			roles = user.getJSONArray("roles");
		} else {
			principal = "guest";
			roles = new JSONArray();
		}
		
		//来源ip和访问链接
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = request.getRemoteAddr();
		String url = request.getRequestURL().toString();
		
		logger.info("[" + now + "] " + principal + "(" + roles.toString() + ")@" + ip + " " + url);
	}

}

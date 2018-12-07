package com.brt360.client1.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brt360.common.util.UriUtil;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	
	@Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(UriUtil.getLoginUrl(UriUtil.getClient1Host()));
        shiroFilterFactoryBean.setUnauthorizedUrl(UriUtil.getUnauthUrl());

        /*配置过滤器*/
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //放开静态资源请求
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        //放开login
        filterChainDefinitionMap.put(UriUtil.getLoginUrl(), "anon");
        //配置权限请求
        filterChainDefinitionMap.put("/sys**", "roles[ROLE_ADMIN]");
        //其余请求需要认证
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
        return shiroFilterFactoryBean;
    }
	
    //thymeleaf支持
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
    
}
package com.brt360.common.redisDB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoleDB {
	
private static final String ROLE_DB_KEY = "ROLE_DB";
	
	//按名字装配
	@Resource
	private RedisTemplate<String, List<String>> redisTemplate;
	
	@PostConstruct
	public void init() {
		//初始权限
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_ADMIN");
		put("root", roles);
	}
	
	public void put(String username, List<String> roles) {
		redisTemplate.opsForHash().put(ROLE_DB_KEY, username, roles);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> get(String username) {
		Object roles = redisTemplate.opsForHash().get(ROLE_DB_KEY, username);
		if (roles == null) {
			return null;
		} else {
			return (List<String>)roles;
		}
	}
	
	public boolean hasKey(String username) {
		return redisTemplate.opsForHash().hasKey(ROLE_DB_KEY, username);
	}

}

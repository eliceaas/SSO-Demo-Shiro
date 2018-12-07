package com.brt360.common.redisDB;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDB {
	
	private static final String USER_DB_KEY = "USER_DB";
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@PostConstruct
	public void init() {
		//初始用户
		put("root", "root");
	}
	
	public void put(String username, String password) {
		redisTemplate.opsForHash().put(USER_DB_KEY, username, password);
	}
	
	public String get(String username) {
		Object password = redisTemplate.opsForHash().get(USER_DB_KEY, username);
		if (password == null) {
			return null;
		} else {
			return password.toString();
		}
	}
	
	public boolean hasKey(String username) {
		return redisTemplate.opsForHash().hasKey(USER_DB_KEY, username);
	}

}

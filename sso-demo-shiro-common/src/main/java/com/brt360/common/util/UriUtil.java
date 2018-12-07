package com.brt360.common.util;

public class UriUtil {
	
	private static final String LOGIN_URL = "/dispathLogin";
	
	private static final String UNAUTH_URL = "http://localhost:8000/403";
	
	private static final String REDIS_HOST = "localhost:6379";
	
	private static final String LOGIN_PAGE_URL = "http://localhost:8000/loginPage";
	
	private static final String CLIENT1_HOST = "http://localhost:8081";
	
	private static final String CLIENT2_HOST = "http://localhost:8082";
	
	public static String getRedisHost() {
		return REDIS_HOST;
	}

	public static String getLoginPageUrl(String redirect_url) {
		return LOGIN_PAGE_URL + "?redirect_url=" + redirect_url;
	}
	
	public static String getUnauthUrl() {
		return UNAUTH_URL;
	}
	
	public static String getLoginUrl(String host) {
		return LOGIN_URL + "?host=" + host;
	}
	
	public static String getLoginUrl() {
		return LOGIN_URL;
	}

	public static String getClient1Host() {
		return CLIENT1_HOST;
	}
	
	public static String getClient2Host() {
		return CLIENT2_HOST;
	}

}

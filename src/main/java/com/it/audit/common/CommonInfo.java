package com.it.audit.common;

public class CommonInfo {
	
	public static final String USER_COOKIE_KEY = "it_audit_token";
	
	public static final String LOCAL_CACHE_KEY_LOGIN_FORMAT = "login_token_%s";
	public static final Long LOCAL_CACHE_KEY_LOGIN_EXPIRE = 24*60*60*1000L;
	public static final String LOCAL_CACHE_KEY_USER_INFO = "user_%s";
	public static final Long LOCAL_CACHE_KEY_USER_INFO_EXPIRE = 10*60*1000L;
}

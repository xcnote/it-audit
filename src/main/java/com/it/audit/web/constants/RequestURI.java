package com.it.audit.web.constants;

public class RequestURI {
	public static final String BASE_URI = "/";
	
	public static final String LOGIN_URI = "/login";
	public static final String LOGOUT_URI = "/logout";
	public static final String INDEX_URI = "/index";
	public static final String INDEX_LEFT_URI = INDEX_URI + "/left/{role}";
	public static final String INDEX_CENTER_URI = INDEX_URI + "/center/{role}";
	
	public static final String USER_PAGE = INDEX_URI + "/user/page";
	public static final String USER_CREATE = INDEX_URI + "/user/create";

	public static final String ERROR_PAGE = "/404";
	
	public static final String[] INTERCEPT_URI = new String[]{BASE_URI, INDEX_URI, INDEX_URI + "/**", LOGOUT_URI};
	public static final String[] AUTH_URI = INTERCEPT_URI;
}

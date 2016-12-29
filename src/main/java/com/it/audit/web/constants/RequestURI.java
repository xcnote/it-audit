package com.it.audit.web.constants;

public class RequestURI {
	public static final String BASE_URI = "/";
	
	public static final String LOGIN_URI = "/login";
	public static final String LOGOUT_URI = "/logout";
	public static final String INDEX_URI = "/index";
	public static final String INDEX_URI_RELAY = "/index/{index}/{modul}/{action}";
	public static final String INDEX_LEFT_URI = INDEX_URI + "/left/{role}";
	public static final String INDEX_CENTER_URI = INDEX_URI + "/center/{role}";
	
	//模块对应关系：index-用户角色枚举， model-角色模块（如user），action-模块功能（如page）
	//超级管理员-用户管理
	public static final String USER_PAGE = INDEX_URI + "/user/page";
	public static final String USER_CREATE = INDEX_URI + "/user/create";
	public static final String USER_UPDATE = INDEX_URI + "/user/update";
	public static final String USER_DELETE = INDEX_URI + "/user/delete";
	
	//项目经理-项目创建与列表
	public static final String MANAGER_BASE = INDEX_URI + "/manager";
	public static final String MANAGER_OBJECT_CREATE = MANAGER_BASE + "/create";
	public static final String MANAGER_OBJECT_MANAGE = MANAGER_BASE + "/manage";
	public static final String MANAGER_OBJECT_PAGE = MANAGER_BASE + "/page";
	//项目经理-项目管理-测试范围
	public static final String MANAGER_OBJECT_TEST_RANGE = MANAGER_BASE + "/test/range";
	public static final String MANAGER_OBJECT_TEST_GCLIST = MANAGER_BASE + "/test/gclist";
	public static final String MANAGER_OBJECT_TEST_GCIMPORT = MANAGER_BASE + "/test/gcImport";
	public static final String MANAGER_OBJECT_TEST_GCUPDATE = MANAGER_BASE + "/test/gcUpdate";
	public static final String MANAGER_OBJECT_TEST_GCDELETE = MANAGER_BASE + "/test/gcDelete";
	public static final String MANAGER_OBJECT_TEST_ACLIST = MANAGER_BASE + "/test/aclist";
	public static final String MANAGER_OBJECT_TEST_ACUPDATE = MANAGER_BASE + "/test/acUpdate";
	public static final String MANAGER_OBJECT_TEST_ACDELETE = MANAGER_BASE + "/test/acDelete";
	public static final String MANAGER_OBJECT_TEST_DALIST = MANAGER_BASE + "/test/dalist";
	public static final String MANAGER_OBJECT_TEST_DAUPDATE = MANAGER_BASE + "/test/daUpdate";
	public static final String MANAGER_OBJECT_TEST_DADELETE = MANAGER_BASE + "/test/daDelete";
	//项目经理-项目管理-成员管理
	public static final String MANAGER_OBJECT_USER_LIST = MANAGER_BASE + "/user/list";

	public static final String ERROR_PAGE = "/404";
	
	public static final String[] INTERCEPT_URI = new String[]{BASE_URI, INDEX_URI, INDEX_URI + "/**", LOGOUT_URI};
	public static final String[] AUTH_URI = INTERCEPT_URI;
}

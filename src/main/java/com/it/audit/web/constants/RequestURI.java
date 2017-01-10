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
	
	//高管
	public static final String EXECUTIVE_BASE = INDEX_URI + "/executive";
	public static final String EXECUTIVE_INDEX = EXECUTIVE_BASE + "/index";
	public static final String EXECUTIVE_OBJECT = EXECUTIVE_BASE + "/object";
	public static final String EXECUTIVE_INDUSTRY = EXECUTIVE_BASE + "/industry";
	public static final String EXECUTIVE_SCALE = EXECUTIVE_BASE + "/scale";
	//高管 - 报告调阅 - 报告
	public static final String EXECUTIVE_REPORT_LIST = EXECUTIVE_BASE + "/report";
	public static final String EXECUTIVE_REPORT_INFO = EXECUTIVE_BASE + "/report/info";
	public static final String EXECUTIVE_REPORT_DOWN = EXECUTIVE_BASE + "/report/down";
	
	//项目经理-项目创建与列表
	public static final String MANAGER_BASE = INDEX_URI + "/manager";
	public static final String MANAGER_OBJECT_CREATE = MANAGER_BASE + "/create";
	public static final String MANAGER_OBJECT_UPDATE = MANAGER_BASE + "/update";
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
	public static final String MANAGER_OBJECT_USER_ADD = MANAGER_BASE + "/user/add";
	public static final String MANAGER_OBJECT_USER_DELETE = MANAGER_BASE + "/user/delete";
	//项目经理-项目管理-任务分配
	public static final String MANAGER_OBJECT_TASK_ALLOT = MANAGER_BASE + "/task/allot";
	public static final String MANAGER_OBJECT_TASK_GCLIST = MANAGER_BASE + "/task/gclist";
	public static final String MANAGER_OBJECT_TASK_ACLIST = MANAGER_BASE + "/task/aclist";
	public static final String MANAGER_OBJECT_TASK_DALIST = MANAGER_BASE + "/task/dalist";
	//项目经理-项目管理-工作复核
	public static final String MANAGER_OBJECT_REVIEW_INDEX = MANAGER_BASE + "/review/index";
	public static final String MANAGER_OBJECT_REVIEW_GCLIST = MANAGER_BASE + "/review/gclist";
	public static final String MANAGER_OBJECT_REVIEW_ACLIST = MANAGER_BASE + "/review/aclist";
	public static final String MANAGER_OBJECT_REVIEW_DALIST = MANAGER_BASE + "/review/dalist";
	public static final String MANAGER_OBJECT_REVIEW_UPDATE = MANAGER_BASE + "/review/update";
	public static final String MANAGER_OBJECT_REVIEW_GCUPDATE = MANAGER_BASE + "/review/gcupdate";
	public static final String MANAGER_OBJECT_REVIEW_ACUPDATE = MANAGER_BASE + "/review/acupdate";
	public static final String MANAGER_OBJECT_REVIEW_DAUPDATE = MANAGER_BASE + "/review/daupdate";
	//项目经理-项目报告
	public static final String MANAGER_OBJECT_REPORT = MANAGER_BASE + "/report";
	public static final String MANAGER_OBJECT_REPORT_INFO = MANAGER_BASE + "/report/info";
	public static final String MANAGER_OBJECT_REPORT_CREATE = MANAGER_BASE + "/report/create";
	public static final String MANAGER_OBJECT_PUSHREVIEW = MANAGER_BASE + "/pushreview";
	
	//质量复核
	public static final String REVIEWER_BASE = INDEX_URI + "/reviewer";
	public static final String REVIEWER_OBJECT_LIST = REVIEWER_BASE + "/page";
	//质量复核 - 项目复核 - 底稿 
	public static final String REVIEWER_OBJECT_TEST_INDEX = REVIEWER_BASE + "/test/index";
	public static final String REVIEWER_OBJECT_TEST_GCLIST = REVIEWER_BASE + "/test/gclist";
	public static final String REVIEWER_OBJECT_TEST_ACLIST = REVIEWER_BASE + "/test/aclist";
	public static final String REVIEWER_OBJECT_TEST_DALIST = REVIEWER_BASE + "/test/dalist";
	public static final String REVIEWER_OBJECT_TEST_DEATIL = REVIEWER_BASE + "/test/detail";
	//质量复核 - 项目复核 - 报告
	public static final String REVIEWER_OBJECT_REPORT_INDEX = REVIEWER_BASE + "/report/index";
	public static final String REVIEWER_OBJECT_REPORT_INFO = REVIEWER_BASE + "/report/info";
	public static final String REVIEWER_OBJECT_REPORT_DOWN = REVIEWER_BASE + "/report/down";
	//质量复核 - 项目复核 - 基本信息
	public static final String REVIEWER_OBJECT_INFO = REVIEWER_BASE + "/objectinfo";
	//质量复核 - 项目复核 - 反馈意见
	public static final String REVIEWER_OBJECT_FEEDBACK = REVIEWER_BASE + "/feedback";
	
	//审计师
	public static final String AUDITOR_BASE = INDEX_URI + "/auditor";
	public static final String AUDITOR_OBJECT_LIST = AUDITOR_BASE + "/page";
	public static final String AUDITOR_OBJECT_INFO = AUDITOR_BASE + "/objectinfo";
	//审计师-项目工作-工作任务
	public static final String AUDITOR_OBJECT_TASK_INDEX = AUDITOR_BASE + "/task/index";
	public static final String AUDITOR_OBJECT_TASK_GCLIST = AUDITOR_BASE + "/task/gclist";
	public static final String AUDITOR_OBJECT_TASK_ACLIST = AUDITOR_BASE + "/task/aclist";
	public static final String AUDITOR_OBJECT_TASK_DALIST = AUDITOR_BASE + "/task/dalist";
	public static final String AUDITOR_OBJECT_TASK_UPDATE = AUDITOR_BASE + "/task/update";
	public static final String AUDITOR_OBJECT_TASK_GCUPDATE = AUDITOR_BASE + "/task/gcupdate";
	public static final String AUDITOR_OBJECT_TASK_ACUPDATE = AUDITOR_BASE + "/task/acupdate";
	public static final String AUDITOR_OBJECT_TASK_DAUPDATE = AUDITOR_BASE + "/task/daupdate";
	public static final String AUDITOR_OBJECT_TASK_SUBMIT = AUDITOR_BASE + "/task/submit";
	//审计师-项目报告
	public static final String AUDITOR_OBJECT_REPORT = AUDITOR_BASE + "/report";
	public static final String AUDITOR_OBJECT_REPORT_INFO = AUDITOR_BASE + "/report/info";
	public static final String AUDITOR_OBJECT_REPORT_DOWN = AUDITOR_BASE + "/report/down";
	
	//公共接口
	public static final String COMMON_TEST_GC_EXPORT = INDEX_URI + "/gcexport";
	public static final String COMMON_TEST_AC_EXPORT = INDEX_URI + "/acexport";
	public static final String COMMON_TEST_DA_EXPORT = INDEX_URI + "/daexport";
	public static final String COMMON_TEST_REPORT_EXPORT = INDEX_URI + "/reportexport";
	public static final String COMMON_TEST_PROBLEM_EXPORT = INDEX_URI + "/problemexport";
	
	public static final String ERROR_PAGE = "/404";
	
	public static final String[] INTERCEPT_URI = new String[]{BASE_URI, INDEX_URI, INDEX_URI + "/**", LOGOUT_URI};
	public static final String[] AUTH_URI = INTERCEPT_URI;
}

package com.it.audit.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.it.audit.auth.AuthContextHolder;
import com.it.audit.enums.UserStatus;
import com.it.audit.exception.UserDisableException;
import com.it.audit.model.UserInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户权限控制拦截器
 * @author WANGXIAOCHENG
 *
 */
@Component
@Slf4j
public class AuditUserAuthInterceptor extends HandlerInterceptorAdapter {

//	@Autowired
//	private VideoUserPersistenceService videoUserPersistenceService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String path = request.getRequestURI();
//		List<String> authRequestURI = RequestURI.USER_AUTH_OPERATE_URIS;
		
//		//请求地址需检查用户权限
//		if(authRequestURI.contains(path)){
		UserInfo user = AuthContextHolder.get().getUserInfo();
		if(user.getStatus() == UserStatus.disable){
			log.info("user {} request refuse. user is disabled.");
			throw new UserDisableException("user login request reject because disable.");
		}
//		}
		return super.preHandle(request, response, handler);
	}

}

package com.it.audit.auth.interceptor;

//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.it.audit.auth.AuthContextHolder;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Setter
public class AuditCookieInterceptor extends HandlerInterceptorAdapter {
	@Value("${system.skipauth.prefix}")
	private String skipAuthPrefix;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI();
		String requestId = AuthContextHolder.get().getRequestId();
		log.info("{} request path {}",requestId,path);
		boolean skipAuth = false;
		for(String prefix : skipAuthPrefix.split(",")){
			if(path.startsWith(prefix)){
				skipAuth = true;
				break;
			}
		}
		log.info("{} is skipAuth {}",requestId,skipAuth);
		if(skipAuth){
			return super.preHandle(request, response, handler);
		}
		
//		Cookie[] cookies = request.getCookies();
//		Cookie kscCookie = null;
//		Cookie subCookie = null;
//		if (cookies != null) {
//			for (Cookie cookie : cookies) {
//			}
//		}

//		String token = kscCookie.getValue();
//		String ip = request.getHeader("X-Forwarded-For");
		AuthContextHolder.get().setLoginInfo(null);
		return super.preHandle(request, response, handler);
	}
}

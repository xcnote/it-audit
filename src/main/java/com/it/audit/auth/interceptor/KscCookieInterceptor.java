package com.it.audit.auth.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ksyun.video.common.auth.AuthContextHolder;
import com.ksyun.video.common.exception.AccountNotSupportException;
import com.ksyun.video.common.exception.NotLogginException;
import com.ksyun.video.common.model.kscuser.KscUser;
import com.ksyun.video.common.util.CommonUtil;
import com.ksyun.video.service.KscUserService;

@Component
@Slf4j
@Setter
public class KscCookieInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private KscUserService kscUserService;
	
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
		
		Cookie[] cookies = request.getCookies();
		Cookie kscCookie = null;
		Cookie subCookie = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(CommonUtil.subdigest)) {
					subCookie = cookie;
					//break;
				}
				if (cookie.getName().equals(CommonUtil.kscdigest)) {
					kscCookie = cookie;
					//break;
				}
			}
		}
		if (subCookie != null && StringUtils.isNotBlank(subCookie.getValue())) {
			log.warn("found subdigest in cookie,no support for new child account");
			throw new AccountNotSupportException("no support for old child account");
		}
		if (kscCookie == null) {
			log.warn("not found kscdigest in cookie");
			throw new NotLogginException(false, "not found ksc cookie");
		}

		String token = kscCookie.getValue();
		String ip = request.getHeader("X-Forwarded-For");
		KscUser loginInfo = kscUserService.getLoginInfo(token, ip);
		if (loginInfo == null) {
			log.warn("can not get logininfo ,client not logging");
			throw new NotLogginException(false, "can not get loginInfo");
		}
		if (loginInfo.getNewChildAccount()) {
			log.warn("no support for new child account");
			throw new AccountNotSupportException("no support for new child account");
		}

		AuthContextHolder.get().setLoginInfo(loginInfo);
		return super.preHandle(request, response, handler);
	}
}

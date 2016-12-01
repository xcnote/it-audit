package com.it.audit.auth.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ksyun.video.common.auth.AuthContextHolder;
import com.ksyun.video.common.enums.UserStatus;
import com.ksyun.video.common.util.CommonUtil;
import com.ksyun.video.domain.VideoUser;
import com.ksyun.video.persistence.service.VideoUserPersistenceService;
import com.ksyun.video.web.constants.RequestURI;

/**
 * 用户权限控制拦截器
 * @author WANGXIAOCHENG
 *
 */
@Component
@Slf4j
public class VideoUserAuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private VideoUserPersistenceService videoUserPersistenceService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI();
		List<String> authRequestURI = RequestURI.USER_AUTH_OPERATE_URIS;
		
		//请求地址需检查用户权限
		if(authRequestURI.contains(path)){
			Long userId = Long.parseLong(AuthContextHolder.get().getLoginInfo().getUserId());
			VideoUser videoUser = this.videoUserPersistenceService.findByUserId(userId);
			log.error("Check authority. request uri:{}, userId:{}", path, userId);
			CommonUtil.checkPermission(videoUser != null && !videoUser.getStatus().equals(UserStatus.DISABLED), CommonUtil.USER_DISABLED_MESSAGE);
		}
		return super.preHandle(request, response, handler);
	}

}

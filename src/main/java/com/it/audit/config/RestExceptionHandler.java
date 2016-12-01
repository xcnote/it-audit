package com.it.audit.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ksyun.ks3.exception.Ks3ServiceException;
import com.ksyun.ks3.exception.serviceside.TemporaryRedirectException;
import com.ksyun.video.common.auth.AuthContextHolder;
import com.ksyun.video.common.exception.AccessDinedException;
import com.ksyun.video.common.exception.AccountNotSupportException;
import com.ksyun.video.common.exception.NotFoundException;
import com.ksyun.video.common.exception.NotLogginException;
import com.ksyun.video.common.model.error.RestError;
import com.ksyun.video.common.util.CommonUtil;

@Slf4j
public class RestExceptionHandler extends AbstractHandlerExceptionResolver
		implements InitializingBean {
	@Value("${system.callback.prefix}")
	private String callback;
//	@Value("${login.page.url}")
//	private String loginPageUrl;
//	@Value("${system.region}")
//	private String region;

	private final Map<String, Integer> exceptionStatusMapping = new HashMap<String, Integer>();

	//当处理回调失败时，需要KS3继续回调的异常
	private final List<String> callBackNeedRetry = new ArrayList<String>();
	
	//需要统一处理错误信息的异常
	private final Map<String,String> expMsgOverride = new HashMap<String,String>();

	@Override
	public void afterPropertiesSet() throws Exception {
		exceptionStatusMapping.put(TemporaryRedirectException.class.getName(), 400);
		exceptionStatusMapping.put(IllegalArgumentException.class.getName(),
				400);
		exceptionStatusMapping.put(NotLogginException.class.getName(), 401);
		exceptionStatusMapping.put(AccountNotSupportException.class.getName(), 402);
		exceptionStatusMapping.put(AccessDinedException.class.getName(), 403);
		exceptionStatusMapping.put(NotFoundException.class.getName(), 404);
		exceptionStatusMapping.put(RuntimeException.class.getName(), 404);
		exceptionStatusMapping.put(
				HttpRequestMethodNotSupportedException.class.getName(), 405);
		exceptionStatusMapping.put(
				HttpMessageNotReadableException.class.getName(), 400);
		exceptionStatusMapping.put(
				MissingServletRequestParameterException.class.getName(), 400);
		exceptionStatusMapping.put(TypeMismatchException.class.getName(), 400);
		exceptionStatusMapping.put("javax.validation.ValidationException", 400);
		// 404
		exceptionStatusMapping.put(
				NoSuchRequestHandlingMethodException.class.getName(), 404);
		exceptionStatusMapping
				.put("org.hibernate.ObjectNotFoundException", 404);
		// 406
		exceptionStatusMapping.put(
				HttpMediaTypeNotAcceptableException.class.getName(), 406);
		exceptionStatusMapping.put(
				"org.springframework.dao.DataIntegrityViolationException", 409);
		// 415
		exceptionStatusMapping.put(
				HttpMediaTypeNotSupportedException.class.getName(), 415);
		
		
//		expMsgOverride.put(TemporaryRedirectException.class.getName(),"当前仅支持"+region+"的bucket");
		expMsgOverride.put(TemporaryRedirectException.class.getName(), "当前不支持该区域的bucket");
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String requestId = AuthContextHolder.get().getRequestId();
		//登陆cookie问题及用户登陆超时问题不打印日志
		if(ex instanceof NotLogginException && !((NotLogginException) ex).isPrintLog()){
			log.warn("{} catch exception {}", requestId, "user login system fail.");
		} else {
			log.warn("{} catch exception {}", requestId, CommonUtil.getTrace(ex));
		}
		String path = request.getRequestURI();
		String errorMsg = "";
		int status = 400;
		if (path.startsWith(callback)) {
			status = 200;
			if(callBackNeedRetry.contains(ex.getClass().getName()))
				errorMsg = "{\"result\":false}";
			else
				errorMsg = "{\"result\":true}";
		} else {
			status = exceptionStatusMapping.containsKey(ex.getClass()
					.getName()) ? exceptionStatusMapping.get(ex.getClass()
					.getName()) : 500;
			if (ex instanceof Ks3ServiceException) {
				status = ((Ks3ServiceException) ex).getStatueCode();
			}
			if(status >= 300 && status < 400){
				status = 400;
			}
			
			//暂时方案 监测到登陆超时直接跳转登陆页(废弃)
//			if(StringUtils.isNotBlank(loginPageUrl) && ex instanceof NotLogginException){
//				return new ModelAndView("redirect:" + loginPageUrl);
//			}
			
			String expMsg = expMsgOverride.containsKey(ex.getClass().getName())?expMsgOverride.get(ex.getClass().getName()):ex.getMessage();
			RestError error = new RestError();
			error.setStatus(status);
			error.setMessage(expMsg);
//			error.setDevelopMessage(CommonUtil.getTrace(ex));
			error.setRequestId(requestId);
			errorMsg = "{\"status\":500,\"message\":\"\",\"requestId\":\""
					+ requestId + "\"}";
			try {
				errorMsg = CommonUtil.object2json(error);
			} catch (JsonProcessingException e) {
				log.warn(CommonUtil.getTrace(e));
			}
		}
		response.setHeader("Content-Type", "application/json");
		response.setStatus(status);
		try {
			response.getWriter().write(errorMsg);
		} catch (IOException e) {
			log.warn(CommonUtil.getTrace(e));
		}
		return new ModelAndView();
	}

}

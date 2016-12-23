package com.it.audit.web.page;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.it.audit.auth.AuthContextHolder;
import com.it.audit.common.CommonInfo;
import com.it.audit.enums.UserRole;
import com.it.audit.model.UserInfo;
import com.it.audit.service.UserService;
import com.it.audit.util.UserRoleConfig;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.IndexInfo;
import com.it.audit.web.dto.LoginInfo;
import com.it.audit.web.dto.UserRolePage;

@Controller
public class IndexViewController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView baseIndex(){
		return new ModelAndView("redirect:" + RequestURI.INDEX_URI);
	}
	
	@RequestMapping(value = RequestURI.LOGIN_URI, method = RequestMethod.GET)
	public ModelAndView login(String error){
		return new ModelAndView("login", "error", error);
	}
	
	@RequestMapping(value = RequestURI.LOGIN_URI, method = RequestMethod.POST)
	public ModelAndView loginForUser(HttpServletResponse response, LoginInfo loginInfo){
		String userCookie = this.userService.userLogin(loginInfo.getUsername(), loginInfo.getPassword());
		if(StringUtils.isNotBlank(userCookie)){
			response.addCookie(new Cookie(CommonInfo.USER_COOKIE_KEY, userCookie));
		} else {
			return buildErrorLoginPage("用户名或密码不正确");
		}
		return new ModelAndView("redirect:" + RequestURI.INDEX_URI);
	}
	
	@RequestMapping(value = RequestURI.LOGOUT_URI, method = RequestMethod.GET)
	public ModelAndView logoutForUser(){
		UserInfo userInfo = AuthContextHolder.get().getUserInfo();
		this.userService.userLogout(userInfo.getLoginToken(), userInfo.getUserId());
		return new ModelAndView("redirect:" + RequestURI.LOGIN_URI);
	}
	
	@RequestMapping(value = RequestURI.INDEX_URI)
	public ModelAndView index(){
		UserInfo user = AuthContextHolder.get().getUserInfo();
		List<UserRolePage> roleConfigs = UserRoleConfig.getRolePageConfigs();
		return new ModelAndView("index", "indexInfo", new IndexInfo(user, roleConfigs, null, null, null));
	}
	
	@RequestMapping(value = RequestURI.INDEX_URI_RELAY)
	public ModelAndView index(@PathVariable(value="index") Integer index, @PathVariable(value="modul") String model, @PathVariable(value="action") String action){
		UserInfo user = AuthContextHolder.get().getUserInfo();
		List<UserRolePage> roleConfigs = UserRoleConfig.getRolePageConfigs();
		return new ModelAndView("index", "indexInfo", new IndexInfo(user, roleConfigs, index, model, action));
	}
	
	@RequestMapping(value = RequestURI.INDEX_LEFT_URI)
	public ModelAndView leftMenu(@PathVariable UserRole role){
		return new ModelAndView("leftmenu", "role", role.name());
	}
	
	@RequestMapping(value = RequestURI.INDEX_CENTER_URI)
	public ModelAndView centerContent(@PathVariable UserRole role){
//		switch (role) {
//		case ADMIN:
//			return new ModelAndView("user/list", "role", role.name());
//		default:
//			break;
//		}
		return new ModelAndView();
	}
	
	public static final ModelAndView buildErrorLoginPage(String error){
		return new ModelAndView("redirect:" + RequestURI.LOGIN_URI, "error", error);
	}
}

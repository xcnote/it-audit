package com.it.audit.web.page;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.audit.domain.ItAuditUser;
import com.it.audit.enums.UserRole;
import com.it.audit.enums.UserStatus;
import com.it.audit.service.UserService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.DeleteDTO;
import com.it.audit.web.dto.ResponesBase;

@Controller
public class UserViewController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = RequestURI.USER_PAGE, method = RequestMethod.GET)
	public ModelAndView userPage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditUser> users = this.userService.queryUserPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.put("users", CommonUtil.buildPageParam(users, 7));
		return new ModelAndView("user/list", result);
	}
	
	@RequestMapping(value = RequestURI.USER_CREATE, method = RequestMethod.GET)
	public ModelAndView userCreate(){
		return this.buildUpdetaPageView(true, null, null);
	}
	
	@RequestMapping(value = RequestURI.USER_UPDATE, method = RequestMethod.GET)
	public ModelAndView userUpdate(@RequestParam Long id){
		ItAuditUser user = this.userService.queryUserById(id);
		return this.buildUpdetaPageView(false, user, null);
	}
	
	@RequestMapping(value = RequestURI.USER_DELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> userDelete(@RequestBody DeleteDTO userIds){
		this.userService.userDelete(userIds.getIds());
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "删除成功"), HttpStatus.OK);
	}
	
	@RequestMapping(value = RequestURI.USER_CREATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> userCreate(@RequestBody ItAuditUser user){
		String errormsg = this.userService.userCreate(user);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, null, errormsg), HttpStatus.OK);
	}
	
	@RequestMapping(value = RequestURI.USER_UPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> userUpdate(@RequestBody ItAuditUser user){
		String errormsg = this.userService.userUpdate(user);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, errormsg), HttpStatus.OK);
	}
	
	private ModelAndView buildUpdetaPageView(boolean create, ItAuditUser user, String errmsg){
		UserRole[] roles = UserRole.values();
		UserStatus[] allStatus = UserStatus.values();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roles", roles);
		param.put("allStatus", allStatus);
		param.put("create", create);
		param.put("user", user);
		param.put("errmsg", errmsg);
		return new ModelAndView("user/update", "userInfo", param);
	}
}

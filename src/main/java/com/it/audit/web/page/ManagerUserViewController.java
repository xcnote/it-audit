package com.it.audit.web.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.audit.enums.ObjectUserRole;
import com.it.audit.service.ManagerService;
import com.it.audit.service.UserService;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.DeleteDTO;
import com.it.audit.web.dto.ObjectUser;
import com.it.audit.web.dto.ResponesBase;

@Controller
public class ManagerUserViewController {
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private UserService userService;

	/**
	 * 项目成员列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_USER_LIST, method = RequestMethod.GET)
	public ModelAndView managerObjectUserList(@RequestParam Long objectId){
		List<ObjectUser> objectUsers = this.managerService.queryAllObjectUser(objectId);
		Map<String, Object> result = new HashMap<>();
		result.put("users", objectUsers);
		result.put("objectId", objectId);
		return new ModelAndView("manager/user/list", result);
	}
	
	/**
	 * 成员新增页面
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_USER_ADD, method = RequestMethod.GET)
	public ModelAndView managerObjectUserAdd(@RequestParam Long objectId){
		Map<String, Object> result = new HashMap<>();
		result.put("users", this.userService.queryAll());
		result.put("objectUserRoles", ObjectUserRole.values());
		result.put("objectId", objectId);
		return new ModelAndView("manager/user/add", result);
	}
	
	/**
	 * 成员新增
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_USER_ADD, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectUserAdd(@RequestBody ObjectUser user){
		String result = this.managerService.addObjectUser(user);
		result = StringUtils.isNotEmpty(result)? result: "新增成功";
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, result), HttpStatus.OK);
	}
	
	/**
	 * 成员删除
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_USER_DELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectUserDelete(@RequestBody DeleteDTO ids){
		String result = this.managerService.deleteObjectUser(ids.getIds());
		result = StringUtils.isNotEmpty(result)? result: "删除成功";
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, result), HttpStatus.OK);
	}
}

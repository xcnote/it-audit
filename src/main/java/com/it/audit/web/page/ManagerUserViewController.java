package com.it.audit.web.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.it.audit.service.ManagerService;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.ObjectUser;

@Controller
public class ManagerUserViewController {
	
	@Autowired
	private ManagerService managerService;

	/**
	 * 项目成员列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_USER_LIST, method = RequestMethod.GET)
	public ModelAndView managerObjectUserList(@RequestParam Long objectId){
		List<ObjectUser> objectUsers = this.managerService.queryAllObjectUser(objectId);
		return new ModelAndView("manager/user/list", "users", objectUsers);
	}
}

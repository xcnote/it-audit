package com.it.audit.web.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.it.audit.service.ManagerService;
import com.it.audit.web.constants.RequestURI;

@Controller
public class ManagerViewController {
	
	@Autowired
	private ManagerService managerService;

	@RequestMapping(value = RequestURI.MANAGER_BASE, method = RequestMethod.GET)
	public ModelAndView managerIndex(){
		return new ModelAndView("manager/index");
	}
	
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_CREATE, method = RequestMethod.GET)
	public ModelAndView managerObjectCreate(){
		return new ModelAndView("manager/create", this.managerService.buildCreateParam());
	}
	
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_MANAGE, method = RequestMethod.GET)
	public ModelAndView managerObjectManage(){
		return new ModelAndView("manager/manage", this.managerService.buildCreateParam());
	}
}

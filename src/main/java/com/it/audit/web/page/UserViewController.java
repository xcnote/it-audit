package com.it.audit.web.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.it.audit.domain.ItAuditUser;
import com.it.audit.service.UserService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;

@Controller
public class UserViewController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = RequestURI.USER_PAGE, method = RequestMethod.GET)
	public ModelAndView userPage(Integer page){
		Page<ItAuditUser> users = this.userService.queryUserPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")));
		return new ModelAndView("user/list", "users", CommonUtil.buildPageParam(users, 7));
	}
	
	@RequestMapping(value = RequestURI.USER_CREATE, method = RequestMethod.GET)
	public ModelAndView userCreate(){
		return new ModelAndView("user/update");
	}
}

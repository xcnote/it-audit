package com.it.audit.web.page;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.it.audit.domain.ItAuditObject;
import com.it.audit.service.ManagerService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.ObjectCreate;
import com.it.audit.web.dto.ResponesBase;

@Controller
public class ManagerViewController {
	
	@Autowired
	private ManagerService managerService;

	@RequestMapping(value = RequestURI.MANAGER_BASE, method = RequestMethod.GET)
	public ModelAndView managerIndex(){
		Map<String, List<ItAuditObject>> result = this.managerService.queryIndexData();
		return new ModelAndView("manager/index", result);
	}
	
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_CREATE, method = RequestMethod.GET)
	public ModelAndView managerObjectCreate(){
		return new ModelAndView("manager/create", this.managerService.buildCreateParam());
	}
	
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_PAGE, method = RequestMethod.GET)
	public ModelAndView userPage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditObject> objectList = this.managerService.queryObjectPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.put("objectList", CommonUtil.buildPageParam(objectList, 7));
		return new ModelAndView("manager/list", result);
	}
	
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_MANAGE, method = RequestMethod.GET)
	public ModelAndView managerObjectManage(){
		return new ModelAndView("manager/manage", this.managerService.buildCreateParam());
	}
	
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_CREATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> userCreate(@RequestBody @Valid ObjectCreate createInfo){
		boolean result = this.managerService.createObject(createInfo);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "项目创建成功", result?null:"创建失败，请检查输入内容"), HttpStatus.OK);
	}
}

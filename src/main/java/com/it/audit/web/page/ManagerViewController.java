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
import com.it.audit.service.UserService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.ObjectCreate;
import com.it.audit.web.dto.ObjectUpdate;
import com.it.audit.web.dto.ResponesBase;

/**
 * 项目经理视图
 * @author wangx
 *
 */
@Controller
public class ManagerViewController {
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private UserService userService;

	/**
	 * 主页
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_BASE, method = RequestMethod.GET)
	public ModelAndView managerIndex(){
		Map<String, List<ItAuditObject>> result = this.managerService.queryIndexData();
		return new ModelAndView("manager/index", result);
	}
	
	/**
	 * 项目创建
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_CREATE, method = RequestMethod.GET)
	public ModelAndView managerObjectCreate(){
		return new ModelAndView("manager/create", this.managerService.buildCreateParam());
	}
	
	/**
	 * 项目创建
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_CREATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectCreate(@RequestBody @Valid ObjectCreate createInfo){
		boolean result = this.managerService.createObject(createInfo);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "项目创建成功", result?null:"创建失败，请检查输入内容"), HttpStatus.OK);
	}
	
	/**
	 * 项目管理-项目列表
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_PAGE, method = RequestMethod.GET)
	public ModelAndView managerObjectPage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditObject> objectList = this.managerService.queryObjectPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.putAll(CommonUtil.buildPageParam(objectList, 7));
		result.put("userMap", this.userService.queryAllToMap());
		return new ModelAndView("manager/list", result);
	}
	
	/**
	 * 项目管理-项目测试范围
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_MANAGE, method = RequestMethod.GET)
	public ModelAndView managerObjectManage(@RequestParam Long id){
		ItAuditObject object = this.managerService.queryObjectDetail(id);
		return new ModelAndView("manager/test", "info", object);
	}
	
	/**
	 * 项目管理-工作任务分配
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TASK_ALLOT, method = RequestMethod.GET)
	public ModelAndView objectTaskAllot(@RequestParam Long id){
		ItAuditObject object = this.managerService.queryObjectDetail(id);
		return new ModelAndView("manager/task/task", "info", object);
	}
	
	/**
	 * 项目管理-工作复核
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REVIEW_INDEX, method = RequestMethod.GET)
	public ModelAndView objectTaskReview(@RequestParam Long id){
		ItAuditObject object = this.managerService.queryObjectDetail(id);
		return new ModelAndView("manager/review/index", "info", object);
	}
	
	/**
	 * 项目管理 - 项目基本信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_UPDATE, method = RequestMethod.GET)
	public ModelAndView objectUpdate(@RequestParam Long id){
		ItAuditObject object = this.managerService.queryObjectDetail(id);
		Map<String, Object> result = this.managerService.buildCreateParam();
		result.put("info", object);
		result.put("userMap", this.userService.queryAllToMap());
		return new ModelAndView("manager/update", result);
	}
	
	/**
	 * 项目管理 - 项目基本信息更新
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_UPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> objectUpdate(@RequestBody @Valid ObjectUpdate updateInfo){
		String result = this.managerService.updateObject(updateInfo);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, result == null?"项目创建成功": null, result), HttpStatus.OK);
	}
	
	/**
	 * 项目报告-项目列表
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REPORT, method = RequestMethod.GET)
	public ModelAndView managerObjectReport(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditObject> objectList = this.managerService.queryObjectPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.putAll(CommonUtil.buildPageParam(objectList, 7));
		result.put("userMap", this.userService.queryAllToMap());
		return new ModelAndView("manager/reportlist", result);
	}
}

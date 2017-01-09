package com.it.audit.web.page;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

import com.it.audit.domain.ItAuditObject;
import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.enums.ObjectTaskType;
import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.enums.ObjectUserRole;
import com.it.audit.enums.TestImperfectionType;
import com.it.audit.exception.NotFoundException;
import com.it.audit.service.AuditorService;
import com.it.audit.service.ManagerService;
import com.it.audit.service.ObjectTestACService;
import com.it.audit.service.ObjectTestDAService;
import com.it.audit.service.ObjectTestGCService;
import com.it.audit.service.UserService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.ResponesBase;
import com.it.audit.web.dto.ReviewACUpdate;
import com.it.audit.web.dto.ReviewDAUpdate;
import com.it.audit.web.dto.ReviewGCUpdate;
import com.it.audit.web.dto.TaskAllot;

/**
 * 审计师视图
 * @author wangx
 *
 */
@Controller
public class AuditorViewController {
	
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectTestGCService objectTestGCService;
	@Autowired
	private ObjectTestACService objectTestACService;
	@Autowired
	private ObjectTestDAService objectTestDAService;

	/**
	 * 主页
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_BASE, method = RequestMethod.GET)
	public ModelAndView reviewerIndex(){
		Map<String, List<ItAuditObject>> result = this.auditorService.queryIndexData();
		return new ModelAndView("auditor/index", result);
	}
	
	/**
	 * 项目列表
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_LIST, method = RequestMethod.GET)
	public ModelAndView objectPage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditObject> objectList = this.auditorService.queryObjectPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.putAll(CommonUtil.buildPageParam(objectList, 7));
		result.put("userMap", this.userService.queryAllToMap());
		return new ModelAndView("auditor/list", result);
	}
	
	/**
	 * 项目管理 - 项目基本信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_INFO, method = RequestMethod.GET)
	public ModelAndView objectUpdate(@RequestParam Long objectId){
		ItAuditObject object = this.auditorService.queryObjectDetail(objectId);
		Map<String, Object> result = this.managerService.buildCreateParam();
		result.put("info", object);
		return new ModelAndView("auditor/update", result);
	}
	
	/**
	 * 工作任务主页
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_INDEX, method = RequestMethod.GET)
	public ModelAndView objectTask(@RequestParam Long objectId){
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		return new ModelAndView("auditor/task/index", "info", object);
	}
	
	/**
	 * GC任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_GCLIST, method = RequestMethod.GET)
	public ModelAndView reviewGCTaskList(Integer page, @RequestParam Long objectId, @RequestParam(required=false) Boolean unsubmit, @RequestParam(required=false) Boolean sendback){
		List<ObjectTestStatus> status = null;
		if(unsubmit != null && unsubmit){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue, ObjectTestStatus.sendback});
		}
		if(sendback != null && sendback){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.sendback});
		}
		Page<ItAuditTestGC> gcList = this.objectTestGCService.queryTestGCList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, status);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(gcList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("auditor/task/gclist", result);
	}
	
	/**
	 * AC任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_ACLIST, method = RequestMethod.GET)
	public ModelAndView reviewACTaskList(Integer page, @RequestParam Long objectId, @RequestParam(required=false) Boolean unsubmit, @RequestParam(required=false) Boolean sendback){
		List<ObjectTestStatus> status = null;
		if(unsubmit != null && unsubmit){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue, ObjectTestStatus.sendback});
		}
		if(sendback != null && sendback){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.sendback});
		}
		Page<ItAuditTestAC> acList = this.objectTestACService.queryTestACList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, status);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(acList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("auditor/task/aclist", result);
	}
	
	/**
	 * DA任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_DALIST, method = RequestMethod.GET)
	public ModelAndView reviewtDATaskList(Integer page, @RequestParam Long objectId, @RequestParam(required=false) Boolean unsubmit, @RequestParam(required=false) Boolean sendback){
		List<ObjectTestStatus> status = null;
		if(unsubmit != null && unsubmit){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue, ObjectTestStatus.sendback});
		}
		if(sendback != null && sendback){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.sendback});
		}
		Page<ItAuditTestDA> daList = this.objectTestDAService.queryTestDAList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, status);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(daList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("auditor/task/dalist", result);
	}
	
	/**
	 * GC、AC、DA编辑
	 * @param testId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_UPDATE, method = RequestMethod.GET)
	public ModelAndView reviewTaskUpatePage(@RequestParam Long testId, @RequestParam ObjectTaskType type){
		Map<String, Object> result = new HashMap<>();
		List<TestImperfectionType> imperfectionTypes = TestImperfectionType.getAllType(type);
		result.put("imperfectionTyps", imperfectionTypes);
		switch (type) {
		case GC:
			ItAuditTestGC gc = this.objectTestGCService.queryById(testId);
			result.put("gc", gc);
			return new ModelAndView("auditor/task/gcupdate", result);
		case AC:
			ItAuditTestAC ac = this.objectTestACService.queryById(testId);
			result.put("ac", ac);
			return new ModelAndView("auditor/task/acupdate", result);
		case DA:
			ItAuditTestDA da = this.objectTestDAService.queryById(testId);
			result.put("da", da);
			return new ModelAndView("auditor/task/daupdate", result);
		default:
			CommonUtil.checkAndThrowAssignException(false, new NotFoundException("无效处理类型"));
			break;
		}
		return null;
	}
	
	/**
	 * GC 修改
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_GCUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> reviewGCUpdate(@RequestBody ReviewGCUpdate update){
		ItAuditTestGC gc = this.objectTestGCService.queryById(update.getId());
		if(gc == null){
			return new ResponseEntity<ResponesBase>(new ResponesBase(-1, null, "未查询到记录"), HttpStatus.OK);
		}
		this.objectTestGCService.save(update.updateItAuditTestGCToAudit(gc));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	/**
	 * AC 修改
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_ACUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> reviewACUpdate(@RequestBody ReviewACUpdate update){
		ItAuditTestAC ac = this.objectTestACService.queryById(update.getId());
		if(ac == null){
			return new ResponseEntity<ResponesBase>(new ResponesBase(-1, null, "未查询到记录"), HttpStatus.OK);
		}
		this.objectTestACService.save(update.updateItAuditTestACToAudit(ac));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	/**
	 * DA 修改
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_DAUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> reviewDAUpdate(@RequestBody ReviewDAUpdate update){
		ItAuditTestDA da = this.objectTestDAService.queryById(update.getId());
		if(da == null){
			return new ResponseEntity<ResponesBase>(new ResponesBase(-1, null, "未查询到记录"), HttpStatus.OK);
		}
		this.objectTestDAService.save(update.updateItAuditTestDAToAudit(da));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	/**
	 * 任务提交
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_TASK_SUBMIT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> taskSubmit(@RequestBody TaskAllot task){
		switch (task.getType()) {
		case GC:
			this.objectTestGCService.updateTaskStatus(task.getTaskIds(), ObjectTestStatus.submit);
			break;
		case AC:
			this.objectTestACService.updateTaskStatus(task.getTaskIds(), ObjectTestStatus.submit);
			break;
		case DA:
			this.objectTestDAService.updateTaskStatus(task.getTaskIds(), ObjectTestStatus.submit);
			break;
		default:
			CommonUtil.checkAndThrowAssignException(false, new NotFoundException("无效处理类型"));
			break;
		}
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	/**
	 * 项目报告列表
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_REPORT, method = RequestMethod.GET)
	public ModelAndView objectReportPage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditObject> objectList = this.auditorService.queryObjectPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.putAll(CommonUtil.buildPageParam(objectList, 7));
		result.put("userMap", this.userService.queryAllToMap());
		return new ModelAndView("auditor/reportlist", result);
	}
	
	/**
	 * 项目统计分析
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_REPORT_INFO, method = RequestMethod.GET)
	public ModelAndView reportInfo(@RequestParam Long objectId){
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		return new ModelAndView("auditor/report/info", "info", object);
	}
	
	/**
	 * 项目报告列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.AUDITOR_OBJECT_REPORT_DOWN, method = RequestMethod.GET)
	public ModelAndView reportCreatePage(@RequestParam Long objectId){
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		return new ModelAndView("auditor/report/down", "info", object);
	}
	
	private Map<String, Object> getTaskInfo(Long objectId){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userMap", this.userService.queryAllToMap());
		result.put("objectUsers", this.managerService.queryObjectUserByRole(objectId, ObjectUserRole.AUDITOR));
		return result;
	}
}

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

import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.enums.ObjectTaskType;
import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.enums.ObjectUserRole;
import com.it.audit.enums.TestImperfectionType;
import com.it.audit.exception.NotFoundException;
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

@Controller
public class ManagerReviewViewController {
	
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
	 * GC任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REVIEW_GCLIST, method = RequestMethod.GET)
	public ModelAndView reviewGCTaskList(Integer page, @RequestParam Long objectId, @RequestParam(required=false) Boolean unpass, @RequestParam(required=false) Boolean unsubmit){
		List<ObjectTestStatus> status = null;
		if(unpass != null && unpass){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue, ObjectTestStatus.sendback, ObjectTestStatus.submit});
		}
		if(unsubmit != null && unsubmit){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue});
		}
		Page<ItAuditTestGC> gcList = this.objectTestGCService.queryTestGCList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, status);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(gcList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("manager/review/gclist", result);
	}
	
	/**
	 * AC任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REVIEW_ACLIST, method = RequestMethod.GET)
	public ModelAndView reviewACTaskList(Integer page, @RequestParam Long objectId, @RequestParam(required=false) Boolean unpass, @RequestParam(required=false) Boolean unsubmit){
		List<ObjectTestStatus> status = null;
		if(unpass != null && unpass){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue, ObjectTestStatus.sendback, ObjectTestStatus.submit});
		}
		if(unsubmit != null && unsubmit){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue});
		}
		Page<ItAuditTestAC> acList = this.objectTestACService.queryTestACList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, status);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(acList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("manager/review/aclist", result);
	}
	
	/**
	 * DA任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REVIEW_DALIST, method = RequestMethod.GET)
	public ModelAndView reviewtDATaskList(Integer page, @RequestParam Long objectId, @RequestParam(required=false) Boolean unpass, @RequestParam(required=false) Boolean unsubmit){
		List<ObjectTestStatus> status = null;
		if(unpass != null && unpass){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue, ObjectTestStatus.sendback, ObjectTestStatus.submit});
		}
		if(unsubmit != null && unsubmit){
			status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue});
		}
		Page<ItAuditTestDA> daList = this.objectTestDAService.queryTestDAList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, status);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(daList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("manager/review/dalist", result);
	}
	
	/**
	 * GC、AC、DA编辑
	 * @param testId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REVIEW_UPDATE, method = RequestMethod.GET)
	public ModelAndView reviewTaskUpatePage(@RequestParam Long testId, @RequestParam ObjectTaskType type){
		Map<String, Object> result = new HashMap<>();
		List<TestImperfectionType> imperfectionTypes = TestImperfectionType.getAllType(type);
		result.put("imperfectionTyps", imperfectionTypes);
		switch (type) {
		case GC:
			ItAuditTestGC gc = this.objectTestGCService.queryById(testId);
			result.put("gc", gc);
			return new ModelAndView("manager/review/gcupdate", result);
		case AC:
			ItAuditTestAC ac = this.objectTestACService.queryById(testId);
			result.put("ac", ac);
			return new ModelAndView("manager/review/acupdate", result);
		case DA:
			ItAuditTestDA da = this.objectTestDAService.queryById(testId);
			result.put("da", da);
			return new ModelAndView("manager/review/daupdate", result);
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
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REVIEW_GCUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> reviewGCUpdate(@RequestBody ReviewGCUpdate update){
		ItAuditTestGC gc = this.objectTestGCService.queryById(update.getId());
		if(gc == null){
			return new ResponseEntity<ResponesBase>(new ResponesBase(-1, null, "未查询到记录"), HttpStatus.OK);
		}
		ObjectTestStatus status = ObjectTestStatus.updateObjectTestStatus(update.getManagerOpinion());
		gc.setStatus(status == null? gc.getStatus(): status);
		this.objectTestGCService.save(update.updateItAuditTestGC(gc));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	/**
	 * AC 修改
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REVIEW_ACUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> reviewACUpdate(@RequestBody ReviewACUpdate update){
		ItAuditTestAC ac = this.objectTestACService.queryById(update.getId());
		if(ac == null){
			return new ResponseEntity<ResponesBase>(new ResponesBase(-1, null, "未查询到记录"), HttpStatus.OK);
		}
		ObjectTestStatus status = ObjectTestStatus.updateObjectTestStatus(update.getManagerOpinion());
		ac.setStatus(status == null? ac.getStatus(): status);
		this.objectTestACService.save(update.updateItAuditTestAC(ac));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	/**
	 * DA 修改
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REVIEW_DAUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> reviewDAUpdate(@RequestBody ReviewDAUpdate update){
		ItAuditTestDA da = this.objectTestDAService.queryById(update.getId());
		if(da == null){
			return new ResponseEntity<ResponesBase>(new ResponesBase(-1, null, "未查询到记录"), HttpStatus.OK);
		}
		ObjectTestStatus status = ObjectTestStatus.updateObjectTestStatus(update.getManagerOpinion());
		da.setStatus(status == null? da.getStatus(): status);
		this.objectTestDAService.save(update.updateItAuditTestDA(da));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	private Map<String, Object> getTaskInfo(Long objectId){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userMap", this.userService.queryAllToMap());
		result.put("objectUsers", this.managerService.queryObjectUserByRole(objectId, ObjectUserRole.AUDITOR));
		return result;
	}
}

package com.it.audit.web.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.it.audit.enums.ObjectUserRole;
import com.it.audit.exception.NotFoundException;
import com.it.audit.service.ManagerService;
import com.it.audit.service.ObjectTestACService;
import com.it.audit.service.ObjectTestDAService;
import com.it.audit.service.ObjectTestGCService;
import com.it.audit.service.UserService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.ResponesBase;
import com.it.audit.web.dto.TaskAllot;

@Controller
public class ManagerTaskViewController {
	
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
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TASK_GCLIST, method = RequestMethod.GET)
	public ModelAndView objectGCTaskList(Integer page, @RequestParam Long objectId, @RequestParam Boolean unallocated){
		String queryKey = null;
		String queryValue = null;
		if(unallocated){
			queryKey = "testUserId";
			queryValue = "-1";
		}
		Page<ItAuditTestGC> gcList = this.objectTestGCService.queryTestGCList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(gcList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("manager/task/gclist", result);
	}
	
	/**
	 * AC任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TASK_ACLIST, method = RequestMethod.GET)
	public ModelAndView objectACTaskList(Integer page, @RequestParam Long objectId, @RequestParam Boolean unallocated){
		String queryKey = null;
		String queryValue = null;
		if(unallocated){
			queryKey = "testUserId";
			queryValue = "-1";
		}
		Page<ItAuditTestAC> acList = this.objectTestACService.queryTestACList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(acList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("manager/task/aclist", result);
	}
	
	/**
	 * DA任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TASK_DALIST, method = RequestMethod.GET)
	public ModelAndView objectDATaskList(Integer page, @RequestParam Long objectId, @RequestParam Boolean unallocated){
		String queryKey = null;
		String queryValue = null;
		if(unallocated){
			queryKey = "testUserId";
			queryValue = "-1";
		}
		Page<ItAuditTestDA> daList = this.objectTestDAService.queryTestDAList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId, queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(daList, 7));
		result.put("objectId", objectId);
		result.putAll(this.getTaskInfo(objectId));
		return new ModelAndView("manager/task/dalist", result);
	}
	
	/**
	 * 任务分配
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TASK_ALLOT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> userCreate(@RequestBody @Valid TaskAllot taskAllot){
		ObjectTaskType type = taskAllot.getType();
		Long userId = taskAllot.getUserId();
		List<Long> taskIds = taskAllot.getTaskIds();
		
		String result = null;
		switch (type) {
		case GC:
			result = this.objectTestGCService.allotGCTask(userId, taskIds);
			break;
		case AC:
			result = this.objectTestACService.allotACTask(userId, taskIds);
			break;
		case DA:
			result = this.objectTestDAService.allotDATask(userId, taskIds);
			break;
		default:
			CommonUtil.checkAndThrowAssignException(false, new NotFoundException("无效处理类型"));
			break;
		}
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "任务分配成功", StringUtils.isEmpty(result)?null:result), HttpStatus.OK);
	}
	
	private Map<String, Object> getTaskInfo(Long objectId){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userMap", this.userService.queryAllToMap());
		result.put("objectUsers", this.managerService.queryObjectUserByRole(objectId, ObjectUserRole.AUDITOR));
		return result;
	}
}

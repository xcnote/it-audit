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

import com.it.audit.domain.ItAuditFile;
import com.it.audit.domain.ItAuditObject;
import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.enums.FileType;
import com.it.audit.enums.ObjectTaskType;
import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.enums.TestImperfectionType;
import com.it.audit.exception.NotFoundException;
import com.it.audit.service.FileService;
import com.it.audit.service.ManagerService;
import com.it.audit.service.ObjectService;
import com.it.audit.service.ObjectTestACService;
import com.it.audit.service.ObjectTestDAService;
import com.it.audit.service.ObjectTestGCService;
import com.it.audit.service.ReviewerService;
import com.it.audit.service.UserService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.ResponesBase;
import com.it.audit.web.dto.ReviewFeedback;

/**
 * 质量复核视图
 * @author wangx
 *
 */
@Controller
public class ReviewerViewController {
	
	@Autowired
	private ReviewerService reviewerService;
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
	@Autowired
	private ObjectService objectService;
	@Autowired
	private FileService fileService;

	/**
	 * 主页
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_BASE, method = RequestMethod.GET)
	public ModelAndView reviewerIndex(){
		Map<String, List<ItAuditObject>> result = this.reviewerService.queryIndexData();
		return new ModelAndView("reviewer/index", result);
	}
	
	/**
	 * 项目列表
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_LIST, method = RequestMethod.GET)
	public ModelAndView reviewerObjectPage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditObject> objectList = this.reviewerService.queryObjectPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.putAll(CommonUtil.buildPageParam(objectList, 7));
		result.put("userMap", this.userService.queryAllToMap());
		return new ModelAndView("reviewer/list", result);
	}
	
	/**
	 * 项目管理-工作复核
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_TEST_INDEX, method = RequestMethod.GET)
	public ModelAndView objectTestIndex(@RequestParam Long objectId){
		ItAuditObject object = this.reviewerService.queryObjectDetail(objectId);
		return new ModelAndView("reviewer/test/index", "info", object);
	}
	
	/**
	 * 项目管理-工作复核 - 底稿 GC任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_TEST_GCLIST, method = RequestMethod.GET)
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
		return new ModelAndView("reviewer/test/gclist", result);
	}
	
	/**
	 * 项目管理-工作复核 - 底稿 AC任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_TEST_ACLIST, method = RequestMethod.GET)
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
		return new ModelAndView("reviewer/test/aclist", result);
	}
	
	/**
	 * 项目管理-工作复核 - 底稿 DA任务列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_TEST_DALIST, method = RequestMethod.GET)
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
		return new ModelAndView("reviewer/test/dalist", result);
	}
	
	/**
	 * 项目管理-工作复核 - 底稿 GC、AC、DA详情
	 * @param testId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_TEST_DEATIL, method = RequestMethod.GET)
	public ModelAndView reviewTaskUpatePage(@RequestParam Long testId, @RequestParam ObjectTaskType type){
		Map<String, Object> result = new HashMap<>();
		List<TestImperfectionType> imperfectionTypes = TestImperfectionType.getAllType(type);
		result.put("imperfectionTyps", imperfectionTypes);
		String address = "";
		FileType fileType = null;
		switch (type) {
		case GC:
			ItAuditTestGC gc = this.objectTestGCService.queryById(testId);
			result.put("gc", gc);
			fileType = FileType.gctest;
			address = "reviewer/test/gcupdate";
			break;
		case AC:
			ItAuditTestAC ac = this.objectTestACService.queryById(testId);
			result.put("ac", ac);
			fileType = FileType.actest;
			address = "reviewer/test/acupdate";
			break;
		case DA:
			ItAuditTestDA da = this.objectTestDAService.queryById(testId);
			result.put("da", da);
			fileType = FileType.datest;
			address = "reviewer/test/daupdate";
			break;
		default:
			CommonUtil.checkAndThrowAssignException(false, new NotFoundException("无效处理类型"));
			break;
		}
		List<ItAuditFile> files = this.fileService.findFileByTypeAndId(testId, fileType);
		result.put("files", files);
		return new ModelAndView(address, result);
	}
	
	/**
	 * 项目管理-报告查询
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_REPORT_INDEX, method = RequestMethod.GET)
	public ModelAndView objectReviewIndex(@RequestParam Long objectId){
		ItAuditObject object = this.reviewerService.queryObjectDetail(objectId);
		return new ModelAndView("reviewer/report/index", "info", object);
	}
	
	/**
	 * 项目统计分析
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_REPORT_INFO, method = RequestMethod.GET)
	public ModelAndView reportInfo(@RequestParam Long objectId){
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		return new ModelAndView("reviewer/report/info", "info", object);
	}
	
	/**
	 * 项目报告列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_REPORT_DOWN, method = RequestMethod.GET)
	public ModelAndView reportCreatePage(@RequestParam Long objectId){
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		Map<String, Object> result = this.objectService.getObjectFile(object.getId());
		result.put("info", object);
		return new ModelAndView("reviewer/report/down", result);
	}
	
	/**
	 * 项目管理 - 项目基本信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_INFO, method = RequestMethod.GET)
	public ModelAndView objectUpdate(@RequestParam Long objectId){
		ItAuditObject object = this.reviewerService.queryObjectDetail(objectId);
		Map<String, Object> result = this.managerService.buildCreateParam();
		result.put("info", object);
		return new ModelAndView("reviewer/update", result);
	}
	
	/**
	 * 项目管理-报告查询
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_FEEDBACK, method = RequestMethod.GET)
	public ModelAndView objectReviewFeedback(@RequestParam Long objectId){
		ItAuditObject object = this.reviewerService.queryObjectDetail(objectId);
		return new ModelAndView("reviewer/feedback", "info", object);
	}
	/**
	 * 项目管理 - 复核反馈
	 * @return
	 */
	@RequestMapping(value = RequestURI.REVIEWER_OBJECT_FEEDBACK, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> objectReviewFeedback(@RequestBody ReviewFeedback feedback){
		ItAuditObject object = this.managerService.queryObjectDetail(feedback.getObjectId());
		object.setStatus(feedback.getStatus());
		this.managerService.update(object);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功", null), HttpStatus.OK);
	}
}

package com.it.audit.web.page;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.it.audit.enums.ObjectStatus;
import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.service.ManagerService;
import com.it.audit.service.ObjectTestACService;
import com.it.audit.service.ObjectTestDAService;
import com.it.audit.service.ObjectTestGCService;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.DeleteDTO;
import com.it.audit.web.dto.ResponesBase;

@Controller
public class ManagerReportViewController {
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ObjectTestGCService objectTestGCService;
	@Autowired
	private ObjectTestACService objectTestACService;
	@Autowired
	private ObjectTestDAService objectTestDAService;

	/**
	 * 项目统计分析
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REPORT_INFO, method = RequestMethod.GET)
	public ModelAndView reportInfo(@RequestParam Long objectId){
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		return new ModelAndView("manager/report/info", "info", object);
	}
	
	/**
	 * 项目报告列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_REPORT_CREATE, method = RequestMethod.GET)
	public ModelAndView reportCreatePage(@RequestParam Long objectId){
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		return new ModelAndView("manager/report/create", "info", object);
	}
	
	/**
	 * 提交复核
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_PUSHREVIEW, method = RequestMethod.GET)
	public ModelAndView pushreviewPage(@RequestParam Long objectId){
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		return new ModelAndView("manager/report/pushreview", "info", object);
	}

	/**
	 * 提交复核
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_PUSHREVIEW, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> pushreview(@RequestBody DeleteDTO dto){
		ItAuditObject object = this.managerService.queryObjectDetail(dto.getObjectId());
		if(object == null){
			return new ResponseEntity<ResponesBase>(new ResponesBase(-1, null, "未查询到记录"), HttpStatus.OK);
		}
		List<ObjectTestStatus> status = Arrays.asList(new ObjectTestStatus[]{ObjectTestStatus.exectue, ObjectTestStatus.sendback, ObjectTestStatus.submit});
		Page<ItAuditTestGC> gcList = this.objectTestGCService.queryTestGCList(new PageRequest(0, 10, new Sort(Direction.DESC, "createTime")), dto.getObjectId(), status);
		Page<ItAuditTestAC> acList = this.objectTestACService.queryTestACList(new PageRequest(0, 10, new Sort(Direction.DESC, "createTime")), dto.getObjectId(), status);
		Page<ItAuditTestDA> daList = this.objectTestDAService.queryTestDAList(new PageRequest(0, 10, new Sort(Direction.DESC, "createTime")), dto.getObjectId(), status);
		if(CollectionUtils.isNotEmpty(gcList.getContent()) || CollectionUtils.isNotEmpty(acList.getContent()) || CollectionUtils.isNotEmpty(daList.getContent())){
			return new ResponseEntity<ResponesBase>(new ResponesBase(-1, null, "存在未通过测试的内容，无法提交"), HttpStatus.OK);
		}
		object.setStatus(ObjectStatus.review);
		this.managerService.update(object);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
}

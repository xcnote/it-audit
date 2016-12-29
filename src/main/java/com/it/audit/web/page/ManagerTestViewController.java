package com.it.audit.web.page;

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

import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.domain.ItAuditTestGCTemplateGroup;
import com.it.audit.service.AuditTestTemplateService;
import com.it.audit.service.ObjectTestGCService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.DeleteDTO;
import com.it.audit.web.dto.ResponesBase;

@Controller
public class ManagerTestViewController {
	
	@Autowired
	private ObjectTestGCService objectTestGCService;
	@Autowired
	private AuditTestTemplateService testTemplateService;
	
	/**
	 * 测试范围
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_RANGE, method = RequestMethod.GET)
	public ModelAndView managerObjectTestRange(@RequestParam Long objectId){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("gc", this.objectTestGCService.queryTestGCTotal(objectId));
		result.put("ac", 1);
		result.put("da", 1);
		return new ModelAndView("manager/test/range", result);
	}
	
	/**
	 * gc列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_GCLIST, method = RequestMethod.GET)
	public ModelAndView managerObjectGCList(Integer page, @RequestParam Long objectId){
		Page<ItAuditTestGC> gcList = this.objectTestGCService.queryTestGCList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId);
		List<ItAuditTestGCTemplateGroup> templateGroups = this.testTemplateService.queryAllTemplateGroupName();
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.put("gcList", CommonUtil.buildPageParam(gcList, 7));
		result.put("objectId", objectId);
		result.put("templateGroups", templateGroups);
		return new ModelAndView("manager/test/gclist", result);
	}
	
	/**
	 * gc 导入
	 * @param templateId
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_GCIMPORT, method = RequestMethod.GET)
	public ModelAndView managerObjectGCImport(@RequestParam Long templateId, @RequestParam Long objectId){
		this.objectTestGCService.testGCImport(templateId, objectId);
		return this.managerObjectGCList(0, objectId);
	}
	
	/**
	 * gc 删除
	 * @param gcIds
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_GCDELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectGCDelete(@RequestBody DeleteDTO gcIds){
		this.objectTestGCService.deleteGCs(gcIds.getIds());
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "删除成功"), HttpStatus.OK);
	}
	
	/**
	 * 新增或修改
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_GCUPDATE, method = RequestMethod.GET)
	public ModelAndView managerObjectGCUpdate(@RequestParam(required=false) Long id, @RequestParam(required=false) Long objectId){
		ItAuditTestGC gc = new ItAuditTestGC();
		gc.setObjectId(objectId);
		if(id != null){
			gc = this.objectTestGCService.queryById(id);
		}
		return new ModelAndView("manager/test/gcupdate", "gc", gc);
	}
	
	/**
	 * gc 添加
	 * @param gcIds
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_GCUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectGCUpdate(@RequestBody ItAuditTestGC gc){
		this.objectTestGCService.save(gc);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
}

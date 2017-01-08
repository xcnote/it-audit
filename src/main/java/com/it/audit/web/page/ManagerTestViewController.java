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

import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.domain.ItAuditTestGCTemplateGroup;
import com.it.audit.service.AuditTestTemplateService;
import com.it.audit.service.ObjectTestACService;
import com.it.audit.service.ObjectTestDAService;
import com.it.audit.service.ObjectTestGCService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.DeleteDTO;
import com.it.audit.web.dto.ResponesBase;
import com.it.audit.web.dto.TestACUpdate;
import com.it.audit.web.dto.TestDAUpdate;
import com.it.audit.web.dto.TestGCUpdate;

@Controller
public class ManagerTestViewController {
	
	@Autowired
	private AuditTestTemplateService testTemplateService;
	@Autowired
	private ObjectTestGCService objectTestGCService;
	@Autowired
	private ObjectTestACService objectTestACService;
	@Autowired
	private ObjectTestDAService objectTestDAService;
	
	/**
	 * 测试范围
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_RANGE, method = RequestMethod.GET)
	public ModelAndView managerObjectTestRange(@RequestParam Long objectId){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("gc", this.objectTestGCService.queryTestGCTotal(objectId));
		result.put("ac", this.objectTestACService.queryTestACTotal(objectId));
		result.put("da", this.objectTestDAService.queryTestDATotal(objectId));
		return new ModelAndView("manager/test/range", result);
	}
	
	//-----GC TEST
	
	/**
	 * GC 列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_GCLIST, method = RequestMethod.GET)
	public ModelAndView managerObjectGCList(Integer page, @RequestParam Long objectId){
		Page<ItAuditTestGC> gcList = this.objectTestGCService.queryTestGCList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId);
		List<ItAuditTestGCTemplateGroup> templateGroups = this.testTemplateService.queryAllTemplateGroupName();
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(gcList, 7));
		result.put("objectId", objectId);
		result.put("templateGroups", templateGroups);
		return new ModelAndView("manager/test/gclist", result);
	}
	
	/**
	 * GC 导入
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
	 * GC 删除
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
	 * GC 新增或修改
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
	 * GC 添加 TODO 更新时会将多余内容覆盖
	 * @param gcIds
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_GCUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectGCUpdate(@RequestBody TestGCUpdate update){
		ItAuditTestGC gc = new ItAuditTestGC();
		if(update.getId() != null) {
			gc = this.objectTestGCService.queryById(update.getId());
		}
		this.objectTestGCService.save(update.updateItAuditTestGC(gc));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	//-----AC TEST
	
	/**
	 * AC 列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_ACLIST, method = RequestMethod.GET)
	public ModelAndView managerObjectACList(Integer page, @RequestParam Long objectId){
		Page<ItAuditTestAC> acList = this.objectTestACService.queryTestACList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(acList, 7));
		result.put("objectId", objectId);
		return new ModelAndView("manager/test/aclist", result);
	}
	
	/**
	 * AC 删除
	 * @param gcIds
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_ACDELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectACDelete(@RequestBody DeleteDTO acIds){
		this.objectTestACService.deleteACs(acIds.getIds());
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "删除成功"), HttpStatus.OK);
	}
	
	/**
	 * AC 新增或修改
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_ACUPDATE, method = RequestMethod.GET)
	public ModelAndView managerObjectACUpdate(@RequestParam(required=false) Long id, @RequestParam(required=false) Long objectId){
		ItAuditTestAC ac = new ItAuditTestAC();
		ac.setObjectId(objectId);
		if(id != null){
			ac = this.objectTestACService.queryById(id);
		}
		return new ModelAndView("manager/test/acupdate", "ac", ac);
	}
	
	/**
	 * AC 添加
	 * @param gcIds
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_ACUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectACUpdate(@RequestBody TestACUpdate update){
		ItAuditTestAC ac = new ItAuditTestAC();
		if(ac.getId() != null){
			ac = this.objectTestACService.queryById(ac.getId());
		}
		this.objectTestACService.save(update.updateItAuditTestAC(ac));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	//-----DA TEST
	
	/**
	 * DA 列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_DALIST, method = RequestMethod.GET)
	public ModelAndView managerObjectDAList(Integer page, @RequestParam Long objectId){
		Page<ItAuditTestDA> daList = this.objectTestDAService.queryTestDAList(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), objectId);
		Map<String, Object> result = CommonUtil.buildQueryResult(null, null);
		result.putAll(CommonUtil.buildPageParam(daList, 7));
		result.put("objectId", objectId);
		return new ModelAndView("manager/test/dalist", result);
	}
	
	/**
	 * DA 删除
	 * @param gcIds
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_DADELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectDADelete(@RequestBody DeleteDTO daIds){
		this.objectTestDAService.deleteDAs(daIds.getIds());
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "删除成功"), HttpStatus.OK);
	}
	
	/**
	 * DA 新增或修改
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_DAUPDATE, method = RequestMethod.GET)
	public ModelAndView managerObjectDAUpdate(@RequestParam(required=false) Long id, @RequestParam(required=false) Long objectId){
		ItAuditTestDA da = new ItAuditTestDA();
		da.setObjectId(objectId);
		if(id != null){
			da = this.objectTestDAService.queryById(id);
		}
		return new ModelAndView("manager/test/daupdate", "da", da);
	}
	
	/**
	 * AC 添加
	 * @param gcIds
	 * @return
	 */
	@RequestMapping(value = RequestURI.MANAGER_OBJECT_TEST_DAUPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> managerObjectDAUpdate(@RequestBody TestDAUpdate update){
		ItAuditTestDA da = new ItAuditTestDA();
		if(update.getId() != null){
			da = this.objectTestDAService.queryById(update.getId());
		}
		this.objectTestDAService.save(update.updateItAuditTestDA(da));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
}

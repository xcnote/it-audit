package com.it.audit.web.page;

import java.util.HashMap;
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

import com.it.audit.domain.ItAuditTestGCTemplate;
import com.it.audit.domain.ItAuditTestGCTemplateGroup;
import com.it.audit.persistence.service.ItAuditTestGCTemplateGroupPersistenceService;
import com.it.audit.persistence.service.ItAuditTestGCTemplatePersistenceService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.DeleteDTO;
import com.it.audit.web.dto.ResponesBase;

/**
 * 模板视图
 * @author wangx
 *
 */
@Controller
public class TemplateViewController {
	
	@Autowired
	private ItAuditTestGCTemplateGroupPersistenceService gcTemplateGroupService;
	@Autowired
	private ItAuditTestGCTemplatePersistenceService gcTemplateService;
	
	/**
	 * 模板组列表
	 * @return
	 */
	@RequestMapping(value = RequestURI.TEMPLATE_GROUP_PAGE, method = RequestMethod.GET)
	public ModelAndView templateGroupPage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditTestGCTemplateGroup> groupList = this.gcTemplateGroupService.findByParam(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.putAll(CommonUtil.buildPageParam(groupList, 7));
		return new ModelAndView("template/grouplist", result);
	}
	
	/**
	 * 模板组新增
	 * @return
	 */
	@RequestMapping(value = RequestURI.TEMPLATE_GROUP_UPDATE, method = RequestMethod.GET)
	public ModelAndView templateGroupUpdatePage(@RequestParam(required=false) Long id){
		if(id != null){
			ItAuditTestGCTemplateGroup group = this.gcTemplateGroupService.findById(id);
			return new ModelAndView("template/groupupdate", "group", group);
		}
		return new ModelAndView("template/groupupdate");
	}
	
	/**
	 * 模板组更新
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.TEMPLATE_GROUP_UPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> templateGroupUpdate(@RequestBody ItAuditTestGCTemplateGroup group){
		this.gcTemplateGroupService.save(group);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	/**
	 * 模板组删除
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.TEMPLATE_GROUP_DELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> templateGroupDelete(@RequestBody DeleteDTO delete){
		this.gcTemplateGroupService.delete(delete.getIds().toArray(new Long[]{}));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "删除成功"), HttpStatus.OK);
	}

	/**
	 * 模板详情列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.TEMPLATE_PAGE, method = RequestMethod.GET)
	public ModelAndView templatePage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue, @RequestParam Long groupId){
		ItAuditTestGCTemplateGroup group = this.gcTemplateGroupService.findById(groupId);
		Page<ItAuditTestGCTemplate> templateList = this.gcTemplateService.findByParam(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue, groupId);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.putAll(CommonUtil.buildPageParam(templateList, 7));
		result.put("templateGroupId", groupId);
		result.put("templateGroupName", group.getName());
		return new ModelAndView("template/list", result);
	}
	
	/**
	 * 模板新增
	 * @return
	 */
	@RequestMapping(value = RequestURI.TEMPLATE_UPDATE, method = RequestMethod.GET)
	public ModelAndView templateUpdatePage(@RequestParam Long groupId, @RequestParam(required=false) Long id){
		ItAuditTestGCTemplate template = null;
		if(id != null){
			template = this.gcTemplateService.findById(id);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("templateGroupId", groupId);
		result.put("template", template);
		return new ModelAndView("template/update", result);
	}
	
	/**
	 * 模板更新
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.TEMPLATE_UPDATE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> templateUpdate(@RequestBody ItAuditTestGCTemplate template){
		this.gcTemplateService.save(template);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "提交成功"), HttpStatus.OK);
	}
	
	/**
	 * 模板删除
	 * @param id
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.TEMPLATE_DELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponesBase> templateDelete(@RequestBody DeleteDTO delete){
		this.gcTemplateService.delete(delete.getIds().toArray(new Long[]{}));
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "删除成功"), HttpStatus.OK);
	}
}

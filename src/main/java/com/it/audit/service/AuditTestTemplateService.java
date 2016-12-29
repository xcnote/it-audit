package com.it.audit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditTestGCTemplate;
import com.it.audit.domain.ItAuditTestGCTemplateGroup;
import com.it.audit.persistence.service.ItAuditTestGCTemplateGroupPersistenceService;
import com.it.audit.persistence.service.ItAuditTestGCTemplatePersistenceService;

@Service
public class AuditTestTemplateService {

	@Autowired
	private ItAuditTestGCTemplateGroupPersistenceService groupPersistenceService;
	@Autowired
	private ItAuditTestGCTemplatePersistenceService templatePersistenceService;
	
	public List<ItAuditTestGCTemplateGroup> queryAllTemplateGroupName(){
		return this.groupPersistenceService.findAll();
	}
	
	public List<ItAuditTestGCTemplate> queryAllTemplate(Long groupId){
		return this.templatePersistenceService.findByGroupId(groupId);
	}
}

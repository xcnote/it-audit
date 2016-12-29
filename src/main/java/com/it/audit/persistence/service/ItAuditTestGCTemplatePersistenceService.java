package com.it.audit.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditTestGCTemplate;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditTestGCTemplateRepository;

@Service
public class ItAuditTestGCTemplatePersistenceService extends BasePersistenceService<ItAuditTestGCTemplate, Long> {
	
	@Autowired
	private ItAuditTestGCTemplateRepository itAuditTestGCTemplateRepository;

	@Override
	protected BasePersistenceDao<ItAuditTestGCTemplate, Long> getPersistenceDao() {
		return this.itAuditTestGCTemplateRepository;
	}
	
	public List<ItAuditTestGCTemplate> findByGroupId(Long groupId){
		return this.itAuditTestGCTemplateRepository.findByGroupId(groupId);
	}
}

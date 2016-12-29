package com.it.audit.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditTestGCTemplateGroup;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditTestGCTemplateGroupRepository;

@Service
public class ItAuditTestGCTemplateGroupPersistenceService extends BasePersistenceService<ItAuditTestGCTemplateGroup, Long> {
	
	@Autowired
	private ItAuditTestGCTemplateGroupRepository itAuditTestGCTemplateGroupRepository;

	@Override
	protected BasePersistenceDao<ItAuditTestGCTemplateGroup, Long> getPersistenceDao() {
		return this.itAuditTestGCTemplateGroupRepository;
	}
}

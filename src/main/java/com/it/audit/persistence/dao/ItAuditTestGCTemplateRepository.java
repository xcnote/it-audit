package com.it.audit.persistence.dao;

import java.util.List;

import com.it.audit.domain.ItAuditTestGCTemplate;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditTestGCTemplateRepository extends BasePersistenceDao<ItAuditTestGCTemplate, Long> {

	List<ItAuditTestGCTemplate> findByGroupId(Long groupId);
}

package com.it.audit.persistence.dao;

import org.springframework.data.jpa.repository.Query;

import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditTestDARepository extends BasePersistenceDao<ItAuditTestDA, Long> {
	
	@Query("select count(a.id) from ItAuditTestDA a where a.objectId=?1")
	Integer findCountByObjectId(Long objectId);
}

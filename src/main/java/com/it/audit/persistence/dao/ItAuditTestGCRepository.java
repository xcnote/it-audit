package com.it.audit.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditTestGCRepository extends BasePersistenceDao<ItAuditTestGC, Long> {
	
	@Query("select count(a.id) from ItAuditTestGC a where a.objectId=?1")
	Integer findCountByObjectId(Long objectId);

	List<ItAuditTestGC> findByObjectIdAndTestUserId(Long objectId, Long testUserId);
}

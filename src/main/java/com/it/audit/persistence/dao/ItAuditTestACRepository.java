package com.it.audit.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditTestACRepository extends BasePersistenceDao<ItAuditTestAC, Long> {
	
	@Query("select count(a.id) from ItAuditTestAC a where a.objectId=?1")
	Integer findCountByObjectId(Long objectId);

	List<ItAuditTestAC> findByObjectIdAndTestUserId(Long objectId, Long userId);
}

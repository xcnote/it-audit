package com.it.audit.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditTestDARepository extends BasePersistenceDao<ItAuditTestDA, Long> {
	
	@Query("select count(a.id) from ItAuditTestDA a where a.objectId=?1")
	Integer findCountByObjectId(Long objectId);

	List<ItAuditTestDA> findByObjectIdAndTestUserId(Long objectId, Long userId);
	
	@Modifying
	@Transactional
	@Query("update ItAuditTestDA a set a.testUserId = ?1 where a.id in (?2)")
	void updateTestUserId(Long userId, List<Long> ids);
}

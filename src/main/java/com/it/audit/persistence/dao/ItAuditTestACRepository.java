package com.it.audit.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.enums.TestImperfectionType;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditTestACRepository extends BasePersistenceDao<ItAuditTestAC, Long> {
	
	@Query("select count(a.id) from ItAuditTestAC a where a.objectId=?1")
	Integer findCountByObjectId(Long objectId);

	List<ItAuditTestAC> findByObjectIdAndTestUserId(Long objectId, Long userId);
	
	@Modifying
	@Transactional
	@Query("update ItAuditTestAC a set a.testUserId = ?1 where a.id in (?2)")
	void updateTestUserId(Long userId, List<Long> ids);
	
	@Modifying
	@Transactional
	@Query("update ItAuditTestAC a set a.status = ?2 where a.id in (?1)")
	void updateTestStatus(List<Long> ids, ObjectTestStatus status);

	List<ItAuditTestAC> findByObjectId(Long objectId);

	List<ItAuditTestAC> findByObjectIdAndImperfectionIn(Long objectId, List<TestImperfectionType> imperfections);
}

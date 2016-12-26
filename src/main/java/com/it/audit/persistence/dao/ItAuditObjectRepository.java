package com.it.audit.persistence.dao;

import java.util.List;

import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditObject;
import com.it.audit.enums.ObjectStatus;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditObjectRepository extends BasePersistenceDao<ItAuditObject, Long> {

	List<ItAuditObject> findByManagerUserIdAndStatusAndCreateTimeBetweenOrderByCreateTimeDesc(Long managerUserId,
			ObjectStatus status, DateTime startTime, DateTime endTime);
}

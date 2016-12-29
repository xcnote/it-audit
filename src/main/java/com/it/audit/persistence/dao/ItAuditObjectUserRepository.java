package com.it.audit.persistence.dao;

import java.util.List;

import com.it.audit.domain.ItAuditObjectUser;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditObjectUserRepository extends BasePersistenceDao<ItAuditObjectUser, Long> {

	List<ItAuditObjectUser> findByObjectId(Long objectId);
}

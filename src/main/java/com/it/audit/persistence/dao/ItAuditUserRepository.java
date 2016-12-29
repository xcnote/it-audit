package com.it.audit.persistence.dao;

import java.util.Collection;

import com.it.audit.domain.ItAuditUser;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditUserRepository extends BasePersistenceDao<ItAuditUser, Long> {

	ItAuditUser findByLoginNameAndPassword(String loginName, String password);

	ItAuditUser findByUserId(Long userId);

	ItAuditUser findByLoginName(String loginName);

	ItAuditUser findByUserIdIn(Collection<Long> userIds);
}

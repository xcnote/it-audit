package com.it.audit.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditObjectUser;
import com.it.audit.enums.ObjectUserRole;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditObjectUserRepository;

@Service
public class ItAuditObjectUserPersistenceService extends BasePersistenceService<ItAuditObjectUser, Long> {
	
	@Autowired
	private ItAuditObjectUserRepository itAuditObjectUserRepository;
	
	@Override
	protected BasePersistenceDao<ItAuditObjectUser, Long> getPersistenceDao() {
		return this.itAuditObjectUserRepository;
	}
	
	public List<ItAuditObjectUser> findByObjectId(Long objectId){
		return this.itAuditObjectUserRepository.findByObjectId(objectId);
	}
	
	public List<ItAuditObjectUser> findByObjectIdAndRole(Long objectId, ObjectUserRole role){
		return this.itAuditObjectUserRepository.findByObjectIdAndRole(objectId, role);
	}
	
	public List<ItAuditObjectUser> findByIdIn(List<Long> ids){
		return this.itAuditObjectUserRepository.findByIdIn(ids);
	}

	public List<ItAuditObjectUser> findByUserIdAndRole(Long userId, ObjectUserRole role) {
		return this.itAuditObjectUserRepository.findByUserIdAndRole(userId, role);
	}
}

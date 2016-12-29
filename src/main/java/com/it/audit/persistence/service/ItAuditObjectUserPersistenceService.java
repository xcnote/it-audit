package com.it.audit.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditObjectUser;
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
}

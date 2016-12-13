package com.it.audit.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditUser;
import com.it.audit.enums.UserStatus;
import com.it.audit.persistence.service.ItAuditUserPersistenceService;

@Service
public class UserService {
	
	@Autowired
	private ItAuditUserPersistenceService itAuditUserPersistenceService;

	public String userLogin(String loginName, String password){
		ItAuditUser user = this.itAuditUserPersistenceService.findByLoginNameAndPassword(loginName, password);
		if(user != null && user.getStatus() != UserStatus.disable) {
			return UUID.randomUUID().toString();
		}
		return null;
	}
}

package com.it.audit.web.dto;

import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditObjectUser;
import com.it.audit.domain.ItAuditUser;
import com.it.audit.enums.ObjectUserRole;

import lombok.Data;

@Data
public class ObjectUser {

	private Long id;

	private Long userId;
	
	private String userName;
	
	private Long objectId;
	
	private ObjectUserRole role;
	
	private DateTime createTime = new DateTime();

	public ObjectUser() {
		super();
	}
	public ObjectUser(ItAuditObjectUser objectUser, ItAuditUser user) {
		super();
		this.id = objectUser.getId();
		this.userId = objectUser.getUserId();
		this.userName = user == null ? null : user.getUserName();
		this.objectId = objectUser.getObjectId();
		this.role = objectUser.getRole();
		this.createTime = objectUser.getCreateTime();
	}
	public ObjectUser(Long id, Long userId, String userName, Long objectId, ObjectUserRole role, DateTime createTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.objectId = objectId;
		this.role = role;
		this.createTime = createTime;
	}
}

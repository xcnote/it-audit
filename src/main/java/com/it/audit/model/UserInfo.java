package com.it.audit.model;

import java.util.Date;
import java.util.List;

import com.it.audit.domain.ItAuditUser;
import com.it.audit.enums.UserRole;
import com.it.audit.enums.UserStatus;

import lombok.Data;

@Data
public class UserInfo {

	private Long userId;
	private String userName;
	private String loginName;
	private String loginToken;
	private List<UserRole> roles;
	private UserStatus status;
	private Date createTime;
	private Date updateTime;
	
	public UserInfo(String loginToken, ItAuditUser user) {
		super();
		this.userId = user.getUserId();
		this.userName = user.getUserName();
		this.loginName = user.getLoginName();
		this.loginToken = loginToken;
		this.roles = user.getRoles();
		this.status = user.getStatus();
		this.createTime = user.getCreateTime().toDate();
		this.updateTime = user.getUpdateTime().toDate();
	}
}

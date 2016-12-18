package com.it.audit.web.dao;

import java.util.List;

import com.it.audit.model.UserInfo;

import lombok.Data;

@Data
public class IndexInfo {
	
	private UserInfo user;
	private List<UserRolePage> allRole;
	
	public IndexInfo(UserInfo user, List<UserRolePage> allRole) {
		super();
		this.user = user;
		this.allRole = allRole;
	}
}

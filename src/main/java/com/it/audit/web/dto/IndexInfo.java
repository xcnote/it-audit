package com.it.audit.web.dto;

import java.util.List;

import com.it.audit.model.UserInfo;

import lombok.Data;

@Data
public class IndexInfo {
	
	private UserInfo user;
	private List<UserRolePage> allRole;
	private Integer index;
	private String model;
	private String action;
	
	public IndexInfo(UserInfo user, List<UserRolePage> allRole, Integer index, String model, String action) {
		super();
		this.user = user;
		this.allRole = allRole;
		this.index = index;
		this.model = model;
		this.action = action;
	}
}

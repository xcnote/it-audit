package com.it.audit.web.dao;

import com.it.audit.enums.UserRole;

import lombok.Data;

@Data
public class UserRolePage {
	
	private UserRole role;
	
	private String imgClass;
	
	private String leftMenuUrl;
	
	private String centerContentUrl;

	public UserRolePage(UserRole role, String imgClass, String leftMenuUrl, String centerContentUrl) {
		super();
		this.role = role;
		this.imgClass = imgClass;
		this.leftMenuUrl = leftMenuUrl;
		this.centerContentUrl = centerContentUrl;
	}
}

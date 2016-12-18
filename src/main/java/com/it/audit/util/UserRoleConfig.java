package com.it.audit.util;

import java.util.ArrayList;
import java.util.List;

import com.it.audit.enums.UserRole;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dao.UserRolePage;

public class UserRoleConfig {
	
	private static final List<UserRolePage> roleConfigs = new ArrayList<UserRolePage>();
	private static final String[] roleImgClassName = new String[]{"icon-flatscreen", "icon-pencil", "icon-message", "icon-chart", "icon-chart"}; 
	
	static{
		UserRole[] roles = UserRole.values();
		for(int i=0; i<roles.length; i++){
			UserRolePage config = new UserRolePage(
					roles[i], 
					roleImgClassName[i], 
					RequestURI.INDEX_LEFT_URI.replace("{role}", roles[i].name()), 
					RequestURI.INDEX_CENTER_URI.replace("{role}", roles[i].name()));
			roleConfigs.add(config);
		}
	}

	public static List<UserRolePage> getRolePageConfigs(){
		return roleConfigs;
	}
}

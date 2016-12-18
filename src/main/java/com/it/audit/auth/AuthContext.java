package com.it.audit.auth;

import java.util.HashMap;
import java.util.Map;

import com.it.audit.model.UserInfo;

import lombok.Data;

@Data
public class AuthContext {
	private String requestId;
	private UserInfo userInfo;
	private Map<String,Object> extendObjects = new HashMap<String,Object>();
	public boolean isAnonymous(){
		return userInfo == null;
	}
}

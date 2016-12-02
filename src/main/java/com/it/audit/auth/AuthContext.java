package com.it.audit.auth;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class AuthContext {
	private String requestId;
	private String loginInfo;
	private Map<String,Object> extendObjects = new HashMap<String,Object>();
	public boolean isAnonymous(){
		return loginInfo == null;
	}
}

package com.it.audit.auth;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import com.ksyun.video.common.model.accesskey.KeyPair;
import com.ksyun.video.common.model.kscuser.KscUser;

@Data
public class AuthContext {
	private String requestId;
	private KeyPair keyPair;
	private KscUser loginInfo;
	private Map<String,Object> extendObjects = new HashMap<String,Object>();
	public boolean isAnonymous(){
		return loginInfo == null;
	}
}

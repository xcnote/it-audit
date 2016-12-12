package com.it.audit.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	public String userLogin(String userName, String password){
		if("a".equals(userName)) {
			return null;
		}
		return UUID.randomUUID().toString();
	}
}

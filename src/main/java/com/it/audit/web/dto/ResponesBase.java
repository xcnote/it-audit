package com.it.audit.web.dto;

import lombok.Data;

@Data
public class ResponesBase {

	private int code;
	private String msg;
	
	public ResponesBase() {
		super();
	}
	
	public ResponesBase(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
}

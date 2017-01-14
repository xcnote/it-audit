package com.it.audit.web.dto;

import java.util.Map;

import lombok.Data;

@Data
public class ResponesBase {

	private int code;
	private String msg;
	private String error;
	private Map<String, Object> result;
	
	public ResponesBase() {
		super();
	}
	
	public ResponesBase(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public ResponesBase(int code, String msg, String error) {
		super();
		this.code = code;
		this.msg = msg;
		this.error = error;
	}
	
	public ResponesBase(int code, String msg, String error, Map<String, Object> result) {
		super();
		this.code = code;
		this.msg = msg;
		this.error = error;
		this.result = result;
	}
}

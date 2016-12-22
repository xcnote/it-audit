package com.it.audit.enums;

public enum UserStatus {

	normal("正常"),
	disable("停用");
	
	private String text;
	
	private UserStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

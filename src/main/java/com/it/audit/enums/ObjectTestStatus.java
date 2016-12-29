package com.it.audit.enums;

public enum ObjectTestStatus {

	exectue("未提交"),
	submit("已提交"),
	pass("通过"),
	sendback("退回");
	
	private String text;

	private ObjectTestStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

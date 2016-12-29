package com.it.audit.enums;

public enum ObjectUserRole {

	PARTNER("项目合伙人"),
	REVIEW("项目质量复核人"),
	MANAGER("项目经理"),
	AUDITOR("审计师");
	
	private String text;

	private ObjectUserRole(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

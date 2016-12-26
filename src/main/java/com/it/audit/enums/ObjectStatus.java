package com.it.audit.enums;

public enum ObjectStatus {

	execute("执行中"),
	review("项目质量复核中"),
	finish("质量复核通过"),
	revise("退回修订中");
	
	private String text;

	private ObjectStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

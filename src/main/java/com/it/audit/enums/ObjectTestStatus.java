package com.it.audit.enums;

import org.apache.commons.lang3.StringUtils;

public enum ObjectTestStatus {

	exectue("未提交"),
	submit("已提交"),
	pass("通过"),
	sendback("退回");
	
	private String text;

	private ObjectTestStatus(String text) {
		this.text = text;
	}
	
	public static ObjectTestStatus updateObjectTestStatus(String managerOpinion){
		if(StringUtils.isNotEmpty(managerOpinion)){
			if("pass".equals(managerOpinion)){
				return ObjectTestStatus.pass;
			}
			if("sendback".equals(managerOpinion)){
				return ObjectTestStatus.sendback;
			}
		}
		return null;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

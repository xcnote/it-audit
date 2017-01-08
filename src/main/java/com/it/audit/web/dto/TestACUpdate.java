package com.it.audit.web.dto;

import com.it.audit.domain.ItAuditTestAC;

import lombok.Data;

@Data
public class TestACUpdate {

	private Long id;
	
	private Long objectId;
	
	//风险描述
	private String riskDesc;
	
	//AC测试内容
	private String acContent;
	
	//AC测试方法
	private String acMethod;
	
	public ItAuditTestAC updateItAuditTestAC(ItAuditTestAC ac) {
		ac.setId(id);
		ac.setObjectId(objectId);
		ac.setRiskDesc(riskDesc);
		ac.setAcContent(acContent);
		ac.setAcMethod(acMethod);
		return ac;
	}
}

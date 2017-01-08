package com.it.audit.web.dto;

import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditTestDA;

import lombok.Data;

@Data
public class TestDAUpdate {

	private Long id;
	
	private Long objectId;
	
	//风险描述
	private String riskDesc;
	
	//DA测试内容
	private String daContent;
	
	//DA测试方法
	private String daMethod;
	
	public ItAuditTestDA updateItAuditTestDA(ItAuditTestDA da) {
		da.setId(id);
		da.setObjectId(objectId);
		da.setRiskDesc(riskDesc);
		da.setDaContent(daContent);
		da.setDaMethod(daMethod);
		da.setUpdateTime(new DateTime());
		return da;
	}
}

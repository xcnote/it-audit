package com.it.audit.web.dto;

import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditTestGC;

import lombok.Data;

@Data
public class TestGCUpdate {
	
	private Long id;
	
	private Long objectId;
	
	//风险编号
	private String riskNumber;
	
	//IT风险描述
	private String riskDesc;
	
	//控制活动
	private String controlActivity;
	
	//一级控制域
	private String firstRegion;
	
	//二级控制域
	private String secondRegion;
	
	//风险初步评价
	private String riskEstimate;
	
	//监管指引
	private String supervise;
	
	//审计程序
	private String audit;

	public ItAuditTestGC updateItAuditTestGC(ItAuditTestGC gc) {
		gc.setId(id);
		gc.setObjectId(objectId);
		gc.setRiskDesc(riskDesc);
		gc.setControlActivity(controlActivity);
		gc.setFirstRegion(firstRegion);
		gc.setSecondRegion(secondRegion);
		gc.setRiskEstimate(riskEstimate);
		gc.setSupervise(supervise);
		gc.setAudit(audit);
		gc.setUpdateTime(new DateTime());
		return gc;
	}
}

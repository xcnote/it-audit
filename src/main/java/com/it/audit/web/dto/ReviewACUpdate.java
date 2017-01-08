package com.it.audit.web.dto;

import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.enums.TestImperfectionType;

import lombok.Data;

@Data
public class ReviewACUpdate {

	private Long id;
	
	//风险描述
	private String riskDesc;
	
	//AC测试内容
	private String acContent;
	
	//AC测试方法
	private String acMethod;
	
	//差异金额
	private Double disAmount;
	
	//样本金额
	private Double sampleAmount;
	
	//AC测试记录
	private String acRecord;
	
	//AC测试结论
	private String acResult;
	
	//经理意见
	private String managerOpinion;
	
	//缺陷评价
	private TestImperfectionType imperfection;
	
	//经理批注
	private String managerPostil;

	public ItAuditTestAC updateItAuditTestAC(ItAuditTestAC ac) {
		ac.setRiskDesc(riskDesc);
		ac.setAcContent(acContent);
		ac.setAcMethod(acMethod);
		ac.setDisAmount(disAmount);
		ac.setSampleAmount(sampleAmount);
		ac.setAcRecord(acRecord);
		ac.setAcResult(acResult);
		ac.setManagerOpinion(managerOpinion);
		ac.setImperfection(imperfection);
		ac.setManagerPostil(managerPostil);
		ac.setUpdateTime(new DateTime());
		return ac;
	}
	public ItAuditTestAC updateItAuditTestACToAudit(ItAuditTestAC ac) {
		ac.setDisAmount(disAmount);
		ac.setSampleAmount(sampleAmount);
		ac.setAcRecord(acRecord);
		ac.setAcResult(acResult);
		ac.setUpdateTime(new DateTime());
		return ac;
	}
}

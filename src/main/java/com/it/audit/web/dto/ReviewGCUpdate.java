package com.it.audit.web.dto;

import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.enums.TestImperfectionType;

import lombok.Data;

@Data
public class ReviewGCUpdate {
	
	private Long id;
	
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
	
	//访谈对象
	private String interview;
	
	//交叉访谈对象
	private String crossInterview;
	
	//制度文件
	private String regimeFile;
	
	//D&I有效性测试记录
	private String diRecord;
	
	//D&I有效性结论
	private String diResult;
	
	//OE测试样本数量
	private Integer oeTestNum;
	
	//OE测试无效样本数量
	private Integer oeInvalidNum;
	
	//OE测试结论
	private String oeResult;
	
	//经理意见
	private String managerOpinion;
	
	//缺陷评价
	private TestImperfectionType imperfection;
	
	//经理批注
	private String managerPostil;

	public ItAuditTestGC updateItAuditTestGC(ItAuditTestGC gc) {
		gc.setRiskDesc(riskDesc);
		gc.setControlActivity(controlActivity);
		gc.setFirstRegion(firstRegion);
		gc.setSecondRegion(secondRegion);
		gc.setRiskEstimate(riskEstimate);
		gc.setSupervise(supervise);
		gc.setAudit(audit);
		gc.setInterview(interview);
		gc.setCrossInterview(crossInterview);
		gc.setRegimeFile(regimeFile);
		gc.setDiRecord(diRecord);
		gc.setDiResult(diResult);
		gc.setOeTestNum(oeTestNum);
		gc.setOeInvalidNum(oeInvalidNum);
		gc.setOeResult(oeResult);
		gc.setManagerOpinion(managerOpinion);
		gc.setImperfection(imperfection);
		gc.setManagerPostil(managerPostil);
		gc.setUpdateTime(new DateTime());
		return gc;
	}
	public ItAuditTestGC updateItAuditTestGCToAudit(ItAuditTestGC gc) {
		gc.setInterview(interview);
		gc.setCrossInterview(crossInterview);
		gc.setRegimeFile(regimeFile);
		gc.setDiRecord(diRecord);
		gc.setDiResult(diResult);
		gc.setOeTestNum(oeTestNum);
		gc.setOeInvalidNum(oeInvalidNum);
		gc.setOeResult(oeResult);
		gc.setUpdateTime(new DateTime());
		return gc;
	}
}

package com.it.audit.web.dto;

import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.enums.TestImperfectionType;

import lombok.Data;

@Data
public class ReviewDAUpdate {

	private Long id;
	
	//风险描述
	private String riskDesc;
	
	//DA测试内容
	private String daContent;
	
	//DA测试方法
	private String daMethod;
	
	//问题样本金额
	private Double problemAmount;
	
	//样本金额
	private Double sampleAmount;
	
	//DA测试记录
	private String daRecord;
	
	//DA测试结论
	private String daResult;
	
	//经理意见
	private String managerOpinion;
	
	//缺陷评价
	private TestImperfectionType imperfection;
	
	//经理批注
	private String managerPostil;

	public ItAuditTestDA updateItAuditTestDA(ItAuditTestDA da) {
		da.setRiskDesc(riskDesc);
		da.setDaContent(daContent);
		da.setDaMethod(daMethod);
		da.setProblemAmount(problemAmount);
		da.setSampleAmount(sampleAmount);
		da.setDaRecord(daRecord);
		da.setDaResult(daResult);
		da.setManagerOpinion(managerOpinion);
		da.setImperfection(imperfection);
		da.setManagerPostil(managerPostil);
		da.setUpdateTime(new DateTime());
		return da;
	}
}

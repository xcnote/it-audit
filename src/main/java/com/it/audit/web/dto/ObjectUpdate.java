package com.it.audit.web.dto;

import java.util.Date;

import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditObject;
import com.it.audit.enums.ObjectStatus;

import lombok.Data;

@Data
public class ObjectUpdate {
	
	private Long id;
	
	//客户名称
	private String customName;
	
	//客户行业
	private String customTrade;
	
	//客户资产规模
	private String customAsset;
	
	//客户风险评级
	private String customRate;
	
	//项目名称
	private String name;
	
	//是否属于财务审计项目
	private Boolean financeAudit;
	
	//财务审计项目编号
	private String financeNumber;
	
	//审计开始
	private Date auditStartTime;
	
	//审计结束
	private Date auditEndTime;
	
	//现场工作开始
	private Date workStratTime;
	
	//现场工作结束
	private Date workEndTime;
	
	//审计目标
	private String target;
	
	//项目状态
	private ObjectStatus status;

	public ItAuditObject updateItAuditObject(ItAuditObject object) {
		object.setCustomName(customName);
		object.setCustomTrade(customTrade);
		object.setCustomAsset(customAsset);
		object.setCustomRate(customRate);
		object.setName(name);
		object.setFinanceAudit(financeAudit);
		object.setFinanceNumber(financeNumber);
		object.setAuditStartTime(new DateTime(auditStartTime.getTime()));
		object.setAuditEndTime(new DateTime(auditEndTime.getTime()));
		object.setWorkStratTime(new DateTime(workStratTime.getTime()));
		object.setWorkEndTime(new DateTime(workEndTime.getTime()));
		object.setTarget(target);
		return object;
	}
}

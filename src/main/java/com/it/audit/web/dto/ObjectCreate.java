package com.it.audit.web.dto;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.it.audit.domain.ItAuditObject;
import com.it.audit.util.DateUtils;

import lombok.Data;

@Data
public class ObjectCreate {
	//客户名称
	@NotNull(message="客户名称不能为空")
	private String customName;
	
	//客户行业
	private String customTrade;
	
	//客户资产规模
	private String customAsset;
	
	//客户风险评级
	private String customRate;
	
	//项目名称
	@NotNull(message="项目名称不能为空")
	private String name;
	
	//是否属于财务审计项目
	private Boolean financeAudit;
	
	//财务审计项目编号
	private String financeNumber;
	
	//审计开始
	private String auditStartTime;
	
	//审计结束
	private String auditEndTime;
	
	//现场工作开始
	private String workStratTime;
	
	//现场工作结束
	private String workEndTime;
	
	//审计目标
	private String target;

	public ItAuditObject buildItAuditObject() {
		ItAuditObject object = new ItAuditObject();
		object.setCustomName(this.customName);
		object.setCustomTrade(this.customTrade);
		object.setCustomAsset(this.customAsset);
		object.setCustomRate(this.customRate);
		object.setName(this.name);
		object.setFinanceAudit(this.financeAudit);
		object.setFinanceNumber(this.financeNumber);
		object.setAuditStartTime(StringUtils.isNotBlank(this.auditStartTime)? new DateTime(DateUtils.parseDate(this.auditStartTime, "yyyy-MM-dd").getTime()): null);
		object.setAuditEndTime(StringUtils.isNotBlank(this.auditEndTime)? new DateTime(DateUtils.parseDate(this.auditEndTime, "yyyy-MM-dd").getTime()): null);
		object.setWorkStratTime(StringUtils.isNotBlank(this.workStratTime)? new DateTime(DateUtils.parseDate(this.workStratTime, "yyyy-MM-dd").getTime()): null);
		object.setWorkEndTime(StringUtils.isNotBlank(this.workEndTime)? new DateTime(DateUtils.parseDate(this.workEndTime, "yyyy-MM-dd").getTime()): null);
		object.setTarget(this.target);
		return object;
	}
}

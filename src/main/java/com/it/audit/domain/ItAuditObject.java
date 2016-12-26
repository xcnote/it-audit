package com.it.audit.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.it.audit.enums.ObjectStatus;

import lombok.Data;

@javax.persistence.Entity
@javax.persistence.Table(name = "it_audit_object", uniqueConstraints = {
      }, indexes = {
      })
@javax.persistence.EntityListeners(AuditingEntityListener.class)
@Data
public class ItAuditObject {

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, insertable = false, nullable = false)
	private Long id;
	
	//客户名称
	@Column(name = "custom_name")
	private String customName;
	
	//客户行业
	@Column(name = "custom_trade")
	private String customTrade;
	
	//客户资产规模
	@Column(name = "custom_asset")
	private String customAsset;
	
	//客户风险评级
	@Column(name = "custom_rate")
	private String customRate;
	
	//项目名称
	@Column(name = "name")
	private String name;
	
	//项目经理
	@Column(name = "manager_user_id")
	private Long managerUserId;
	
	//是否属于财务审计项目
	@Column(name = "finance_audit")
	private Boolean financeAudit;
	
	//财务审计项目编号
	@Column(name = "finance_number")
	private String financeNumber;
	
	//审计开始
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "audit_start_time")
	private DateTime auditStartTime;
	
	//审计结束
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "audit_end_time")
	private DateTime auditEndTime;
	
	//现场工作开始
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "work_strat_time")
	private DateTime workStratTime;
	
	//现场工作结束
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "work_end_time")
	private DateTime workEndTime;
	
	//审计目标
	@Column(name = "target", length=500)
	private String target;
	
	//项目编号
	@Column(name = "object_number")
	private String objectNumber;
	
	//项目创建人
	@Column(name = "create_user_id")
	private Long createUserId;
	
	//项目状态
	@Column(name = "status")
	private ObjectStatus status;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "create_time")
	private DateTime createTime;
	
	//修订日期
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "revise_time")
	private DateTime reviseTime;
	
	//项目合伙人ID
	@Column(name = "partner_user_id")
	private Long partnerUserId;
	
	//项目质量复核人ID
	@Column(name = "review_user_id")
	private Long reviewUserId;
}

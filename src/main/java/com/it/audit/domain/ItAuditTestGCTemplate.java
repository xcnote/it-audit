package com.it.audit.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * GC测试模板表
 * @author wangx
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "it_audit_test_gc_template", uniqueConstraints = {
      }, indexes = {
      })
@javax.persistence.EntityListeners(AuditingEntityListener.class)
@Data
public class ItAuditTestGCTemplate {

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, insertable = false, nullable = false)
	private Long id;
	
	//风险编号
	@Column(name = "risk_number")
	private String riskNumber;
	
	//IT风险描述
	@Column(name = "risk_desc", length = 1000)
	private String riskDesc;
	
	//控制活动
	@Column(name = "control_activity")
	private String controlActivity;
	
	//一级控制域
	@Column(name = "first_region")
	private String firstRegion;
	
	//二级控制域
	@Column(name = "second_region")
	private String secondRegion;
	
	//风险初步评价
	@Column(name = "risk_estimate", length = 1000)
	private String riskEstimate;
	
	//监管指引
	@Column(name = "supervise", length = 500)
	private String supervise;
	
	//审计程序
	@Column(name = "audit", length = 1000)
	private String audit;
	
	private Long groupId;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "create_time")
	private DateTime createTime = new DateTime();
}

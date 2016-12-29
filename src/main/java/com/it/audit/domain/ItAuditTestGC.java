package com.it.audit.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.it.audit.enums.ObjectTestStatus;

import lombok.Data;

@javax.persistence.Entity
@javax.persistence.Table(name = "it_audit_test_gc", uniqueConstraints = {
      }, indexes = {
      })
@javax.persistence.EntityListeners(AuditingEntityListener.class)
@Data
public class ItAuditTestGC {

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, insertable = false, nullable = false)
	private Long id;
	
	//测试编号
	@Column(name = "number")
	private String number;
	
	//风险编号
	@Column(name = "risk_number")
	private String riskNumber;
	
	//IT风险描述
	@Column(name = "risk_desc", length = 1000)
	private String riskDesc;
	
	//控制编号
	@Column(name = "control_number")
	private String controlNumber;
	
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
	
	//访谈对象
	@Column(name = "interview")
	private String interview;
	
	//交叉访谈对象
	@Column(name = "cross_interview")
	private String crossInterview;
	
	//制度文件
	@Column(name = "regime_file")
	private String regimeFile;
	
	//D&I有效性测试记录
	@Column(name = "di_record", length = 500)
	private String diRecord;
	
	//D&I有效性结论
	@Column(name = "di_result", length = 500)
	private String diResult;
	
	//OE是否测试
	@Column(name = "oe")
	private Boolean oe;
	
	//OE测试样本数量
	@Column(name = "oe_test_num")
	private Integer oeTestNum;
	
	//OE测试无效样本数量
	@Column(name = "oe_invalid_num")
	private Integer oeInvalidNum;
	
	//OE测试记录
	@Column(name = "oe_record", length = 500)
	private String oeRecord;
	
	//OE测试结论
	@Column(name = "oe_result", length = 500)
	private String oeResult;
	
	//测试人
	@Column(name = "test_userid")
	private Long testUserId;
	
	//最后提交日期
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "submit_time")
	private DateTime submitTime;
	
	//经理意见
	@Column(name = "manager_opinion", length = 500)
	private String managerOpinion;
	
	//缺陷评价
	@Column(name = "imperfection", length = 500)
	private String imperfection;
	
	//经理批注
	@Column(name = "manager_postil", length = 500)
	private String managerPostil;
	
	//状态
	@Column(name = "status")
	private ObjectTestStatus status = ObjectTestStatus.exectue;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "create_time")
	private DateTime createTime = new DateTime();
	
	@Column(name = "object_id")
	private Long objectId;
}

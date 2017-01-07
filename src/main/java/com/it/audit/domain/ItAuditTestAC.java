package com.it.audit.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.enums.TestImperfectionType;
import com.it.audit.util.CommonUtil;

import lombok.Data;

/**
 * AC测试表
 * @author wangx
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "it_audit_test_ac", uniqueConstraints = {
      }, indexes = {
      })
@javax.persistence.EntityListeners(AuditingEntityListener.class)
@Data
public class ItAuditTestAC {

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, insertable = false, nullable = false)
	private Long id;
	
	//测试编号
	@Column(name = "number")
	private String number;
	
	//风险描述
	@Column(name = "risk_desc", length = 1000)
	private String riskDesc;
	
	//AC测试内容
	@Column(name = "ac_content", length = 1000)
	private String acContent;
	
	//AC测试方法
	@Column(name = "ac_method", length = 1000)
	private String acMethod;
	
	//差异金额
	@Column(name = "dis_amount")
	private Double disAmount;
	
	//样本金额
	@Column(name = "sample_amount")
	private Double sampleAmount;
	
	//AC测试记录
	@Column(name = "ac_record", length = 500)
	private String acRecord;
	
	//AC测试结论
	@Column(name = "ac_result", length = 500)
	private String acResult;
	
	//差异比例
	@Column(name = "dis_ratio")
	private Double disRatio;
	
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
	@Column(name = "imperfection")
	private TestImperfectionType imperfection;
	
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
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "update_time")
	private DateTime updateTime;
	
	@Column(name = "object_id")
	private Long objectId;
	
	public String getManagerOpinionToChinese(){
		return CommonUtil.managerOpinionToChinese(managerOpinion);
	}
}

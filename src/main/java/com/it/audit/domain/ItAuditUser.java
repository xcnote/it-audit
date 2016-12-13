package com.it.audit.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;


@javax.persistence.Entity
@javax.persistence.Table(name = "it_audit_user", uniqueConstraints = {
      }, indexes = {
      })
@javax.persistence.EntityListeners(AuditingEntityListener.class)
@Data
public class ItAuditUser {

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, insertable = false, nullable = false)
	private Long id;
	
	//用户id
	@Column(name = "user_id")
	private Long userId;
	
	//用户名
	@Column(name = "user_name")
	private String userName;
	
	//用户类型
	@Column(name = "user_type")
	private String userType;
	
	//用户状态
	@Column(name = "status")
	private Long status;
	
	//创建时间
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "create_time")
	private DateTime createTime;
	
	//更新时间
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "update_time")
	private DateTime updateTime;
}

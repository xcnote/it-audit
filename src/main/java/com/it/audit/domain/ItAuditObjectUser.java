package com.it.audit.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.it.audit.enums.ObjectUserRole;

import lombok.Data;

/**
 * 项目成员表
 * @author wangx
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "it_audit_object_user", uniqueConstraints = {
      }, indexes = {
      })
@javax.persistence.EntityListeners(AuditingEntityListener.class)
@Data
public class ItAuditObjectUser {
	
	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, insertable = false, nullable = false)
	private Long id;

	//用户id
	@Column(name = "user_id")
	private Long userId;
	
	//项目id
	@Column(name = "object_id")
	private Long objectId;
	
	//项目职责
	@Column(name = "role")
	private ObjectUserRole role;
	
	//创建时间
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "create_time")
	private DateTime createTime = new DateTime();
}

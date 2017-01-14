package com.it.audit.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.it.audit.enums.FileType;

import lombok.Data;

/**
 * 文件关系表
 * @author wangx
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "it_audit_file", uniqueConstraints = {
      }, indexes = {
      })
@javax.persistence.EntityListeners(AuditingEntityListener.class)
@Data
public class ItAuditFile {
	
	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, insertable = false, nullable = false)
	private Long id;

	//项目id
	@Column(name = "object_id")
	private Long objectId;
	
	//测试id
	@Column(name = "test_id")
	private Long testId;
	
	//原文件名
	@Column(name = "name")
	private String name;
	
	//文件类型
	@Column(name = "type")
	private FileType type;
	
	//文件路径
	@Column(name = "file_path")
	private String filePath;
	
	//文件名
	@Column(name = "file_name")
	private String fileName;
	
	//创建时间
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat
	@Column(name = "create_time")
	private DateTime createTime = new DateTime();
}

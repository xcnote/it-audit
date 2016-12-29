package com.it.audit.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.auth.AuthContextHolder;
import com.it.audit.domain.ItAuditObject;
import com.it.audit.enums.ObjectStatus;
import com.it.audit.persistence.service.ConstantIndustryPersistenceService;
import com.it.audit.persistence.service.ItAuditObjectPersistenceService;
import com.it.audit.util.DateUtils;
import com.it.audit.web.dto.ObjectCreate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagerService {

	@Autowired
	private ConstantIndustryPersistenceService industryPersistenceService;
	@Autowired
	private ItAuditObjectPersistenceService itAuditObjectPersistenceService;
	
	/**
	 * 项目经理主页数据
	 * @return
	 */
	public Map<String, List<ItAuditObject>> queryIndexData(){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		List<ItAuditObject> execObjects = this.itAuditObjectPersistenceService.findByCreateTime(userId, ObjectStatus.execute, new DateTime(DateUtils.startTimeInYear().getTime()), new DateTime());
		List<ItAuditObject> finishObjects = this.itAuditObjectPersistenceService.findByCreateTime(userId, ObjectStatus.finish, new DateTime(DateUtils.startTimeInYear().getTime()), new DateTime());
		Map<String, List<ItAuditObject>> result = new HashMap<String, List<ItAuditObject>>();
		result.put("exec", execObjects);
		result.put("finish", finishObjects);
		return result;
	}
	
	/**
	 * 创建项目页面所需数据
	 * @return
	 */
	public Map<String, Object> buildCreateParam(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("industrys", this.industryPersistenceService.findAll());
		return param;
	}
	
	/**
	 * 项目创建
	 * @param createInfo
	 * @return
	 */
	public boolean createObject(ObjectCreate createInfo){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		
		try {
			DateTime date = new DateTime();
			ItAuditObject object = createInfo.buildItAuditObject();
			object.setManagerUserId(userId);
			object.setObjectNumber(Long.toHexString(userId).toUpperCase()+Long.toHexString(new Date().getTime()/1000).toUpperCase());
			object.setCreateUserId(userId);
			object.setStatus(ObjectStatus.execute);
			object.setCreateTime(date);
			object.setReviseTime(date);
			this.itAuditObjectPersistenceService.save(object);
			return true;
		} catch (Exception e) {
			log.error("create Object error by userId:{}", userId, e);
		}
		return false;
	}
	
	/**
	 * 查询项目分页
	 * @param pageRequest
	 * @return
	 */
	public Page<ItAuditObject> queryObjectPage(PageRequest pageRequest, String queryKey, String queryValue){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		return this.itAuditObjectPersistenceService.findByParam(pageRequest, queryKey, queryValue, userId);
	}
	
	public ItAuditObject queryObjectDetail(Long id){
		return this.itAuditObjectPersistenceService.findById(id);
	}
}

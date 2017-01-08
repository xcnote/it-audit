package com.it.audit.service;

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
import com.it.audit.persistence.service.ItAuditObjectPersistenceService;
import com.it.audit.util.DateUtils;

@Service
public class ReviewerService {
	
	@Autowired
	private ItAuditObjectPersistenceService itAuditObjectPersistenceService;

	/**
	 * 主页数据
	 * @return
	 */
	public Map<String, List<ItAuditObject>> queryIndexData(){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		List<ItAuditObject> execObjects = this.itAuditObjectPersistenceService.findReviewerByCreateTime(userId, ObjectStatus.review, new DateTime(DateUtils.startTimeInYear().getTime()), new DateTime());
		List<ItAuditObject> finishObjects = this.itAuditObjectPersistenceService.findReviewerByCreateTime(userId, ObjectStatus.finish, new DateTime(DateUtils.startTimeInYear().getTime()), new DateTime());
		Map<String, List<ItAuditObject>> result = new HashMap<String, List<ItAuditObject>>();
		result.put("exec", execObjects);
		result.put("finish", finishObjects);
		return result;
	}
	
	/**
	 * 查询项目分页
	 * @param pageRequest
	 * @return
	 */
	public Page<ItAuditObject> queryObjectPage(PageRequest pageRequest, String queryKey, String queryValue){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		return this.itAuditObjectPersistenceService.findReviewerByParam(pageRequest, queryKey, queryValue, userId);
	}
	
	/**
	 * 查询项目详情
	 * @param id
	 * @return
	 */
	public ItAuditObject queryObjectDetail(Long objectId){
		return this.itAuditObjectPersistenceService.findById(objectId);
	}
}

package com.it.audit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.auth.AuthContextHolder;
import com.it.audit.domain.ItAuditObject;
import com.it.audit.domain.ItAuditObjectUser;
import com.it.audit.enums.ObjectStatus;
import com.it.audit.enums.ObjectUserRole;
import com.it.audit.persistence.service.ItAuditObjectPersistenceService;
import com.it.audit.persistence.service.ItAuditObjectUserPersistenceService;
import com.it.audit.util.DateUtils;

@Service
public class AuditorService {
	
	@Autowired
	private ItAuditObjectPersistenceService itAuditObjectPersistenceService;
	@Autowired
	private ItAuditObjectUserPersistenceService itAuditObjectUserPersistenceService;

	/**
	 * 主页数据
	 * @return
	 */
	public Map<String, List<ItAuditObject>> queryIndexData(){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		List<ItAuditObjectUser> infos = this.itAuditObjectUserPersistenceService.findByUserIdAndRole(userId, ObjectUserRole.AUDITOR);
		Set<Long> objectIds = new HashSet<>();
		if(infos == null || infos.size() == 0){
			objectIds.add(0L);
		} else {
			for(ItAuditObjectUser info: infos){
				objectIds.add(info.getObjectId());
			}
		}
		List<ItAuditObject> execObjects = this.itAuditObjectPersistenceService.findByIdsAndCreateTime(new ArrayList<Long>(objectIds), ObjectStatus.execute, new DateTime(DateUtils.startTimeInYear().getTime()), new DateTime());
		List<ItAuditObject> finishObjects = this.itAuditObjectPersistenceService.findByIdsAndCreateTime(new ArrayList<Long>(objectIds), ObjectStatus.finish, new DateTime(DateUtils.startTimeInYear().getTime()), new DateTime());
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
		List<ItAuditObjectUser> infos = this.itAuditObjectUserPersistenceService.findByUserIdAndRole(userId, ObjectUserRole.AUDITOR);
		Set<Long> objectIds = new HashSet<>();
		if(infos == null || infos.size() == 0){
			objectIds.add(0L);
		} else {
			for(ItAuditObjectUser info: infos){
				objectIds.add(info.getObjectId());
			}
		}
		return this.itAuditObjectPersistenceService.findAuditByParam(pageRequest, queryKey, queryValue, new ArrayList<Long>(objectIds));
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

package com.it.audit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.persistence.service.ItAuditTestDAPersistenceService;

@Service
public class ObjectTestDAService {

	@Autowired
	private ItAuditTestDAPersistenceService testDAPersistenceService;
	
	/**
	 * 查询DA条数
	 * @param objectId
	 * @return
	 */
	public Integer queryTestDATotal(Long objectId){
		return this.testDAPersistenceService.findCountByObjectId(objectId);
	}
	
	/**
	 * 查询DA列表
	 * @param pageRequest
	 * @param objectId
	 * @return
	 */
	public Page<ItAuditTestDA> queryTestDAList(PageRequest pageRequest, Long objectId){
		return this.testDAPersistenceService.findByParam(pageRequest, null, null, objectId, null);
	}
	public Page<ItAuditTestDA> queryTestDAList(PageRequest pageRequest, Long objectId, List<ObjectTestStatus> status){
		return this.testDAPersistenceService.findByParam(pageRequest, null, null, objectId, status);
	}
	public Page<ItAuditTestDA> queryTestDAList(PageRequest pageRequest, Long objectId, String queryKey, Object queryValue){
		return this.testDAPersistenceService.findByParam(pageRequest, queryKey, queryValue, objectId, null);
	}
	
	public void deleteDAs(List<Long> ids){
		this.testDAPersistenceService.delete(ids.toArray(new Long[]{}));
	}
	
	public ItAuditTestDA queryById(Long id){
		return this.testDAPersistenceService.findById(id);
	}
	
	public void save(ItAuditTestDA da){
		List<ItAuditTestDA> das = new ArrayList<>();
		das.add(da);
		this.save(das, da.getObjectId());
	}
	public void save(List<ItAuditTestDA> das, Long objectId){
		List<ItAuditTestDA> result = this.testDAPersistenceService.save(das);
		for(ItAuditTestDA da: result){
			String number = Long.toHexString(objectId).toUpperCase()+"DA"+da.getId();
			da.setNumber(number);
		}
		this.testDAPersistenceService.save(result);
	}
	
	/**
	 * 查询指定项目和测试人下的DA测试
	 * @param userId
	 * @param objectId
	 * @return
	 */
	public List<ItAuditTestDA> queryTestDAByTestUser(Long userId, Long objectId){
		return this.testDAPersistenceService.findByObjectIdAndTestUserId(objectId, userId);
	}

	/**
	 * DA任务分配
	 * @param userId
	 * @param gcIds
	 * @return
	 */
	public String allotDATask(Long userId, List<Long> daIds) {
		List<ItAuditTestDA> acs = this.testDAPersistenceService.findAll(daIds);
		if(acs.size() != daIds.size()){
			return "存在已删除的任务，请重新分配";
		}
		this.testDAPersistenceService.updateTestUserId(userId, daIds);
		return null;
	}
	
	/**
	 * 更新任务状态
	 * @param ids
	 * @param status
	 */
	public void updateTaskStatus(List<Long> ids, ObjectTestStatus status){
		this.testDAPersistenceService.updateTestStatus(ids, status);
	}
}

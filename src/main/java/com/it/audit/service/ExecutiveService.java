package com.it.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditObject;
import com.it.audit.persistence.service.ItAuditObjectPersistenceService;

@Service
public class ExecutiveService {
	
	@Autowired
	private ItAuditObjectPersistenceService itAuditObjectPersistenceService;
	
	/**
	 * 查询项目分页
	 * @param pageRequest
	 * @return
	 */
	public Page<ItAuditObject> queryObjectPage(PageRequest pageRequest, String queryKey, String queryValue){
		//Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		return this.itAuditObjectPersistenceService.findAuditByParam(pageRequest, queryKey, queryValue, null);
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

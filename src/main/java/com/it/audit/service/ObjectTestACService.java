package com.it.audit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.persistence.service.ItAuditTestACPersistenceService;

@Service
public class ObjectTestACService {

	@Autowired
	private ItAuditTestACPersistenceService testACPersistenceService;
	
	/**
	 * 查询AC条数
	 * @param objectId
	 * @return
	 */
	public Integer queryTestACTotal(Long objectId){
		return this.testACPersistenceService.findCountByObjectId(objectId);
	}
	
	/**
	 * 查询AC列表
	 * @param pageRequest
	 * @param objectId
	 * @return
	 */
	public Page<ItAuditTestAC> queryTestACList(PageRequest pageRequest, Long objectId){
		return this.testACPersistenceService.findByParam(pageRequest, null, null, objectId);
	}
	
	
	public void deleteACs(List<Long> ids){
		this.testACPersistenceService.delete(ids.toArray(new Long[]{}));
	}
	
	public ItAuditTestAC queryById(Long id){
		return this.testACPersistenceService.findById(id);
	}
	
	public void save(ItAuditTestAC ac){
		List<ItAuditTestAC> acs = new ArrayList<>();
		acs.add(ac);
		this.save(acs, ac.getObjectId());
	}
	public void save(List<ItAuditTestAC> acs, Long objectId){
		List<ItAuditTestAC> result = this.testACPersistenceService.save(acs);
		for(ItAuditTestAC ac: result){
			String number = Long.toHexString(objectId).toUpperCase()+"AC"+ac.getId();
			ac.setNumber(number);
		}
		this.testACPersistenceService.save(result);
	}
}

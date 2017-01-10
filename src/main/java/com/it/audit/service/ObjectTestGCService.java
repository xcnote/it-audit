package com.it.audit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.domain.ItAuditTestGCTemplate;
import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.enums.TestImperfectionType;
import com.it.audit.persistence.service.ItAuditTestGCPersistenceService;

@Service
public class ObjectTestGCService {

	@Autowired
	private ItAuditTestGCPersistenceService testGCPersistenceService;
	@Autowired
	private AuditTestTemplateService auditTestTemplateService;
	
	/**
	 * 查询GC条数
	 * @param objectId
	 * @return
	 */
	public Integer queryTestGCTotal(Long objectId){
		return this.testGCPersistenceService.findCountByObjectId(objectId);
	}
	
	public List<ItAuditTestGC> queryTestGCAllList(Long objectId){
		return this.testGCPersistenceService.findByObjectId(objectId);
	}
	
	public List<ItAuditTestGC> queryTestGCAllListByImperfections(Long objectId, List<TestImperfectionType> imperfections){
		return this.testGCPersistenceService.findByObjectIdAndImperfectionIn(objectId, imperfections);
	}
	
	/**
	 * 查询GC列表
	 * @param pageRequest
	 * @param objectId
	 * @return
	 */
	public Page<ItAuditTestGC> queryTestGCList(PageRequest pageRequest, Long objectId){
		return this.testGCPersistenceService.findByParam(pageRequest, null, null, objectId, null);
	}
	public Page<ItAuditTestGC> queryTestGCList(PageRequest pageRequest, Long objectId, List<ObjectTestStatus> status){
		return this.testGCPersistenceService.findByParam(pageRequest, null, null, objectId, status);
	}
	public Page<ItAuditTestGC> queryTestGCList(PageRequest pageRequest, Long objectId, String queryKey, Object queryValue){
		return this.testGCPersistenceService.findByParam(pageRequest, queryKey, queryValue, objectId, null);
	}
	
	/**
	 * gc导入模板
	 * @param templateGroupId
	 * @param objectId
	 */
	public void testGCImport(Long templateGroupId, Long objectId){
		List<ItAuditTestGCTemplate> templates = this.auditTestTemplateService.queryAllTemplate(templateGroupId);
		if(templates == null){
			return;
		}
		List<ItAuditTestGC> gcs = new ArrayList<>();
		for(ItAuditTestGCTemplate template: templates){
			ItAuditTestGC gc = this.buildTestGC(template);
			gc.setObjectId(objectId);
			gcs.add(gc);
		}
		if(!gcs.isEmpty()){
			this.save(gcs, objectId);
		}
	}
	
	public void deleteGCs(List<Long> ids){
		this.testGCPersistenceService.delete(ids.toArray(new Long[]{}));
	}
	
	public ItAuditTestGC queryById(Long id){
		return this.testGCPersistenceService.findById(id);
	}
	
	public void save(ItAuditTestGC gc){
		List<ItAuditTestGC> gcs = new ArrayList<>();
		gcs.add(gc);
		this.save(gcs, gc.getObjectId());
	}
	public void save(List<ItAuditTestGC> gcs, Long objectId){
		List<ItAuditTestGC> result = this.testGCPersistenceService.save(gcs);
		for(ItAuditTestGC gc: result){
			String number = Long.toHexString(objectId).toUpperCase()+"GC"+gc.getId();
			gc.setNumber(number);
		}
		this.testGCPersistenceService.save(result);
	}
	
	/**
	 * 查询指定项目和测试人下的GC测试
	 * @param userId
	 * @param objectId
	 * @return
	 */
	public List<ItAuditTestGC> queryTestGCByTestUser(Long userId, Long objectId){
		return this.testGCPersistenceService.findByObjectIdAndTestUserId(objectId, userId);
	}
	
	/**
	 * GC任务分配
	 * @param userId
	 * @param gcIds
	 * @return
	 */
	public String allotGCTask(Long userId, List<Long> gcIds){
		List<ItAuditTestGC> gcs = this.testGCPersistenceService.findAll(gcIds);
		if(gcs.size() != gcIds.size()){
			return "存在已删除的任务，请重新分配";
		}
		this.testGCPersistenceService.updateTestUserId(userId, gcIds);
		return null;
	}
	
	/**
	 * 更新任务状态
	 * @param ids
	 * @param status
	 */
	public void updateTaskStatus(List<Long> ids, ObjectTestStatus status){
		this.testGCPersistenceService.updateTestStatus(ids, status);
	}
	
	private ItAuditTestGC buildTestGC(ItAuditTestGCTemplate template){
		ItAuditTestGC gc = new ItAuditTestGC();
		gc.setRiskNumber(template.getRiskNumber());
		gc.setRiskDesc(template.getRiskDesc());
		gc.setControlActivity(template.getControlActivity());
		gc.setFirstRegion(template.getFirstRegion());
		gc.setSecondRegion(template.getSecondRegion());
		gc.setRiskEstimate(template.getRiskEstimate());
		gc.setSupervise(template.getSupervise());
		gc.setAudit(template.getAudit());
		return gc;
	}
}

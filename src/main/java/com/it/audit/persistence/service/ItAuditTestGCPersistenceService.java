package com.it.audit.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.enums.ObjectTestStatus;
import com.it.audit.enums.TestImperfectionType;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditTestGCRepository;

@Service
public class ItAuditTestGCPersistenceService extends BasePersistenceService<ItAuditTestGC, Long> {
	
	@Autowired
	private ItAuditTestGCRepository itAuditTestGCRepository;
	
	@Override
	protected BasePersistenceDao<ItAuditTestGC, Long> getPersistenceDao() {
		return this.itAuditTestGCRepository;
	}
	
	public Integer findCountByObjectId(Long objectId){
		return this.itAuditTestGCRepository.findCountByObjectId(objectId);
	}
	
	public List<ItAuditTestGC> findByObjectId(Long objectId){
		return this.itAuditTestGCRepository.findByObjectId(objectId);
	}
	
	public List<ItAuditTestGC> findByObjectIdAndImperfectionIn(Long objectId,
			List<TestImperfectionType> imperfections) {
		return this.itAuditTestGCRepository.findByObjectIdAndImperfectionIn(objectId, imperfections);
	}
	
	public List<ItAuditTestGC> findByObjectIdAndTestUserId(Long objectId, Long testUserId){
		return this.itAuditTestGCRepository.findByObjectIdAndTestUserId(objectId, testUserId);
	}
	
	public void updateTestUserId(Long userId, List<Long> ids){
		this.itAuditTestGCRepository.updateTestUserId(userId, ids);
	}
	
	public void updateTestStatus(List<Long> ids, ObjectTestStatus status) {
		this.itAuditTestGCRepository.updateTestStatus(ids, status);
	}
	
	public Page<ItAuditTestGC> findByParam(PageRequest pageRequest, final String queryKey, final Object queryValue, final Long objectId, final List<ObjectTestStatus> status){
		return this.itAuditTestGCRepository.findAll(new Specification<ItAuditTestGC>() {
			@Override
			public Predicate toPredicate(Root<ItAuditTestGC> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(!StringUtils.isEmpty(queryKey)){
					if("objectNumber".equals(queryKey)){
						Path<String> objectNumber = root.get("objectNumber");
						predicates.add(cb.equal(objectNumber, queryValue));
					}
					if("name".equals(queryKey)){
						Path<String> name = root.get("name");
						predicates.add(cb.like(name, "%" + queryValue +"%"));
					}
					if("testUserId".equals(queryKey)){
						Path<Long> name = root.get("testUserId");
						if("-1".equals(queryValue.toString())){
							predicates.add(cb.isNull(name));
						} else {
							predicates.add(cb.equal(name, queryValue));
						}
					}
				}
				if(CollectionUtils.isNotEmpty(status)){
					Path<ObjectTestStatus> name = root.get("status");
					predicates.add(name.in(status));
				}
				Path<Long> objectIdPath = root.get("objectId");
				predicates.add(cb.equal(objectIdPath, objectId));
				
	            if (predicates.size() > 0) {  
	                return cb.and(predicates.toArray(new Predicate[predicates.size()]));  
	            }
				return cb.conjunction();
			}
		}, pageRequest);
	}
}

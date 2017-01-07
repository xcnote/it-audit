package com.it.audit.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.it.audit.domain.ItAuditTestGC;
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
	
	public List<ItAuditTestGC> findByObjectIdAndTestUserId(Long objectId, Long testUserId){
		return this.itAuditTestGCRepository.findByObjectIdAndTestUserId(objectId, testUserId);
	}
	
	public void updateTestUserId(Long userId, List<Long> ids){
		this.itAuditTestGCRepository.updateTestUserId(userId, ids);
	}
	
	public Page<ItAuditTestGC> findByParam(PageRequest pageRequest, final String queryKey, final Object queryValue, final Long objectId){
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

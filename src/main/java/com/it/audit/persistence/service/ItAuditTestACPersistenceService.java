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

import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditTestACRepository;

@Service
public class ItAuditTestACPersistenceService extends BasePersistenceService<ItAuditTestAC, Long> {
	
	@Autowired
	private ItAuditTestACRepository itAuditTestACRepository;
	
	@Override
	protected BasePersistenceDao<ItAuditTestAC, Long> getPersistenceDao() {
		return this.itAuditTestACRepository;
	}
	
	public Integer findCountByObjectId(Long objectId){
		return this.itAuditTestACRepository.findCountByObjectId(objectId);
	}
	
	public Page<ItAuditTestAC> findByParam(PageRequest pageRequest, final String queryKey, final String queryValue, final Long objectId){
		return this.itAuditTestACRepository.findAll(new Specification<ItAuditTestAC>() {
			@Override
			public Predicate toPredicate(Root<ItAuditTestAC> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
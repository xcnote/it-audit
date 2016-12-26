package com.it.audit.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.it.audit.domain.ItAuditObject;
import com.it.audit.enums.ObjectStatus;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditObjectRepository;

@Service
public class ItAuditObjectPersistenceService extends BasePersistenceService<ItAuditObject, Long> {
	
	@Autowired
	private ItAuditObjectRepository itAuditObjectRepository;

	@Override
	protected BasePersistenceDao<ItAuditObject, Long> getPersistenceDao() {
		return this.itAuditObjectRepository;
	}
	
	public List<ItAuditObject> findByCreateTime(Long managerUserId, ObjectStatus status, DateTime startTime, DateTime endTime){
		return this.itAuditObjectRepository.findByManagerUserIdAndStatusAndCreateTimeBetweenOrderByCreateTimeDesc(managerUserId, status, startTime, endTime);
	}
	
	public Page<ItAuditObject> findByParam(PageRequest pageRequest, final String queryKey, final String queryValue, final Long userId){
		return this.itAuditObjectRepository.findAll(new Specification<ItAuditObject>() {
			@Override
			public Predicate toPredicate(Root<ItAuditObject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
				Path<Long> managerUserId = root.get("managerUserId");
				predicates.add(cb.equal(managerUserId, userId));
				
	            if (predicates.size() > 0) {  
	                return cb.and(predicates.toArray(new Predicate[predicates.size()]));  
	            }
				return cb.conjunction();
			}
		}, pageRequest);
	}
}

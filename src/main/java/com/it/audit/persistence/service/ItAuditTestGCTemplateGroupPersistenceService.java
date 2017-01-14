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

import com.it.audit.domain.ItAuditTestGCTemplateGroup;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditTestGCTemplateGroupRepository;

@Service
public class ItAuditTestGCTemplateGroupPersistenceService extends BasePersistenceService<ItAuditTestGCTemplateGroup, Long> {
	
	@Autowired
	private ItAuditTestGCTemplateGroupRepository itAuditTestGCTemplateGroupRepository;

	@Override
	protected BasePersistenceDao<ItAuditTestGCTemplateGroup, Long> getPersistenceDao() {
		return this.itAuditTestGCTemplateGroupRepository;
	}
	
	public Page<ItAuditTestGCTemplateGroup> findByParam(PageRequest pageRequest, final String queryKey, final String queryValue){
		return this.itAuditTestGCTemplateGroupRepository.findAll(new Specification<ItAuditTestGCTemplateGroup>() {
			@Override
			public Predicate toPredicate(Root<ItAuditTestGCTemplateGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(!StringUtils.isEmpty(queryKey)){
					if("name".equals(queryKey)){
						Path<String> name = root.get("name");
						predicates.add(cb.like(name, "%" + queryValue +"%"));
					}
				}
	            if (predicates.size() > 0) {  
	                return cb.and(predicates.toArray(new Predicate[predicates.size()]));  
	            }
				return cb.conjunction();
			}
		}, pageRequest);
	}
}

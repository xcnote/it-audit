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

import com.it.audit.domain.ItAuditTestGCTemplate;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditTestGCTemplateRepository;

@Service
public class ItAuditTestGCTemplatePersistenceService extends BasePersistenceService<ItAuditTestGCTemplate, Long> {
	
	@Autowired
	private ItAuditTestGCTemplateRepository itAuditTestGCTemplateRepository;

	@Override
	protected BasePersistenceDao<ItAuditTestGCTemplate, Long> getPersistenceDao() {
		return this.itAuditTestGCTemplateRepository;
	}
	
	public List<ItAuditTestGCTemplate> findByGroupId(Long groupId){
		return this.itAuditTestGCTemplateRepository.findByGroupId(groupId);
	}
	
	public Page<ItAuditTestGCTemplate> findByParam(PageRequest pageRequest, final String queryKey, final String queryValue, final Long groupId){
		return this.itAuditTestGCTemplateRepository.findAll(new Specification<ItAuditTestGCTemplate>() {
			@Override
			public Predicate toPredicate(Root<ItAuditTestGCTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(!StringUtils.isEmpty(queryKey)){
					if("firstRegion".equals(queryKey)){
						Path<String> name = root.get("firstRegion");
						predicates.add(cb.like(name, "%" + queryValue +"%"));
					}
					if("secondRegion".equals(queryKey)){
						Path<String> name = root.get("secondRegion");
						predicates.add(cb.like(name, "%" + queryValue +"%"));
					}
				}
				if(groupId != null){
					Path<Long> group = root.get("groupId");
					predicates.add(cb.equal(group, groupId));
				}
	            if (predicates.size() > 0) {  
	                return cb.and(predicates.toArray(new Predicate[predicates.size()]));  
	            }
				return cb.conjunction();
			}
		}, pageRequest);
	}
}

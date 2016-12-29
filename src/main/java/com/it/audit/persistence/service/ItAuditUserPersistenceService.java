package com.it.audit.persistence.service;

import java.util.ArrayList;
import java.util.Collection;
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

import com.it.audit.domain.ItAuditUser;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditUserRepository;

@Service
public class ItAuditUserPersistenceService extends BasePersistenceService<ItAuditUser, Long> {
	
	@Autowired
	private ItAuditUserRepository itAuditUserRepository;

	@Override
	protected BasePersistenceDao<ItAuditUser, Long> getPersistenceDao() {
		return this.itAuditUserRepository;
	}

	public ItAuditUser findByLoginNameAndPassword(String loginName, String password){
		return this.itAuditUserRepository.findByLoginNameAndPassword(loginName, password);
	}
	
	public ItAuditUser findByUserId(Long userId){
		return this.itAuditUserRepository.findByUserId(userId);
	}
	
	public ItAuditUser findByUserIds(Collection<Long> userIds){
		return this.itAuditUserRepository.findByUserIdIn(userIds);
	}

	public ItAuditUser findByLoginName(String loginName) {
		return this.itAuditUserRepository.findByLoginName(loginName);
	}
	
	public Page<ItAuditUser> findByParam(PageRequest pageRequest, final String queryKey, final String queryValue){
		return this.itAuditUserRepository.findAll(new Specification<ItAuditUser>() {
			@Override
			public Predicate toPredicate(Root<ItAuditUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(!StringUtils.isEmpty(queryKey)){
					if("userName".equals(queryKey)){
						Path<String> userName = root.get("userName");
						predicates.add(cb.like(userName, "%" + queryValue +"%"));
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

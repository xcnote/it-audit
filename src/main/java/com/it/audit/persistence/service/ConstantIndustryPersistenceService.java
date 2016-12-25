package com.it.audit.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ConstantIndustry;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ConstantIndustryRepository;

@Service
public class ConstantIndustryPersistenceService extends BasePersistenceService<ConstantIndustry, Long> {
	
	@Autowired
	private ConstantIndustryRepository constantIndustryRepository;

	@Override
	protected BasePersistenceDao<ConstantIndustry, Long> getPersistenceDao() {
		return this.constantIndustryRepository;
	}
}

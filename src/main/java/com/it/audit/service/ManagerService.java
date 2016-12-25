package com.it.audit.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.persistence.service.ConstantIndustryPersistenceService;

@Service
public class ManagerService {

	@Autowired
	private ConstantIndustryPersistenceService industryPersistenceService;
	
	public Map<String, Object> buildCreateParam(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("industrys", this.industryPersistenceService.findAll());
		return param;
	}
}

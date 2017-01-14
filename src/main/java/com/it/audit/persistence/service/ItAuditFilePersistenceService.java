package com.it.audit.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditFile;
import com.it.audit.enums.FileType;
import com.it.audit.persistence.base.BasePersistenceDao;
import com.it.audit.persistence.base.BasePersistenceService;
import com.it.audit.persistence.dao.ItAuditFileRepository;

@Service
public class ItAuditFilePersistenceService extends BasePersistenceService<ItAuditFile, Long> {
	
	@Autowired
	private ItAuditFileRepository itAuditFileRepository;

	@Override
	protected BasePersistenceDao<ItAuditFile, Long> getPersistenceDao() {
		return this.itAuditFileRepository;
	}
	
	public List<ItAuditFile> findByObjectIdAndType(Long objectId, FileType type) {
		return this.itAuditFileRepository.findByObjectIdAndType(objectId, type);
	}
	
	public List<ItAuditFile> findByTestIdAndType(Long testId, FileType type) {
		return this.itAuditFileRepository.findByTestIdAndType(testId, type);
	}
	
	public List<ItAuditFile> findByType(FileType type) {
		return this.itAuditFileRepository.findByType(type);
	}
}

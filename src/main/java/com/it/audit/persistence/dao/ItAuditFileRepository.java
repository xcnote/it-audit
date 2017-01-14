package com.it.audit.persistence.dao;

import java.util.List;

import com.it.audit.domain.ItAuditFile;
import com.it.audit.enums.FileType;
import com.it.audit.persistence.base.BasePersistenceDao;

public interface ItAuditFileRepository extends BasePersistenceDao<ItAuditFile, Long> {

	List<ItAuditFile> findByObjectIdAndType(Long objectId, FileType type);

	List<ItAuditFile> findByTestIdAndType(Long testId, FileType type);

	List<ItAuditFile> findByType(FileType type);
}

package com.it.audit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditFile;
import com.it.audit.enums.FileType;

@Service
public class ObjectService {
	
	@Autowired
	private FileService fileService;

	/**
	 * 获取项目文件
	 * @param id
	 * @return
	 */
	public Map<String, Object> getObjectFile(Long id){
		List<ItAuditFile> reportFiles = this.fileService.findFileByTypeAndId(id, FileType.report);
		List<ItAuditFile> problemFiles = this.fileService.findFileByTypeAndId(id, FileType.problem);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("reportfile", CollectionUtils.isEmpty(reportFiles)? null: reportFiles.get(0));
		result.put("problemfile", CollectionUtils.isEmpty(problemFiles)? null: problemFiles.get(0));
		return result;
	}
}

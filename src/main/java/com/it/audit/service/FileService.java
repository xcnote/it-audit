package com.it.audit.service;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.it.audit.domain.ItAuditFile;
import com.it.audit.domain.ItAuditObject;
import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.enums.FileType;
import com.it.audit.exception.FileException;
import com.it.audit.exception.NotFoundException;
import com.it.audit.persistence.service.ItAuditFilePersistenceService;
import com.it.audit.persistence.service.ItAuditObjectPersistenceService;
import com.it.audit.util.CommonUtil;
import com.it.audit.util.FileUtils;

@Component
public class FileService {
	
	@Autowired
	private ItAuditFilePersistenceService itAuditFilePersistenceService;
	@Autowired
	private ItAuditObjectPersistenceService itAuditObjectPersistenceService;
	@Autowired
	private ObjectTestGCService objectTestGCService;
	@Autowired
	private ObjectTestACService objectTestACService;
	@Autowired
	private ObjectTestDAService objectTestDAService;
	

	/**
	 * 文件上传
	 * @param objectId
	 * @param testId
	 * @param type
	 * @param file
	 */
	@Transactional
	public ItAuditFile fileUpload(Long objectId, Long testId, FileType type, MultipartFile file){
		String filePath = type.toString();
		String fileName = UUID.randomUUID().toString();
		
		ItAuditFile itAuditFile = new ItAuditFile();
		itAuditFile.setObjectId(objectId);
		itAuditFile.setTestId(testId);
		itAuditFile.setName(file.getOriginalFilename());
		itAuditFile.setFilePath(filePath);
		itAuditFile.setFileName(fileName);
		itAuditFile.setType(type);
		switch (type) {
		case gctest:
			ItAuditTestGC gc = this.objectTestGCService.queryById(testId);
			CommonUtil.checkAndThrowAssignException(gc != null, new NotFoundException("未找到指定测试记录"));
			break;
		case actest:
			ItAuditTestAC ac = this.objectTestACService.queryById(testId);
			CommonUtil.checkAndThrowAssignException(ac != null, new NotFoundException("未找到指定测试记录"));
			break;
		case datest:
			ItAuditTestDA da = this.objectTestDAService.queryById(testId);
			CommonUtil.checkAndThrowAssignException(da != null, new NotFoundException("未找到指定测试记录"));
			break;
		case normreport:
			break;
		case report:
		case problem:
			ItAuditObject object = this.itAuditObjectPersistenceService.findById(objectId);
			CommonUtil.checkAndThrowAssignException(object != null, new NotFoundException("未找到指定项目"));
			
			List<ItAuditFile> oldFiles = this.itAuditFilePersistenceService.findByObjectIdAndType(objectId, type);
			if(CollectionUtils.isNotEmpty(oldFiles)){
				for(ItAuditFile oldFile: oldFiles){
					FileUtils.fileDelete(oldFile.getFilePath(), oldFile.getFileName());
				}
				this.itAuditFilePersistenceService.delete(oldFiles);
			}
			break;
		default:
			CommonUtil.checkAndThrowAssignException(false, new NotFoundException("未知上传"));
			break;
		}
		try {
			FileUtils.writeFile(filePath, fileName, file.getInputStream());
			return this.itAuditFilePersistenceService.save(itAuditFile);
		} catch (Exception e) {
			throw new FileException("文件上传失败。");
		}
	}
	
	/**
	 * 文件下载
	 * @param id
	 * @param type
	 * @param response
	 */
	public void fileDown(Long id, Long fileId, FileType type, HttpServletRequest request, HttpServletResponse response){
		ItAuditFile file = null;
		switch (type) {
		case gctest:
			ItAuditTestGC gc = this.objectTestGCService.queryById(id);
			CommonUtil.checkAndThrowAssignException(gc != null, new NotFoundException("未找到指定测试记录"));
			file = this.itAuditFilePersistenceService.findById(fileId);
			break;
		case actest:
			ItAuditTestAC ac = this.objectTestACService.queryById(id);
			CommonUtil.checkAndThrowAssignException(ac != null, new NotFoundException("未找到指定测试记录"));
			file = this.itAuditFilePersistenceService.findById(fileId);
			break;
		case datest:
			ItAuditTestDA da = this.objectTestDAService.queryById(id);
			CommonUtil.checkAndThrowAssignException(da != null, new NotFoundException("未找到指定测试记录"));
			file = this.itAuditFilePersistenceService.findById(fileId);
			break;
		case normreport:
			List<ItAuditFile> normalfiles = this.itAuditFilePersistenceService.findByType(type);
			if(CollectionUtils.isNotEmpty(normalfiles)){
				file = normalfiles.get(0);
			}
			break;
		case report:
		case problem:
			ItAuditObject object = this.itAuditObjectPersistenceService.findById(id);
			CommonUtil.checkAndThrowAssignException(object != null, new NotFoundException("未找到指定项目"));
			
			List<ItAuditFile> files = this.itAuditFilePersistenceService.findByObjectIdAndType(id, type);
			if(CollectionUtils.isNotEmpty(files)){
				file = files.get(0);
			}
			break;
		default:
			CommonUtil.checkAndThrowAssignException(false, new NotFoundException("未知上传"));
			break;
		}
		CommonUtil.checkAndThrowAssignException(file != null, new NotFoundException("未找到指定文件"));
		FileUtils.fileExport(file.getFilePath(), file.getFileName(), file.getName(), request, response);
	}
	
	/**
	 * 查询指定项目的上传文件列表
	 * @param id
	 * @param type
	 * @return
	 */
	public List<ItAuditFile> findFileByTypeAndId(Long id, FileType type){
		List<ItAuditFile> files = null;
		switch (type) {
		case gctest:
			ItAuditTestGC gc = this.objectTestGCService.queryById(id);
			CommonUtil.checkAndThrowAssignException(gc != null, new NotFoundException("未找到指定测试记录"));
			files = this.itAuditFilePersistenceService.findByTestIdAndType(id, type);
			break;
		case actest:
			ItAuditTestAC ac = this.objectTestACService.queryById(id);
			CommonUtil.checkAndThrowAssignException(ac != null, new NotFoundException("未找到指定测试记录"));
			files = this.itAuditFilePersistenceService.findByTestIdAndType(id, type);
			break;
		case datest:
			ItAuditTestDA da = this.objectTestDAService.queryById(id);
			CommonUtil.checkAndThrowAssignException(da != null, new NotFoundException("未找到指定测试记录"));
			files = this.itAuditFilePersistenceService.findByTestIdAndType(id, type);
			break;
		case normreport:
			files = this.itAuditFilePersistenceService.findByType(type);
			break;
		case report:
		case problem:
			ItAuditObject object = this.itAuditObjectPersistenceService.findById(id);
			CommonUtil.checkAndThrowAssignException(object != null, new NotFoundException("未找到指定项目"));
			
			files = this.itAuditFilePersistenceService.findByObjectIdAndType(id, type);
			break;
		default:
			CommonUtil.checkAndThrowAssignException(false, new NotFoundException("未知上传"));
			break;
		}
		return files;
	}
	
	/**
	 * 文件删除
	 * @param id
	 */
	public void fileDelete(FileType type, Long id){
		List<ItAuditFile> files = this.findFileByTypeAndId(id, type);
		if(CollectionUtils.isEmpty(files)){
			return;
		}
		for(ItAuditFile file: files){
			FileUtils.fileDelete(file.getFilePath(), file.getFileName());
			this.itAuditFilePersistenceService.delete(file);
		}
	}
}

package com.it.audit.web.page;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.audit.domain.ItAuditFile;
import com.it.audit.enums.FileType;
import com.it.audit.exception.ExcelException;
import com.it.audit.service.FileService;
import com.it.audit.service.ObjectTestExcelService;
import com.it.audit.web.constants.RequestURI;
import com.it.audit.web.dto.ResponesBase;

/**
 * 公共视图
 * @author wangx
 *
 */
@Controller
public class CommonViewController {
	
	@Autowired
	private ObjectTestExcelService objectTestExcelService;
	@Autowired
	private FileService fileService;

	/**
	 * GC数据表导出
	 * @return
	 * @throws ExcelException 
	 */
	@RequestMapping(value = RequestURI.COMMON_TEST_GC_EXPORT, method = RequestMethod.GET)
	public void gcExport(HttpServletRequest request, HttpServletResponse response, @RequestParam Long objectId) throws ExcelException{
		this.objectTestExcelService.testGCExport(objectId, request, response);
	}
	
	/**
	 * AC数据表导出
	 * @return
	 * @throws ExcelException 
	 */
	@RequestMapping(value = RequestURI.COMMON_TEST_AC_EXPORT, method = RequestMethod.GET)
	public void acExport(HttpServletRequest request, HttpServletResponse response, @RequestParam Long objectId) throws ExcelException{
		this.objectTestExcelService.testACExport(objectId, request, response);
	}
	
	/**
	 * DA数据表导出
	 * @return
	 * @throws ExcelException 
	 */
	@RequestMapping(value = RequestURI.COMMON_TEST_DA_EXPORT, method = RequestMethod.GET)
	public void daExport(HttpServletRequest request, HttpServletResponse response, @RequestParam Long objectId) throws ExcelException{
		this.objectTestExcelService.testDAExport(objectId, request, response);
	}
	
	/**
	 * 统计分析导出
	 * @return
	 * @throws ExcelException 
	 */
	@RequestMapping(value = RequestURI.COMMON_TEST_REPORT_EXPORT, method = RequestMethod.GET)
	public void reportExport(HttpServletRequest request, HttpServletResponse response, @RequestParam Long objectId) throws ExcelException{
		this.objectTestExcelService.statisticAnaly(objectId, request, response);
	}
	
	/**
	 * 统计分析导出
	 * @return
	 * @throws ExcelException 
	 */
	@RequestMapping(value = RequestURI.COMMON_TEST_PROBLEM_EXPORT, method = RequestMethod.GET)
	public void problemExport(HttpServletRequest request, HttpServletResponse response, @RequestParam Long objectId) throws ExcelException{
		this.objectTestExcelService.testProblemListExport(objectId, request, response);
	}
	
	/**
	 * 文件上传
	 * @param type
	 * @param objectId
	 * @param testId
	 * @param file
	 * @return
	 */
	@RequestMapping(value = RequestURI.COMMON_FILE_UPLOAD, method=RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<ResponesBase> fileUpload(@PathVariable("type") FileType type, @RequestParam(required=false) Long objectId, @RequestParam(required=false) Long testId, @RequestParam("file") MultipartFile file){    
		ItAuditFile itAuditFile = this.fileService.fileUpload(objectId, testId, type, file);
		Map<String, Object> result = new HashMap<>();
		result.put("id", itAuditFile.getId());
		result.put("name", itAuditFile.getName());
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "上传成功", null, result), HttpStatus.OK);
    }
	
	/**
	 * 文件下载
	 * @param type
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = RequestURI.COMMON_FILE_DOWN, method=RequestMethod.GET)
    public void fileDown(@PathVariable("type") FileType type, @RequestParam Long id, @RequestParam(required=false) Long fileId, HttpServletRequest request, HttpServletResponse response){    
		this.fileService.fileDown(id, fileId, type, request, response);
    }
	
	/**
	 * 文件删除
	 * @return
	 */
	@RequestMapping(value = RequestURI.COMMON_FILE_DELETE, method=RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<ResponesBase> fileDelete(@PathVariable("type") FileType type, @RequestParam Long id){    
		this.fileService.fileDelete(type, id);
		return new ResponseEntity<ResponesBase>(new ResponesBase(0, "删除成功"), HttpStatus.OK);
    }
}

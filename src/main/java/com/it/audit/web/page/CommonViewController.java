package com.it.audit.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.audit.exception.ExcelException;
import com.it.audit.service.ObjectTestExcelService;
import com.it.audit.web.constants.RequestURI;

/**
 * 公共视图
 * @author wangx
 *
 */
@Controller
public class CommonViewController {
	
	@Autowired
	private ObjectTestExcelService objectTestExcelService;

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
}

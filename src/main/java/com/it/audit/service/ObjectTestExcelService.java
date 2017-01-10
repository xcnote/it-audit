package com.it.audit.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.audit.domain.ItAuditObject;
import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.domain.ItAuditUser;
import com.it.audit.enums.TestImperfectionType;
import com.it.audit.exception.ExcelException;
import com.it.audit.util.DateUtils;
import com.it.audit.util.ExcelDataBean;
import com.it.audit.util.ExcelExportUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ObjectTestExcelService {
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectTestGCService objectTestGCService;
	@Autowired
	private ObjectTestACService objectTestACService;
	@Autowired
	private ObjectTestDAService objectTestDAService;
	
	private static final String Date_Format_Pattern = "yyyy-MM-dd HH:mm:ss";
	
	private static final String[] gcFields = new String[]{"测试编号", "风险编号", "IT风险描述", "控制活动", "一级控制域", "二级控制域", "风险初步评价", "监管指引", "审计程序", "访谈对象", "交叉访谈对象", "制度文件", "D&I有效性测试记录", "D&I有效性结论", "OE是否测试", "OE测试样本数量", "OE测试无效样本数量", "OE测试记录", "OE测试结论", "测试人", "最后提交日期", "经理意见", "缺陷评价", "经理批注", "状态", "创建时间"};
	private static final String[] gcFieldKeys = new String[]{"number", "riskNumber", "riskDesc", "controlActivity", "firstRegion", "secondRegion", "riskEstimate", "supervise", "audit", "interview", "crossInterview", "regimeFile", "diRecord", "diResult", "oe", "oeTestNum", "oeInvalidNum", "oeRecord", "oeResult", "testUser", "submitTime", "managerOpinion", "imperfection", "managerPostil", "status", "createTime"};
	
	private static final String[] acFields = new String[]{"测试编号", "风险描述", "AC测试内容", "AC测试方法", "差异金额", "样本金额", "AC测试记录", "AC测试结论", "差异比例", "测试人", "最后提交日期", "经理意见", "缺陷评价", "经理批注", "状态", "创建时间"};
	private static final String[] acFieldKeys = new String[]{"number", "riskDesc", "acContent", "acMethod", "disAmount", "sampleAmount", "acRecord", "acResult", "disRatio", "testUser", "submitTime", "managerOpinion", "imperfection", "managerPostil", "status", "createTime"};
	
	private static final String[] daFields = new String[]{"测试编号", "风险描述", "DA测试内容", "DA测试方法", "问题样本金额", "样本金额", "DA测试记录", "DA测试结论", "问题金额比例", "测试人", "最后提交日期", "经理意见", "缺陷评价", "经理批注", "状态", "创建时间"};
	private static final String[] daFieldKeys = new String[]{"number", "riskDesc", "daContent", "daMethod", "problemAmount", "sampleAmount", "daRecord", "daResult", "problemRatio", "testUser", "submitTime", "managerOpinion", "imperfection", "managerPostil", "status", "createTime"};
	
	private static final String[] gcReportFields = new String[]{"一级控制域", "控制点总数", "问题总数", "问题占比", "重大缺陷", "重要缺陷", "一般缺陷"};
	private static final String[] gcReportFieldKeys = new String[]{"firstRegion", "total", "problemTotal", "problemRatio", "serious", "important", "ordinary"};
	private static final String[] acReportFields = new String[]{"问题评价", "样本差异金额总计", "样本金额总计", "差异比例"};
	private static final String[] acReportFieldKeys = new String[]{"comment", "disAmountTotal", "sampleAmountTotal", "disRatio"};
	private static final String[] daReportFields = new String[]{"问题评价", "异常样本金额总计", "样本金额总计", "异常样本比例"};
	private static final String[] daReportFieldKeys = new String[]{"comment", "problemAmountTotal", "sampleAmountTotal", "problemRatio"};

	private static final String[] problemFields = new String[]{"测试类型", "风险描述", "一级控制域", "二级控制域", "缺陷评价", "D&I有效性测试记录", "OE测试记录", "AC测试记录", "DA测试记录"};
	private static final String[] problemFieldKeys = new String[]{"type", "riskDesc", "firstRegion", "secondRegion", "imperfection", "diRecord", "oeRecord", "acRecord", "daRecord"};
	
	/**
	 * GC 底表导出
	 * @param objectId
	 * @param request
	 * @param response
	 * @throws ExcelException
	 */
	public void testGCExport(Long objectId, HttpServletRequest request, HttpServletResponse response) throws ExcelException{
		Map<Long, ItAuditUser> allUser = userService.queryAllToMap();
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		List<ItAuditTestGC> all = this.objectTestGCService.queryTestGCAllList(objectId);
		
		log.info("request gc data export to excel.");
		List<ExcelDataBean> excelDatas = new ArrayList<ExcelDataBean>();
		ExcelDataBean dataBean = new ExcelDataBean("GC数据");
		dataBean.addDataFieldMap(gcFieldKeys, gcFields);
		for(int i=0; i<all.size(); i++){
			ItAuditTestGC gc = all.get(i);
			dataBean.addExcelDataMap(gcFieldKeys, new Object[]{
					judgeNull(gc.getNumber()), 
					judgeNull(gc.getRiskNumber()), 
					judgeNull(gc.getRiskDesc()), 
					judgeNull(gc.getControlActivity()), 
					judgeNull(gc.getFirstRegion()), 
					judgeNull(gc.getSecondRegion()), 
					judgeNull(gc.getRiskEstimate()), 
					judgeNull(gc.getSupervise()), 
					judgeNull(gc.getAudit()), 
					judgeNull(gc.getInterview()), 
					judgeNull(gc.getCrossInterview()), 
					judgeNull(gc.getRegimeFile()), 
					judgeNull(gc.getDiRecord()), 
					judgeNull(gc.getDiResult()), 
					gc.getOe() == null? "": gc.getOe()?"是":"否", 
					judgeNull(gc.getOeTestNum()), 
					judgeNull(gc.getOeInvalidNum()), 
					judgeNull(gc.getOeRecord()), 
					judgeNull(gc.getOeResult()), 
					gc.getTestUserId() == null? "" : allUser.get(gc.getTestUserId()) == null? "未知": allUser.get(gc.getTestUserId()).getUserName(), 
					gc.getSubmitTime() == null? "" : DateUtils.formatDate(gc.getSubmitTime().toDate(), Date_Format_Pattern), 
					judgeNull(gc.getManagerOpinionToChinese()), 
					gc.getImperfection() == null ? "": gc.getImperfection().getGctext(), 
					judgeNull(gc.getManagerPostil()), 
					gc.getStatus().getText(), 
					DateUtils.formatDate(gc.getCreateTime().toDate(), Date_Format_Pattern)});
		}
		excelDatas.add(dataBean);
		this.excelExport(excelDatas, object.getName() + "-GC数据表", request, response);
	}
	
	/**
	 * AC 底表导出
	 * @param objectId
	 * @param request
	 * @param response
	 * @throws ExcelException
	 */
	public void testACExport(Long objectId, HttpServletRequest request, HttpServletResponse response) throws ExcelException{
		Map<Long, ItAuditUser> allUser = userService.queryAllToMap();
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		List<ItAuditTestAC> all = this.objectTestACService.queryTestACAllList(objectId);
		
		log.info("request ac data export to excel.");
		List<ExcelDataBean> excelDatas = new ArrayList<ExcelDataBean>();
		ExcelDataBean dataBean = new ExcelDataBean("AC数据");
		dataBean.addDataFieldMap(acFieldKeys, acFields);
		for(int i=0; i<all.size(); i++){
			ItAuditTestAC ac = all.get(i);
			dataBean.addExcelDataMap(acFieldKeys, new Object[]{
					judgeNull(ac.getNumber()), 
					judgeNull(ac.getRiskDesc()), 
					judgeNull(ac.getAcContent()), 
					judgeNull(ac.getAcMethod()), 
					judgeNull(ac.getDisAmount()), 
					judgeNull(ac.getSampleAmount()), 
					judgeNull(ac.getAcRecord()), 
					judgeNull(ac.getAcResult()), 
					judgeNull(ac.getDisRatio()), 
					ac.getTestUserId() == null? "" : allUser.get(ac.getTestUserId()) == null? "未知": allUser.get(ac.getTestUserId()).getUserName(), 
					ac.getSubmitTime() == null? "" : DateUtils.formatDate(ac.getSubmitTime().toDate(), Date_Format_Pattern), 
					judgeNull(ac.getManagerOpinionToChinese()), 
					ac.getImperfection() == null ? "": ac.getImperfection().getGctext(), 
					judgeNull(ac.getManagerPostil()), 
					ac.getStatus().getText(), 
					DateUtils.formatDate(ac.getCreateTime().toDate(), Date_Format_Pattern)});
		}
		excelDatas.add(dataBean);
		this.excelExport(excelDatas, object.getName() + "-AC数据表", request, response);
	}
	
	/**
	 * DA 底表导出
	 * @param objectId
	 * @param request
	 * @param response
	 * @throws ExcelException
	 */
	public void testDAExport(Long objectId, HttpServletRequest request, HttpServletResponse response) throws ExcelException{
		Map<Long, ItAuditUser> allUser = userService.queryAllToMap();
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		List<ItAuditTestDA> all = this.objectTestDAService.queryTestDAAllList(objectId);
		
		log.info("request da data export to excel.");
		List<ExcelDataBean> excelDatas = new ArrayList<ExcelDataBean>();
		ExcelDataBean dataBean = new ExcelDataBean("DA数据");
		dataBean.addDataFieldMap(daFieldKeys, daFields);
		for(int i=0; i<all.size(); i++){
			ItAuditTestDA da = all.get(i);
			dataBean.addExcelDataMap(daFieldKeys, new Object[]{
					judgeNull(da.getNumber()), 
					judgeNull(da.getRiskDesc()), 
					judgeNull(da.getDaContent()), 
					judgeNull(da.getDaMethod()), 
					judgeNull(da.getProblemAmount()), 
					judgeNull(da.getSampleAmount()), 
					judgeNull(da.getDaRecord()), 
					judgeNull(da.getDaResult()), 
					judgeNull(da.getProblemRatio()), 
					da.getTestUserId() == null? "" : allUser.get(da.getTestUserId()) == null? "未知": allUser.get(da.getTestUserId()).getUserName(), 
					da.getSubmitTime() == null? "" : DateUtils.formatDate(da.getSubmitTime().toDate(), Date_Format_Pattern), 
					judgeNull(da.getManagerOpinionToChinese()), 
					da.getImperfection() == null ? "": da.getImperfection().getGctext(), 
					judgeNull(da.getManagerPostil()), 
					da.getStatus().getText(), 
					DateUtils.formatDate(da.getCreateTime().toDate(), Date_Format_Pattern)});
		}
		excelDatas.add(dataBean);
		this.excelExport(excelDatas, object.getName() + "-DA数据表", request, response);
	}
	
	/**
	 * 统计分析导出
	 * @param objectId
	 * @param request
	 * @param response
	 * @throws ExcelException
	 */
	public void statisticAnaly(Long objectId, HttpServletRequest request, HttpServletResponse response) throws ExcelException{
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		
		log.info("request report info data export to excel.");
		List<ExcelDataBean> excelDatas = new ArrayList<ExcelDataBean>();
		ExcelDataBean gcDataBean = new ExcelDataBean("GC测试统计分析");
		ExcelDataBean acDataBean = new ExcelDataBean("AC测试统计分析");
		ExcelDataBean daDataBean = new ExcelDataBean("DA测试统计分析");
		
		gcDataBean.addDataFieldMap(gcReportFieldKeys, gcReportFields);
		acDataBean.addDataFieldMap(acReportFieldKeys, acReportFields);
		daDataBean.addDataFieldMap(daReportFieldKeys, daReportFields);
		
		gcDataBean.addExcelDataMap(gcReportFieldKeys, new Object[]{"信息科技治理","4","2","50%","0","0","2"});
		acDataBean.addExcelDataMap(acReportFieldKeys, new Object[]{"存在可接受的合理差异","1000","950000000","1.1%"});
		daDataBean.addExcelDataMap(daReportFieldKeys, new Object[]{"发现一般异常情况","1000","950000000","1.1%"});
		
		excelDatas.add(gcDataBean);
		excelDatas.add(acDataBean);
		excelDatas.add(daDataBean);
		this.excelExport(excelDatas, object.getName() + "-统计分析", request, response);
	}
	
	/**
	 * 标准问题清单导出
	 * @param objectId
	 * @param request
	 * @param response
	 * @throws ExcelException
	 */
	public void testProblemListExport(Long objectId, HttpServletRequest request, HttpServletResponse response) throws ExcelException{
		List<TestImperfectionType> gcImperfections = Arrays.asList(new TestImperfectionType[]{TestImperfectionType.serious, TestImperfectionType.important, TestImperfectionType.ordinary});
		List<TestImperfectionType> acImperfections = Arrays.asList(new TestImperfectionType[]{TestImperfectionType.important, TestImperfectionType.ordinary});
		List<TestImperfectionType> daImperfections = Arrays.asList(new TestImperfectionType[]{TestImperfectionType.important, TestImperfectionType.ordinary});
		
		ItAuditObject object = this.managerService.queryObjectDetail(objectId);
		List<ItAuditTestGC> gcProblemList = this.objectTestGCService.queryTestGCAllListByImperfections(objectId, gcImperfections);
		List<ItAuditTestAC> acProblemList = this.objectTestACService.queryTestACAllListByImperfections(objectId, acImperfections);
		List<ItAuditTestDA> daProblemList = this.objectTestDAService.queryTestDAAllListByImperfections(objectId, daImperfections);
		
		
		log.info("request da data export to excel.");
		List<ExcelDataBean> excelDatas = new ArrayList<ExcelDataBean>();
		ExcelDataBean dataBean = new ExcelDataBean("标准问题数据");
		dataBean.addDataFieldMap(problemFieldKeys, problemFields);
		if(gcProblemList != null){
			for(ItAuditTestGC gc: gcProblemList){
				dataBean.addExcelDataMap(problemFieldKeys, new Object[]{
						"一般控制性测试（GC）", 
						judgeNull(gc.getRiskDesc()),
						judgeNull(gc.getFirstRegion()),
						judgeNull(gc.getSecondRegion()),
						gc.getImperfection() == null ? "": gc.getImperfection().getGctext(),
						judgeNull(gc.getDiRecord()),
						judgeNull(gc.getOeRecord()),
						"N/A", "N/A"});
			}
		}
		if(acProblemList != null){
			for(ItAuditTestAC ac: acProblemList){
				dataBean.addExcelDataMap(problemFieldKeys, new Object[]{
						"自动化控制测试（AC）", 
						judgeNull(ac.getRiskDesc()),
						"N/A", "N/A",
						ac.getImperfection() == null ? "": ac.getImperfection().getGctext(),
						"N/A", "N/A",
						judgeNull(ac.getAcRecord()), 
						"N/A"});
			}
		}
		if(daProblemList != null){
			for(ItAuditTestDA da: daProblemList){
				dataBean.addExcelDataMap(problemFieldKeys, new Object[]{
						"数据分析测试（DA）", 
						judgeNull(da.getRiskDesc()),
						"N/A", "N/A",
						da.getImperfection() == null ? "": da.getImperfection().getGctext(),
						"N/A", "N/A",
						"N/A", 
						judgeNull(da.getDaRecord())});
			}
		}
		excelDatas.add(dataBean);
		this.excelExport(excelDatas, object.getName() + "-标准问题清单", request, response);
	}
	
	public void excelExport(List<ExcelDataBean> excelDatas, String fileName, HttpServletRequest request, HttpServletResponse response) throws ExcelException {
		ExcelExportUtils.exportToExcel(excelDatas, fileName, true, request, response);
	}
	
	public String judgeNull(Object obj) {
		if(obj == null){
			return "";
		}
		return obj.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(acFields.length);
		System.out.println(acFieldKeys.length);
	}
}

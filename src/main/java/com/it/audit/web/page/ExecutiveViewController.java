package com.it.audit.web.page;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.it.audit.domain.ItAuditObject;
import com.it.audit.service.ExecutiveService;
import com.it.audit.service.UserService;
import com.it.audit.util.CommonUtil;
import com.it.audit.web.constants.RequestURI;

/**
 * 高管视图
 * @author wangx
 *
 */
@Controller
public class ExecutiveViewController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ExecutiveService executiveService;

	/**
	 * 主页
	 * @return
	 */
	@RequestMapping(value = RequestURI.EXECUTIVE_INDEX, method = RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView("executive/manage/index");
	}
	
	/**
	 * 项目进展
	 * @return
	 */
	@RequestMapping(value = RequestURI.EXECUTIVE_OBJECT, method = RequestMethod.GET)
	public ModelAndView objectInfo(){
		return new ModelAndView("executive/manage/object");
	}
	
	/**
	 * 按行业分析
	 * @return
	 */
	@RequestMapping(value = RequestURI.EXECUTIVE_INDUSTRY, method = RequestMethod.GET)
	public ModelAndView industryInfo(){
		return new ModelAndView("executive/manage/industry");
	}
	
	/**
	 * 按规模分析
	 * @return
	 */
	@RequestMapping(value = RequestURI.EXECUTIVE_SCALE, method = RequestMethod.GET)
	public ModelAndView scaleInfo(){
		return new ModelAndView("executive/manage/scale");
	}
	
	/**
	 * 项目报告列表
	 * @return
	 */
	@RequestMapping(value = RequestURI.EXECUTIVE_REPORT_LIST, method = RequestMethod.GET)
	public ModelAndView objectReportPage(Integer page, @RequestParam(required=false) String queryKey, @RequestParam(required=false) String queryValue){
		Page<ItAuditObject> objectList = this.executiveService.queryObjectPage(new PageRequest(page, 10, new Sort(Direction.DESC, "createTime")), queryKey, queryValue);
		Map<String, Object> result = CommonUtil.buildQueryResult(queryKey, queryValue);
		result.putAll(CommonUtil.buildPageParam(objectList, 7));
		result.put("userMap", this.userService.queryAllToMap());
		return new ModelAndView("executive/reportlist", result);
	}
	
	/**
	 * 项目统计分析
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.EXECUTIVE_REPORT_INFO, method = RequestMethod.GET)
	public ModelAndView reportInfo(@RequestParam Long objectId){
		ItAuditObject object = this.executiveService.queryObjectDetail(objectId);
		return new ModelAndView("executive/report/info", "info", object);
	}
	
	/**
	 * 项目报告列表
	 * @param page
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value = RequestURI.EXECUTIVE_REPORT_DOWN, method = RequestMethod.GET)
	public ModelAndView reportDown(@RequestParam Long objectId){
		ItAuditObject object = this.executiveService.queryObjectDetail(objectId);
		return new ModelAndView("executive/report/down", "info", object);
	}
}

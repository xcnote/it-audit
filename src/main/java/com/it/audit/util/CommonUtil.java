package com.it.audit.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.it.audit.enums.ObjectTestStatus;

public class CommonUtil {
	
	public static void checkAndThrowAssignException(boolean check, RuntimeException ex) throws RuntimeException{
		if(!check){
			throw ex;
		}
	}
	
	public static Map<String, Object> buildQueryResult(String queryKey, String queryName){
		Map<String, Object> result = new HashMap<>();
		result.put("queryKey", queryKey);
		result.put("queryValue", queryName);
		return result;
	}
	
	public static String managerOpinionToChinese(String managerOpinion){
		ObjectTestStatus status = ObjectTestStatus.updateObjectTestStatus(managerOpinion);
		if(status != null){
			return status.getText();
		}
		return null;
	}
	
	/**
	 * 
	 * @param page
	 * @param showPageSize 必须为奇数
	 * @return
	 */
	public static Map<String, Object> buildPageParam(Page<?> page, int showPageSize){
		int number = page.getNumber() + 1;
		int total = page.getTotalPages();
		
		int half = (showPageSize-1)/2;
		int showStart = 1;
		int showEnd = showPageSize;
		if(total == 0){
			showEnd = 1;
		} else if(total > showPageSize){
			if(number > (half+1)){
				if(total-number > half){
					showStart = number-half;
					showEnd = number+half;
				} else {
					showStart = total-showPageSize+1;
				}
			}
		} else {
			showEnd = total;
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("startpage", showStart);
		result.put("endpage", showEnd);
		result.put("page", page);
		return result; 
	}
}

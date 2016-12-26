package com.it.audit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author wangx
 *
 */
public class DateUtils {
	
	public static Date formatDate(String time, String pattern){
		if(StringUtils.isBlank(time)){
			return null;
		}
		
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date startTimeInYear(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		return formatDate(year+"-01-01", "yyyy-MM-dd");
	}
	
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(DateUtils.startTimeInYear()));
		System.out.println(DateUtils.startTimeInYear().getTime());
	}
}

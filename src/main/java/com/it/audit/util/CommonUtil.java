package com.it.audit.util;

public class CommonUtil {

	public static void checkAndThrowAssignException(boolean check, RuntimeException ex) throws RuntimeException{
		if(!check){
			throw ex;
		}
	}
}

package com.it.audit.common;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class LocalCache {

	private static final ConcurrentHashMap<String, Object> localCache = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, Long> localCacheTime = new ConcurrentHashMap<>();
	
	public <T> void set(String key, T value, long tt){
		
	}
}

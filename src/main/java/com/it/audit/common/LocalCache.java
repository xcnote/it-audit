package com.it.audit.common;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LocalCache {

	private static final ConcurrentHashMap<String, Object> localCache = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, Long> localCacheTime = new ConcurrentHashMap<>();
	
	/**
	 * 存入缓存
	 * @param key
	 * @param value
	 * @param tt second
	 */
	public void set(String key, Object value, Long tt){
		localCache.put(key, value);
		localCacheTime.put(key, new Date().getTime() + (tt*1000));
	}
	
	/**
	 * 获取缓存内容
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key){
		return (T) localCache.get(key);
	}
	
	/**
	 * 清理缓存
	 */
	public void cleanLocalCache(){
		if(localCache.isEmpty()){
			log.info("local cache is empty.");
			return;
		}
		long timestamp = new Date().getTime();
		for(String key: localCache.keySet()){
			if(localCacheTime.containsKey(key) && localCacheTime.get(key) > timestamp){
				continue;
			}
			localCache.remove(key);
			localCacheTime.remove(key);
			log.info("clean local cache - key {}.", key);
		}
	}
}

package com.it.audit.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.common.CommonInfo;
import com.it.audit.common.LocalCacheService;
import com.it.audit.domain.ItAuditUser;
import com.it.audit.enums.UserStatus;
import com.it.audit.exception.UserDisableException;
import com.it.audit.persistence.service.ItAuditUserPersistenceService;
import com.it.audit.util.CommonUtil;

@Service
public class UserService {
	
	@Autowired
	private ItAuditUserPersistenceService itAuditUserPersistenceService;
	@Autowired
	private LocalCacheService localCache;

	/**
	 * 用户登陆
	 * @param loginName
	 * @param password
	 * @return
	 */
	public String userLogin(String loginName, String password){
		ItAuditUser user = this.itAuditUserPersistenceService.findByLoginNameAndPassword(loginName, password);
		if(user != null) {
			CommonUtil.checkAndThrowAssignException(user.getStatus() != UserStatus.disable, new UserDisableException("用户已失效"));
			String token = UUID.randomUUID().toString();
			this.localCache.set(String.format(CommonInfo.LOCAL_CACHE_KEY_LOGIN_FORMAT, token), user.getUserId(), CommonInfo.LOCAL_CACHE_KEY_LOGIN_EXPIRE);
			this.localCache.set(String.format(CommonInfo.LOCAL_CACHE_KEY_USER_INFO, user.getUserId()), user, CommonInfo.LOCAL_CACHE_KEY_USER_INFO_EXPIRE);
			return token;
		}
		return null;
	}
	
	/**
	 * 登陆验证
	 * @param token
	 * @return
	 */
	public ItAuditUser queryUserByToken(String token){
		Long userId = this.localCache.get(String.format(CommonInfo.LOCAL_CACHE_KEY_LOGIN_FORMAT, token));
		ItAuditUser user = this.localCache.get(String.format(CommonInfo.LOCAL_CACHE_KEY_USER_INFO, userId));
		if(userId != null && user == null){
			user = this.itAuditUserPersistenceService.findByUserId(userId);
		}
		return user;
	}
	
	/**
	 * 登出
	 * @param token
	 * @param userId
	 */
	public void userLogout(String token, Long userId){
		this.localCache.delete(String.format(CommonInfo.LOCAL_CACHE_KEY_LOGIN_FORMAT, token));
		this.localCache.delete(String.format(CommonInfo.LOCAL_CACHE_KEY_USER_INFO, userId));
	}
	
	/**
	 * 查询用户分页
	 * @param pageRequest
	 * @return
	 */
	public Page<ItAuditUser> queryUserPage(PageRequest pageRequest){
		return this.itAuditUserPersistenceService.findPage(pageRequest);
	}
}

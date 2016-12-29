package com.it.audit.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.common.CommonInfo;
import com.it.audit.common.LocalCacheService;
import com.it.audit.domain.ItAuditUser;
import com.it.audit.enums.UserRole;
import com.it.audit.enums.UserStatus;
import com.it.audit.exception.UserDisableException;
import com.it.audit.persistence.service.ItAuditUserPersistenceService;
import com.it.audit.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private ItAuditUserPersistenceService itAuditUserPersistenceService;
	@Autowired
	private LocalCacheService localCache;
	
	private static final Long user_id_base = 1000000L;

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
	public Page<ItAuditUser> queryUserPage(PageRequest pageRequest, String queryKey, String queryValue){
		return this.itAuditUserPersistenceService.findByParam(pageRequest, queryKey, queryValue);
	}
	
	/**
	 * 检查登陆名是否存在
	 * @param loginName
	 * @return
	 */
	public boolean checkLoginName(String loginName){
		ItAuditUser user = this.itAuditUserPersistenceService.findByLoginName(loginName);
		return user == null;
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public String userCreate(ItAuditUser user){
		String loginName = user.getLoginName();
		boolean loginNameIsOk = this.checkLoginName(loginName);
		if(!loginNameIsOk){
			return "用户登录名已经存在，用户创建失败";
		}
		
		try{	
			DateTime time = new DateTime();
			user.setUserRole(UserRole.mergeRoles(user.getSourceRoles()));
			user.setCreateTime(time);
			user.setUpdateTime(time);
			ItAuditUser result = this.itAuditUserPersistenceService.save(user);
			result.setUserId(user_id_base + result.getId());
			this.itAuditUserPersistenceService.save(result);
		} catch (Exception e) {
			log.error("create user error. user:{}", user, e);
			return "未知错误，用户添加失败";
		}
		return null;
	}
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public String userUpdate(ItAuditUser user){
		ItAuditUser old = this.itAuditUserPersistenceService.findById(user.getId());
		if(old == null){
			return "未查询到该用户信息";
		}
		String loginName = user.getLoginName();
		if(!old.getLoginName().equals(loginName)){
			boolean loginNameIsOk = this.checkLoginName(loginName);
			if(!loginNameIsOk){
				return "用户登录名已经存在，用户创建失败";
			}
		}
		
		try{	
			DateTime time = new DateTime();
			user.setUserRole(UserRole.mergeRoles(user.getSourceRoles()));
			user.setUpdateTime(time);
			this.itAuditUserPersistenceService.save(user);
		} catch (Exception e) {
			log.error("create user error. user:{}", user, e);
			return "未知错误，用户更新失败";
		}
		return null;
	}
	
	public void userDelete(List<Long> ids){
		this.itAuditUserPersistenceService.delete(ids.toArray(new Long[]{}));
	}
	
	public ItAuditUser queryUserById(Long id){
		return this.itAuditUserPersistenceService.findById(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItAuditUser> queryUsersByUserIds(Collection<Long> userIds){
		return (List<ItAuditUser>) this.itAuditUserPersistenceService.findByUserIds(userIds);
	}
}

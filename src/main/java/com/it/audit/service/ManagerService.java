package com.it.audit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.it.audit.auth.AuthContextHolder;
import com.it.audit.domain.ItAuditObject;
import com.it.audit.domain.ItAuditObjectUser;
import com.it.audit.domain.ItAuditTestAC;
import com.it.audit.domain.ItAuditTestDA;
import com.it.audit.domain.ItAuditTestGC;
import com.it.audit.domain.ItAuditUser;
import com.it.audit.enums.ObjectStatus;
import com.it.audit.enums.ObjectUserRole;
import com.it.audit.persistence.service.ConstantIndustryPersistenceService;
import com.it.audit.persistence.service.ItAuditObjectPersistenceService;
import com.it.audit.persistence.service.ItAuditObjectUserPersistenceService;
import com.it.audit.util.DateUtils;
import com.it.audit.web.dto.ObjectCreate;
import com.it.audit.web.dto.ObjectUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagerService {

	@Autowired
	private ConstantIndustryPersistenceService industryPersistenceService;
	@Autowired
	private ItAuditObjectPersistenceService itAuditObjectPersistenceService;
	@Autowired
	private ItAuditObjectUserPersistenceService objectUserPersistenceService;
	@Autowired
	private ObjectTestGCService objectTestGCService;
	@Autowired
	private ObjectTestACService objectTestACService;
	@Autowired
	private ObjectTestDAService objectTestDAService;
	@Autowired
	private UserService userService;
	
	/**
	 * 项目经理主页数据
	 * @return
	 */
	public Map<String, List<ItAuditObject>> queryIndexData(){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		List<ItAuditObject> execObjects = this.itAuditObjectPersistenceService.findByCreateTime(userId, ObjectStatus.execute, new DateTime(DateUtils.startTimeInYear().getTime()), new DateTime());
		List<ItAuditObject> finishObjects = this.itAuditObjectPersistenceService.findByCreateTime(userId, ObjectStatus.finish, new DateTime(DateUtils.startTimeInYear().getTime()), new DateTime());
		Map<String, List<ItAuditObject>> result = new HashMap<String, List<ItAuditObject>>();
		result.put("exec", execObjects);
		result.put("finish", finishObjects);
		return result;
	}
	
	/**
	 * 创建项目页面所需数据
	 * @return
	 */
	public Map<String, Object> buildCreateParam(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("industrys", this.industryPersistenceService.findAll());
		return param;
	}
	
	/**
	 * 项目创建
	 * @param createInfo
	 * @return
	 */
	public boolean createObject(ObjectCreate createInfo){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		
		try {
			DateTime date = new DateTime();
			ItAuditObject object = createInfo.buildItAuditObject();
			object.setManagerUserId(userId);
			object.setObjectNumber(Long.toHexString(userId).toUpperCase()+Long.toHexString(new Date().getTime()/1000).toUpperCase());
			object.setCreateUserId(userId);
			object.setStatus(ObjectStatus.execute);
			object.setCreateTime(date);
			object.setReviseTime(date);
			this.itAuditObjectPersistenceService.save(object);
			return true;
		} catch (Exception e) {
			log.error("create Object error by userId:{}", userId, e);
		}
		return false;
	}
	
	/**
	 * 查询项目分页
	 * @param pageRequest
	 * @return
	 */
	public Page<ItAuditObject> queryObjectPage(PageRequest pageRequest, String queryKey, String queryValue){
		Long userId = AuthContextHolder.get().getUserInfo().getUserId();
		return this.itAuditObjectPersistenceService.findByParam(pageRequest, queryKey, queryValue, userId);
	}
	
	/**
	 * 查询项目详情
	 * @param id
	 * @return
	 */
	public ItAuditObject queryObjectDetail(Long objectId){
		return this.itAuditObjectPersistenceService.findById(objectId);
	}
	
	/**
	 * 查询项目成员
	 * @param objectId
	 * @return
	 */
	public List<ObjectUser> queryAllObjectUser(Long objectId){
		Map<Long, ItAuditUser> userId2ObjectUser = new HashMap<>();
		List<ObjectUser> result = new ArrayList<ObjectUser>();
		
		ItAuditObject object = this.queryObjectDetail(objectId);
		Long partnerUserId = object.getPartnerUserId();
		Long reviewUserId = object.getReviewUserId();
		if(partnerUserId != null){
			userId2ObjectUser.put(partnerUserId, null);
		}
		if(reviewUserId != null){
			userId2ObjectUser.put(reviewUserId, null);
		}
		
		List<ItAuditObjectUser> objectUsers = this.objectUserPersistenceService.findByObjectId(objectId);
		if(CollectionUtils.isNotEmpty(objectUsers)){
			for(ItAuditObjectUser objectUser: objectUsers){
				userId2ObjectUser.put(objectUser.getUserId(), null);
			}
		}
		if(userId2ObjectUser.isEmpty()){
			return null;
		}
		
		List<ItAuditUser> users = this.userService.queryUsersByUserIds(userId2ObjectUser.keySet());
		for(ItAuditUser user: users){
			userId2ObjectUser.put(user.getUserId(), user);
		}
		
		if(partnerUserId != null){
			result.add(new ObjectUser(null, partnerUserId, userId2ObjectUser.get(partnerUserId).getUserName(), objectId, ObjectUserRole.PARTNER, null));
		}
		if(reviewUserId != null){
			result.add(new ObjectUser(null, reviewUserId, userId2ObjectUser.get(reviewUserId).getUserName(), objectId, ObjectUserRole.REVIEW, null));
		}
		for(ItAuditObjectUser objectUser: objectUsers){
			result.add(new ObjectUser(objectUser, userId2ObjectUser.get(objectUser.getUserId())));
		}
		return result;
	}
	public List<ObjectUser> queryObjectUserByRole(Long objectId, ObjectUserRole role){
		Map<Long, ItAuditUser> userId2ObjectUser = new HashMap<>();
		List<ObjectUser> result = new ArrayList<ObjectUser>();
		List<ItAuditObjectUser> objectUsers = this.objectUserPersistenceService.findByObjectIdAndRole(objectId, role);
		if(CollectionUtils.isNotEmpty(objectUsers)){
			for(ItAuditObjectUser objectUser: objectUsers){
				userId2ObjectUser.put(objectUser.getUserId(), null);
			}
		}
		
		if(userId2ObjectUser.isEmpty()){
			return null;
		}
		
		List<ItAuditUser> users = this.userService.queryUsersByUserIds(userId2ObjectUser.keySet());
		for(ItAuditUser user: users){
			userId2ObjectUser.put(user.getUserId(), user);
		}
		for(ItAuditObjectUser objectUser: objectUsers){
			result.add(new ObjectUser(objectUser, userId2ObjectUser.get(objectUser.getUserId())));
		}
		return result;
	}
	
	/**
	 * 新增项目成员
	 * @param objectUser 经理人不可为项目合伙人，经理人不可为质量复核人
	 * @return 错误信息
	 */
	public String addObjectUser(ObjectUser objectUser){
		Long currUserId = AuthContextHolder.get().getUserInfo().getUserId();
		Long objectId = objectUser.getObjectId();
		List<ItAuditObjectUser> users = this.objectUserPersistenceService.findByObjectIdAndRole(objectId, ObjectUserRole.MANAGER);
		List<Long> managers = new ArrayList<>();
		managers.add(currUserId);
		if(CollectionUtils.isNotEmpty(users)){
			for(ItAuditObjectUser user: users){
				managers.add(user.getUserId());
			}
		}
		
		ObjectUserRole role = objectUser.getRole();
		Long userId = objectUser.getUserId();
		if(managers.contains(userId) && (role == ObjectUserRole.PARTNER || role == ObjectUserRole.REVIEW)){
			return "存在不相容权限，请重新分配";
		}
		ItAuditObjectUser itObjectUser = new ItAuditObjectUser();
		itObjectUser.setUserId(userId);
		itObjectUser.setRole(role);
		itObjectUser.setObjectId(objectId);
		this.objectUserPersistenceService.save(itObjectUser);
		return null;
	}
	
	/**
	 * 删除项目成员， 存在任务不执行
	 * @param ids
	 * @return
	 */
	public String deleteObjectUser(List<Long> ids){
		List<ItAuditObjectUser> users = this.objectUserPersistenceService.findByIdIn(ids);
		for(ItAuditObjectUser user: users){
			Long userId = user.getUserId();
			Long objectId = user.getObjectId();
			
			List<ItAuditTestGC> gcs = this.objectTestGCService.queryTestGCByTestUser(userId, objectId);
			List<ItAuditTestAC> acs = this.objectTestACService.queryTestACByTestUser(userId, objectId);
			List<ItAuditTestDA> das = this.objectTestDAService.queryTestDAByTestUser(userId, objectId);
			if(CollectionUtils.isNotEmpty(gcs) || CollectionUtils.isNotEmpty(acs) || CollectionUtils.isNotEmpty(das)){
				return this.userService.queryUserByUserId(userId).getUserName() + "存在已分配任务，请取消后重试";
			}
		}
		this.objectUserPersistenceService.delete(ids.toArray(new Long[]{}));
		return null;
	}
}

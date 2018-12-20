package com.goyo.project.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;
import com.goyo.project.core.ProjectConstant;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Organization;
import com.goyo.project.model.SysRole;
import com.goyo.project.model.UserInfo;
import com.goyo.project.organization.management.dao.UserInfoDao;
import com.goyo.project.role.management.dao.SysRoleDao;

public class CommonDataForUserInfo {

	//*********************************************用户-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private UserInfoDao userInfoDao;
	
	@Resource
	private SysRoleDao sysRoleDao; 

	public CommonDataForUserInfo() {
		super();
	}
	
	protected Result<List<UserInfo>> getUserInfosByOrganizationId(Integer organizationId){
		return ResultGenerator.genSuccessResult(userInfoDao.findByOrganizationId(organizationId));
	};

	/**
	 * 获取单个用户
	 * @param userInfo
	 * @return Result<UserInfo>
	 */
	protected Result<UserInfo> getUserSingle(UserInfo userInfo) {
		UserInfo userInfoDB = null;
		if(Optional.ofNullable(userInfo.getUid()).isPresent()){
			userInfoDB = userInfoDao.findById(userInfo.getUid()).get();
		}
		if(Optional.ofNullable(userInfo.getPhoneNumber()).isPresent()){
			userInfoDB = userInfoDao.findByPhoneNumber(userInfo.getPhoneNumber());
		}
		if(Optional.ofNullable(userInfo.getUsername()).isPresent()){
			userInfoDB = userInfoDao.findByUsername(userInfo.getUsername());
		}
		List<SysRole> roleListDB = userInfoDB.getRoleList();
		roleListDB = roleListDB.parallelStream().distinct().collect(Collectors.toList());
		userInfoDB.setRoleList(roleListDB);
		return ResultGenerator.genSuccessResult(userInfoDB);
	}
	/**
	 * 获取单个用户
	 * @param userInfo
	 * @return Result<UserInfo>
	 */
	protected UserInfo getUserSingle_(UserInfo userInfo) {
		UserInfo userInfoDB = null;
		if(Optional.ofNullable(userInfo.getUsername()).isPresent()){
			userInfoDB = userInfoDao.findByUsername(userInfo.getUsername());
		}
		if(Optional.ofNullable(userInfo.getPhoneNumber()).isPresent()){
			userInfoDB = userInfoDao.findByPhoneNumber(userInfo.getPhoneNumber());
		}
		if(Optional.ofNullable(userInfo.getUid()).isPresent()){
			userInfoDB = userInfoDao.findById(userInfo.getUid()).get();
		}
		return userInfoDB;
	}

	/**
	 * 获取分页用户列表
	 * @param userInfo
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<UserInfo>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<UserInfo>> getUserPage(UserInfo userInfo, String pageNum, String pageSize) {
		Page<UserInfo> page = null;
		Pageable pageable = null;
		if(Optional.ofNullable(userInfo).isPresent()&&StringUtils.isNotEmpty(userInfo.getPageNum())&&StringUtils.isNotEmpty(userInfo.getPageSize())){
			int pageNumInt = Integer.parseInt(userInfo.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(userInfo.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = userInfoDao.findAll(new Specification<UserInfo>() {
				/**
				 * 序列化id
				 */
				private static final long serialVersionUID = 9041816359827790396L;
	
				@Override
	            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
					
					
	                Predicate namep = null;
	                Predicate sexp = null;
	                Predicate agep = null;
	                Predicate organizationp = null;
	                
	                Predicate fipostp = null;
	                Predicate postp = null;
	                Predicate qualificationp = null;
	                
	                String name = userInfo.getName();
	                String sex = userInfo.getSex();
	                int age = userInfo.getAge();
	                Organization organization = userInfo.getOrganization();
	                String fipost = userInfo.getFipost();//这个有外键关系，不一定用他，
	                String post = userInfo.getPost();
	                String qualification = userInfo.getQualification();
	                
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
					if(StringUtils.isNotEmpty(sex)) {
	                	sexp = cb.like(root.<String> get("sex"), "%" + sex + "%");
	                }
					if(age!=0) {
	                	agep = cb.equal(root.<Integer> get("age"), age);
	                }
					if(ObjectUtils.allNotNull(organization) && organization.getId()!=0) {
//						organizationp=cb.equal(root.<Organization> get("id"), organization.getId());
						organizationp=cb.equal(root.<Organization> get("organization").<Integer> get("id"), organization.getId());
	                }
					if(StringUtils.isNotEmpty(fipost)) {
						fipostp = cb.like(root.<String> get("fipost"), "%" + fipost + "%");
	                }
					if(StringUtils.isNotEmpty(post)) {
						postp = cb.like(root.<String> get("post"), "%" + post + "%");
	                }
					if(StringUtils.isNotEmpty(qualification)) {
						qualificationp = cb.like(root.<String> get("qualification"), "%" + qualification + "%");
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
	                 if(Optional.ofNullable(sexp).isPresent()) query.where(sexp);
	                 if(Optional.ofNullable(agep).isPresent()) query.where(agep);
	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 
	                 if(Optional.ofNullable(fipostp).isPresent()) query.where(fipostp);
	                 if(Optional.ofNullable(postp).isPresent()) query.where(postp);
	                 if(Optional.ofNullable(qualificationp).isPresent()) query.where(qualificationp);
	                 return null;
	             }
	         },pageable);
		}else if(
					!Optional.ofNullable(userInfo).isPresent()||
					(
					StringUtils.isBlank(userInfo.getPageNum())&&
					StringUtils.isBlank(userInfo.getPageSize())
					)
				)
		{
			page = userInfoDao.findAll(Pageable.unpaged());
		}else{
			return new Result<Page<UserInfo>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage(ProjectConstant.PAGING_QUERY_FAILURE);
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 用户添加
	 * @param userInfo
	 * @return Result<String>
	 */
	protected Result<String> getUserAdd(UserInfo userInfo) {
		try {
			//赋予默认的角色(所属组织角色)（暂时每个人一个角色，因为前台没有角色管理的页面，为了后期做加入角色更改可配做准备，增加拓展性，尽量少改动代码，同时也为了后期升级引入shiro）
			if(Optional.ofNullable(userInfo.getOrganization()).isPresent()){
				if(0==userInfo.getSuperAdministrator()){//不是管理员的人
					List<SysRole> collect = userInfo.getRoleList().stream().filter(u->!ProjectConstant.NOTHING.equals(u.getRole())).collect(Collectors.toList());
					if(CollectionUtils.isEmpty(collect)){
						SysRole sysRole = new SysRole();
						sysRole.setAvailable((short) 1);
						sysRole.setDescription(userInfo.getOrganization().getIds());
						sysRole.setRole(userInfo.getOrganization().getName());
						sysRoleDao.save(sysRole);
						userInfo.setRoleList(Lists.newArrayList(sysRole));
					}
				}
			}
			userInfoDao.save(userInfo);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage()+ProjectConstant.USER_ADD_OR_UPDATE_FAILURE);
		}
	}
	/**
	 * 用户权限添加
	 * @param userInfo
	 * @return Result<String>
	 */
	protected Result<String> getUserPermissonAdd(UserInfo userInfo) {
		try {
			userInfoDao.save(userInfo);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage()+ProjectConstant.USER_PERMISSION_ADD_FAILURE);
		}
	}
	
	protected Result<String> getUserAddFailure() {
		return ResultGenerator.genFailResult(ProjectConstant.USER_ADD_OR_UPDATE_FAILURE);
	}
	protected Result<String> getUserPermissionAddFailure() {
		return ResultGenerator.genFailResult(ProjectConstant.USER_PERMISSION_ADD_FAILURE);
	}
	protected Result<String> getPleaseLoginFirst() {
		return ResultGenerator.genFailResult(ProjectConstant.PLEASE_LOGIN_FIRST);
	}
	protected Result<String> getUserNotExisted() {
		return ResultGenerator.genFailResult(ProjectConstant.USER_NOT_EXISTED);
	}
	

	/**
	 * 用户删除
	 * @param userInfo
	 * @return Result<String>
	 */
	protected Result<String> getUserDel(UserInfo userInfo) {
		try {
			userInfoDao.delete(userInfo);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(ProjectConstant.USER_DELETING_FAILURE);
		}
	}

	/**
	 * 锁定用户
	 * @param userInfo
	 * @return Result<String>
	 */
	protected Result<String> getUserLock(UserInfo userInfo) {
		//锁定用户
		userInfo.setState((byte) 2);
		//删除角色
		userInfo.setRoleList(null);
		userInfoDao.save(userInfo);
	    return ResultGenerator.genSuccessResult();
	}

}
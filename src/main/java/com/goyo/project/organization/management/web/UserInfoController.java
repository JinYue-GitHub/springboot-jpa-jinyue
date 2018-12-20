package com.goyo.project.organization.management.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Lists;
import com.goyo.project.common.CommonDataForUserInfo;
import com.goyo.project.core.ProjectConstant;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Organization;
import com.goyo.project.model.PartyOrganization;
import com.goyo.project.model.SysRole;
import com.goyo.project.model.UserInfo;
import com.goyo.project.organization.management.dao.OrganizationDao;
import com.goyo.project.organization.management.dao.UserInfoDao;
import com.goyo.project.partyOrganization.management.dao.PartyOrganizationDao;
import com.goyo.project.role.management.dao.SysRoleDao;
import com.goyo.project.utils.BeanUtils;
import com.goyo.project.utils.CommonUtils;

@RestController
//@RequestMapping(value="/userInfo",method = RequestMethod.POST)
//@CrossOrigin(origins = "http://10.5.95.60:9095", maxAge = 3600)
public class UserInfoController extends CommonDataForUserInfo {
	
	@Resource
	private UserInfoDao userInfoDao; 
	
	@Resource
	private SysRoleDao sysRoleDao; 
	
	@Resource
	private OrganizationDao organizationDao;
	
	@Resource
	private PartyOrganizationDao partyOrganizationDao;
	 /**
     * 用户查询(single)
     * @return Result<UserInfo>
     */
	@PostMapping("/userSingle")
    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<UserInfo> userSingle(UserInfo userInfo){
		return getUserSingle(userInfo);
    }
	
    /**
     * 党内职务下拉信息
     * @return Result<UserInfo>
     */
	@PostMapping("/partyPost")
    public Result<List<String>> partyPost(){
		List<String> list = Lists.newArrayList();
		list.add(ProjectConstant.POST1);
		list.add(ProjectConstant.POST2);
		return ResultGenerator.genSuccessResult(list);
    }
    
    /**
     * 学历下拉信息
     * @return Result<UserInfo>
     */
	@PostMapping("/qualification")
    public Result<List<String>> qualification(){
		List<String> list = Lists.newArrayList();
		
		list.add(ProjectConstant.DOCTOR);
		list.add(ProjectConstant.MASTER);
		list.add(ProjectConstant.UNDERGRADUATE);
		list.add(ProjectConstant.HIGH_SCHOOL);
		list.add(ProjectConstant.JUNIOR_HIGH_SCHOOL_AND_BELOW);
		return ResultGenerator.genSuccessResult(list);
    }
	 /**
     * 党内职务下拉信息
     * @return Result<UserInfo>
     */
	@PostMapping("/political")
    public Result<List<String>> political(){
		List<String> list = Lists.newArrayList();
		list.add(ProjectConstant.PARTY_MEMBER);
		list.add(ProjectConstant.PROBATIONARY_PARTY_MEMBER);
		list.add(ProjectConstant.ACTIVISTS);
		list.add(ProjectConstant.MEMBER);
		list.add(ProjectConstant.PARTY_MEMBER);
		list.add(ProjectConstant.MASSES);
		return ResultGenerator.genSuccessResult(list);
    }
	 /**
     * 通过组织id获取组织下的人
     * @return Result<UserInfo>
     */
	@PostMapping("/userInfoByOrganizationId")
    public Result<List<UserInfo>>  userInfoByOrganizationId(Organization organization){
		Result<List<UserInfo>> userInfosByOrganizationId = getUserInfosByOrganizationId(organization.getId());
		return userInfosByOrganizationId;
    }
	/**
     * 用户查询(all)
     * @return Result<List<UserInfo>>
     */
	@PostMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<Page<UserInfo>> userList(@RequestBody(required=false) UserInfo userInfo, String pageNum, String pageSize){
		return getUserPage(userInfo, pageNum, pageSize);
		
    }
    /**
     * 用户添加;
     * @return
     */
	@PostMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public Result<String> userInfoAdd(UserInfo userInfo){
		return getUserAdd(userInfo);
        
    }
	/**
     * 用户更新;(完善信息)
     * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
     */
	@PostMapping("/userUpate")
    public Result<String> userInfoUpdate(@RequestBody UserInfo userInfo) throws IllegalAccessException, InvocationTargetException{
		
		
		UserInfo currentUserInfo = CommonUtils.getCurrentUserInfo();
		System.out.println(currentUserInfo);
	  
		UserInfo userInfoDB = getUserSingle_(userInfo);
		if(!Optional.ofNullable(userInfo.getUid()).isPresent()){
			//更改密码
			if(!Optional.ofNullable(userInfoDB).isPresent()) return getUserNotExisted();
			userInfoDB.setPassword(userInfo.getPassword());
			return getUserAdd(userInfoDB);
		}else{
//			UserInfo currentUserInfo = CommonUtils.getCurrentUserInfo();
//			if(!Optional.ofNullable(currentUserInfo).isPresent()) return getPleaseLoginFirst();
			/*if(0==currentUserInfo.getSuperAdministrator()){//普通用户登录操作
				if(currentUserInfo.getUid()==userInfo.getUid()){
					BeanUtils.copyProperties(userInfo,userInfoDB);
					//赋予默认的角色(所属组织角色)（暂时每个人一个角色，因为前台没有角色管理的页面，为了后期做加入角色更改可配做准备，增加拓展性，尽量少改动代码，同时也为了后期升级引入shiro）
					if(Optional.ofNullable(userInfoDB.getOrganization()).isPresent()){
						if(0==userInfo.getSuperAdministrator()){//不是管理员的人
							if(Optional.ofNullable(userInfo.getRoleList().stream().filter(u->!ProjectConstant.NOTHING.equals(u.getRole())).collect(Collectors.toList())).isPresent()){
								SysRole sysRole = new SysRole();
								sysRole.setAvailable((short) 1);
								sysRole.setDescription(userInfoDB.getOrganization().getIds());
								sysRole.setRole(userInfoDB.getOrganization().getName());
								sysRoleDao.save(sysRole);
								userInfoDB.setRoleList(Lists.newArrayList(sysRole));
							}
						}
					}
					return getUserAdd(userInfoDB);
				}else{
					return getUserAdd();
				}
			}else{//管理员
				BeanUtils.copyProperties(userInfo,userInfoDB);
				return getUserAdd(userInfoDB);
			}*/
			BeanUtils.copyProperties(userInfo,userInfoDB);
			if(Optional.ofNullable(userInfo.getOrganization()).isPresent()&&Optional.ofNullable(userInfo.getOrganization().getId()).isPresent()){
				Organization organization = organizationDao.findById(userInfo.getOrganization().getId()).get();
				userInfoDB.setOrganization(organization);
			}
			if(Optional.ofNullable(userInfo.getPartyOrganization()).isPresent()&&Optional.ofNullable(userInfo.getPartyOrganization().getId()).isPresent()){
				PartyOrganization partyOrganization = partyOrganizationDao.findById(userInfo.getPartyOrganization().getId()).get();
				userInfoDB.setPartyOrganization(partyOrganization);
			}
			return getUserAdd(userInfoDB);
		}
		
//		return null;
    }
	/**
     * 用户更新;(权限)
     * @return
	 * @throws  
	 * @throws  
     */
	@PostMapping("/userPermissionUpate")
    public Result<String> userPermissionUpate(@RequestBody UserInfo userInfo) throws IllegalAccessException, InvocationTargetException{
		
		UserInfo currentUserInfo = CommonUtils.getCurrentUserInfo();
		System.out.println(currentUserInfo);
	  
		UserInfo userInfoDB = getUserSingle_(userInfo);
		if(!Optional.ofNullable(userInfo.getUid()).isPresent()){
			//更改密码
			userInfoDB.setPassword(userInfo.getPassword());
			return getUserAdd(userInfoDB);
		}else{
//			UserInfo currentUserInfo = CommonUtils.getCurrentUserInfo();
			if(!Optional.ofNullable(currentUserInfo).isPresent()) return getPleaseLoginFirst();
			if(0!=currentUserInfo.getSuperAdministrator()){//管理员
				BeanUtils.copyProperties(userInfo,userInfoDB);
				return getUserPermissonAdd(userInfoDB);
			}else{
				return getUserPermissionAddFailure();
			}
//			BeanUtils.copyProperties(userInfo,userInfoDB);
//			return getUserAdd(userInfoDB);
		}
    }
    /**
     * 用户删除;
     * @return
     */
	@PostMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public Result<String> userDel(UserInfo userInfo){
		return getUserDel(userInfo);
    }
	 /**
     * 锁定用户（禁止登陆）lock
     * @return Result<UserInfo> forbidden
     */
	@PostMapping("/userLock")
	@RequiresPermissions("userInfo:lock")//权限管理;
    public Result<String> lock(UserInfo userInfo){
		return getUserLock(userInfo);
    }
}
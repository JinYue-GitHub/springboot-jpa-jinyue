package com.goyo.project.authorization.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.goyo.project.common.CommonDataForUserInfo;
import com.goyo.project.core.ProjectConstant;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Organization;
import com.goyo.project.model.SysPermission;
import com.goyo.project.model.SysRole;
import com.goyo.project.model.UserInfo;
import com.goyo.project.organization.management.dao.OrganizationDao;
import com.goyo.project.organization.management.dao.UserInfoDao;
import com.goyo.project.permission.management.dao.SysPermissionDao;
import com.goyo.project.role.management.dao.SysRoleDao;
import com.goyo.project.utils.CommonUtils;
/**
 * @author: JinYue
 * @ClassName: AuthorizationController 
 * @Description: 授权
 */
@RestController
@RequestMapping("/authorization")
public class AuthorizationController extends CommonDataForUserInfo{

	
	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private SysPermissionDao sysPermissionDao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private SysRoleDao sysRoleDao;
	
	public static int getCount(String str,String scan){
        int count = 0;
        int index = 0;
        while(((index = str.indexOf(scan)) != -1)){
            //想个办法截取字符串中查找字符,并将查找当前匹配字符之后的字符串重新
            //赋值给字符串
            str = str.substring(index+1);
            count++;
        }
        return count;
    }
	
	 /**
     * 人员分页查询
     * @return
     */
	@PostMapping("/userInfoList")
    public Result<Page<UserInfo> > permissionInfo(UserInfo userInfo,String pageNum,String pageSize){
        return getUserPage(userInfo, pageNum, pageSize);
    }
	
    /**
     * 权限配置（局里的组织部的管理员进来看局里的资源权限列表，新建矿组织部的管理员进来看新建矿的资源列表。通过当前的登录人的所属的组织区分）
     * @return
     */
	@PostMapping("/permissionConfigList")
    public Result<Object > permissionConfigList(HttpServletRequest request,SysPermission sysPermission,String pageNum,String pageSize){
//		List<String> collect = sysPermissionDao.findAll().stream().filter(u -> getCount(u.getParentIds(),"/")==2).collect(Collectors.toList()).stream().map(u -> u.getName()).collect(Collectors.toList());
//		List<Organization> collect = organizationDao.findAll().stream().filter(u -> getCount(u.getIds(),"/")==2).collect(Collectors.toList()).;
		
		UserInfo currentUserInfo = CommonUtils.getCurrentUserInfo();
		if(!Optional.ofNullable(currentUserInfo).isPresent()) return ResultGenerator.genFailResultToObject(ProjectConstant.PLEASE_LOGIN_FIRST);
		byte superAdministrator = currentUserInfo.getSuperAdministrator();
		//root超管 局里
//		if(1==superAdministrator){
////			List<String> menu = sysPermissionDao.findAll().stream().map(u -> u.getName()).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
//			List<Organization> collect = organizationDao.findAll().stream().filter(u -> getCount(u.getIds(),"/")==2).collect(Collectors.toList());//考虑到以后可能会有多个根节点，所以查询返回用了list来接收，现在只有一个根节点
//			return ResultGenerator.genSuccessResult(collect);
//		}else if(2==superAdministrator){//root的下级超管 矿上
//			return ResultGenerator.genSuccessResult(currentUserInfo.getOrganization());
//		}else{
//			return null;
//		}
		if(0!=superAdministrator){
			return ResultGenerator.genSuccessResult(currentUserInfo.getOrganization());
		}else{
			return ResultGenerator.genFailResultToObject(ProjectConstant.ONLY_ADMINISTRATORS_CAN_ASSIGN_PERMISSIONS);
		}
		
    }

    /**
     * 权限添加;
     * @return
     */
	@PostMapping("/permissionConfigUpdate")
//    @RequiresPermissions("permission:add")//权限管理; 前台传给我的某个人的权限ids，id以逗号分隔
    public Result<String> permissionConfigUpdate(HttpSession session,UserInfo userInfo,String permissionIds){
		Object attribute = session.getAttribute("sessionUserInfo");
		Object attribute2 = session.getAttribute("sessionAuthorization");
		System.out.println(attribute);
		System.out.println(attribute2);
		UserInfo currentUserInfo = CommonUtils.getCurrentUserInfo();
		if(Optional.ofNullable(currentUserInfo).isPresent()){
			//如果是root（局）管理员或者下级管理员才可以
			if(0!=currentUserInfo.getSuperAdministrator()){//管理员
				if(Optional.ofNullable(userInfo).isPresent()&&Optional.ofNullable(userInfo.getUid()).isPresent()){
					UserInfo user = userInfoDao.findById(userInfo.getUid()).get();
					if(Optional.ofNullable(permissionIds).isPresent()){
						String[] split = permissionIds.split(",");
						List<SysRole> roleList = Lists.newArrayList();
						List<SysPermission> permissionList = Lists.newArrayList();
						//得到完善信息后的默认角色
						List<SysRole> roleListDB = user.getRoleList();
						roleListDB = roleListDB.parallelStream().distinct().collect(Collectors.toList());
						
						for(SysRole role:roleListDB){
							role.setPermissions(null);
							sysRoleDao.save(role);
						}
						for(String id:split){
							SysPermission sysPermission = sysPermissionDao.findById(Integer.parseInt(id)).get();
								sysPermission.setRoles(roleListDB);
								sysPermissionDao.save(sysPermission);
//								role.setPermissions(permissionList);
//								sysRoleDao.save(role);
//								roleList.add(role);
//							permissionList.add(sysPermission);
						}
						
//						user.setRoleList(roleList);
						//保存权限
//						userInfoDao.save(user);
						return ResultGenerator.genSuccessResult();
					}else{
						return ResultGenerator.genFailResult(ProjectConstant.THE_PERMISSIONS_STRING_DOES_NOT_EXIST);
					}
				}else{
					return ResultGenerator.genFailResult(ProjectConstant.USER_NOT_EXISTED);
				}
			}else{
				return ResultGenerator.genSuccessResult(ProjectConstant.ONLY_ADMINISTRATORS_CAN_ASSIGN_PERMISSIONS);
			}
		}else{
			return ResultGenerator.genFailResult(ProjectConstant.PLEASE_LOGIN_FIRST);
		}
		
		
        
    }
	
	/**
     * 权限更新;
     * @return
     */
	@PostMapping("/permissionUpdate")
    @RequiresPermissions("permission:update")//权限管理;
    public Result<String> permissionUpdate(SysPermission sysPermission){
		sysPermissionDao.save(sysPermission);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 权限删除;
     * @return
     */
	@PostMapping("/permissionDel")
    @RequiresPermissions("permission:del")//权限管理;
    public Result<String> permissionDel(SysPermission sysPermission){
		sysPermissionDao.deleteById(sysPermission.getId());
        return ResultGenerator.genSuccessResult();
    }
}
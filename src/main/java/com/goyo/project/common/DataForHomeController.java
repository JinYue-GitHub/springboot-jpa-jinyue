package com.goyo.project.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.collect.Lists;
import com.goyo.project.core.ProjectConstant;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Authorization;
import com.goyo.project.model.Organization;
import com.goyo.project.model.PartyOrganization;
import com.goyo.project.model.SysPermission;
import com.goyo.project.model.SysRole;
import com.goyo.project.model.UserInfo;
import com.goyo.project.organization.management.dao.OrganizationDao;
import com.goyo.project.organization.management.dao.UserInfoDao;
import com.goyo.project.partyOrganization.management.dao.PartyOrganizationDao;
import com.goyo.project.permission.management.dao.SysPermissionDao;
import com.goyo.project.role.management.dao.SysRoleDao;
import com.goyo.project.utils.CommonUtils;

public class DataForHomeController {

	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private PartyOrganizationDao partyOrganizationDao;
	@Resource
	private SysPermissionDao sysPermissionDao;
	@Resource
	private SysRoleDao sysRoleDao;
	
	
	 public static ArrayList<UserInfo> list = new ArrayList<UserInfo>();
	    static {

	       //实现user实体类

//	        list.add(new UserInfo("aaa", "123"));
//	        list.add(new UserInfo("bbb", "123"));
//	        list.add(new UserInfo("ccc", "123"));
	    }
	    public static List<UserInfo> getAll(){
	        return list;
	    }
	

	public DataForHomeController() {
		super();
	}

	protected Result<String> getIndex() {
		return ResultGenerator.genSuccessResult("/index");
	}

	protected Result<Object> getRegister(UserInfo userInFo, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
	        return ResultGenerator.genFailResultToObject(bindingResult.getFieldError().getDefaultMessage());
	    }
		try {
			if(Optional.ofNullable(userInFo.getPhoneNumber()).isPresent()||Optional.ofNullable(userInFo.getUsername()).isPresent()){
				UserInfo oldUserInfo = null;
				if(Optional.ofNullable(userInFo.getPhoneNumber()).isPresent()){
					oldUserInfo = userInfoDao.findByPhoneNumber(userInFo.getPhoneNumber());
				}else{
					oldUserInfo = userInfoDao.findByUsername(userInFo.getUsername());
				}
				if(Optional.ofNullable(oldUserInfo).isPresent()){
					return ResultGenerator.genFailResultToObject(ProjectConstant.USER_ALREADY_EXISTED);
				}else{
					//角色
					List<SysRole> roleList = Lists.newArrayList();
					if(Optional.ofNullable(sysRoleDao.findByRole(ProjectConstant.NOTHING)).isPresent()){
						roleList.add(sysRoleDao.findByRole(ProjectConstant.NOTHING));
						userInFo.setRoleList(roleList);
					}else{
						SysRole sysRole = new SysRole();
						sysRole.setAvailable((short) 1);
						sysRole.setRole(ProjectConstant.NOTHING);
						roleList.add(sysRole);
						sysRoleDao.save(sysRole);
						userInFo.setRoleList(roleList);
					}
					//党组织
					if(Optional.ofNullable(partyOrganizationDao.findByName(ProjectConstant.NOTHING)).isPresent()){
						userInFo.setPartyOrganization(partyOrganizationDao.findByName(ProjectConstant.NOTHING));
					}else{
						PartyOrganization partyOrganization = new PartyOrganization();
						partyOrganization.setName(ProjectConstant.NOTHING);
						partyOrganizationDao.save(partyOrganization);
						userInFo.setPartyOrganization(partyOrganization);
					}
					//组织
					if(Optional.ofNullable(organizationDao.findByName(ProjectConstant.NOTHING)).isPresent()){
						userInFo.setOrganization(organizationDao.findByName(ProjectConstant.NOTHING));
					}else{
						Organization organization = new Organization();
						organization.setName(ProjectConstant.NOTHING);
						organizationDao.save(organization);
						userInFo.setOrganization(organization);
					}
					userInfoDao.save(userInFo);
					HttpSession currentSession = CommonUtils.getCurrentSession();
		    		currentSession.setAttribute("sessionUserInfo", userInFo);
					return ResultGenerator.genSuccessResult(userInFo);
				}
			}else{
				return ResultGenerator.genFailResultToObject(ProjectConstant.REGISTRATION_FAILURE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResultToObject(ProjectConstant.REGISTRATION_FAILURE+e.getMessage());
		}
	}
	
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

	protected Result<Object> getLogin(UserInfo userInfo) {
		
			//获取页面传入的账号和密码
	    	String username = userInfo.getUsername();
	    	String phoneNumber = userInfo.getPhoneNumber();
	    	String password = userInfo.getPassword();
	    	//查询数据库是否存在用户名所对应的用户信息
	    	UserInfo userInfoDB = null;
	    	UserInfo userInfoDBByUsername = null;
	    	UserInfo userInfoDBByPhoneNumber = null;
	    	if(Optional.ofNullable(username).isPresent()){
	    		userInfoDBByUsername = userInfoDao.findByUsername(username); 
	    	}
	    	if(Optional.ofNullable(phoneNumber).isPresent()){
	    		userInfoDBByPhoneNumber = userInfoDao.findByPhoneNumber(phoneNumber);  
	    	}
	    	if(Optional.ofNullable(userInfoDBByUsername).isPresent()){
	    		userInfoDB = userInfoDBByUsername;
	    	}
	    	if(Optional.ofNullable(userInfoDBByPhoneNumber).isPresent()){
	    		userInfoDB = userInfoDBByPhoneNumber;
	    	}
	    	
	    	if(	
	    			(
	    			(
		    			Optional.ofNullable(userInfoDBByUsername).isPresent()&&
		    			Optional.ofNullable(userInfoDBByUsername.getPassword()).isPresent()&&
		    			password.equals(userInfoDBByUsername.getPassword())
	    			)
	    			||
	    			(
						Optional.ofNullable(userInfoDBByUsername).isPresent()&&
		    			(!Optional.ofNullable(userInfoDBByUsername.getPassword()).isPresent())&&
		    			(!Optional.ofNullable(password).isPresent())
					)
	    			)
	    			
	    			||
	    			(
	    			(
		    			Optional.ofNullable(userInfoDBByPhoneNumber).isPresent()&&
		    			Optional.ofNullable(userInfoDBByPhoneNumber.getPassword()).isPresent()&&
		    			password.equals(userInfoDBByPhoneNumber.getPassword())
	    			)
	    			||
	    			(
						Optional.ofNullable(userInfoDBByPhoneNumber).isPresent()&&
		    			(!Optional.ofNullable(userInfoDBByPhoneNumber.getPassword()).isPresent())&&
		    			(!Optional.ofNullable(password).isPresent())
					)
	    			)
			){
	    		// 将用户保存到session内
	//    		用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定
	    		if ("2".equals(userInfoDB.getState())){
	    			//用户已锁定
	    			return ResultGenerator.genFailResultToObject(ProjectConstant.USER_HAS_BEEN_LOCKED);
	    		}else{
	    			
	    			List<SysRole> roleListDB = userInfoDB.getRoleList();
	    			roleListDB = roleListDB.parallelStream().distinct().collect(Collectors.toList());
	    			userInfoDB.setRoleList(roleListDB);
	    			//登录成功
	    			Authorization authorization = new Authorization();
		    		authorization.setUserInfo(userInfoDB);
		    		if(userInfoDB.getSuperAdministrator()!=0){
		    			//说明是管理员
		    			List<String> menuList = Lists.newArrayList();
		    			List<SysPermission> permissionList = Lists.newArrayList();
		    			menuList.add(ProjectConstant.PERMISSION_MANAGEMENT);
		    			List<SysPermission> collect = userInfoDB.getOrganization().getPermissions().stream().filter(u -> getCount(u.getIds(),"/")==4).collect(Collectors.toList());
//		    			List<SysPermission> collect = sysPermissionDao.findByName(ProjectConstant.PERMISSION_MANAGEMENT).stream().filter(u -> getCount(u.getIds(),"/")==4).collect(Collectors.toList());
		    			if(CollectionUtils.isNotEmpty(collect)){
		    				permissionList.addAll(collect);
		    			}else{
		    				permissionList.add(null);
		    			}
		    			authorization.setMenuList(menuList);
		    			authorization.setPermissionList(permissionList);
		    		}else{
		    			List<String> menuList = Lists.newArrayList();
		    			List<String> tempList = Lists.newArrayList();
		    			
		    			List<SysPermission> permissionList = Lists.newArrayList();
		    			if(Optional.ofNullable(userInfoDB.getRoleList()).isPresent()){
		    				userInfoDB.getRoleList().stream().forEach(u -> u.getPermissions().forEach(p -> permissionList.add(p)));
		    				userInfoDB.getRoleList().stream().forEach(u -> u.getPermissions().forEach(p -> {if(getCount(p.getIds(),"/")==3){tempList.add(p.getName());}}));
		    				menuList = tempList.stream().distinct().collect(Collectors.toList());
		    			}
		    			authorization.setMenuList(menuList);
		    			authorization.setPermissionList(permissionList);
		    		}
		    		CommonUtils.setCurrentUserInfo(userInfoDB);
		    		CommonUtils.setCurrentAuthorization(authorization);
					return ResultGenerator.genSuccessResult(authorization);
	    		}
	    	}else{
	    		return ResultGenerator.genFailResultToObject(ProjectConstant.INCORRECT_USERNAME_OR_PASSWORD);
	    	}
		}

	protected Result<String> getLogout() {
		CommonUtils.getLogout();
	    return ResultGenerator.genSuccessResult(ProjectConstant.LOGOUT_SUCCESS);
	}

	protected Result<String> getUnauthorizedRole() {
		System.out.println("------没有权限-------");
	    return ResultGenerator.genSuccessResult("403");
	}
	
	
	/*//注销
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request, Map<String, Object> map) throws Exception{
        System.out.println("HomeController.logout()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
        	msg="注销失败!";
        }else{
        	msg="注销成功!";
        }
        map.put("msg", msg);
        // 此方法不处理注销,由shiro进行处理
        return ResultGenerator.genSuccessResult("/logout");
    }*/

	   /* @PostMapping("/login")
	    public Result<String> login(HttpServletRequest request, Map<String, Object> map,HttpSession httpSession) throws Exception{
	        System.out.println("HomeController.login()");
	        // 登录失败从request中获取shiro处理的异常信息。
	        // shiroLoginFailure:就是shiro异常类的全类名.
	        String exception = (String) request.getAttribute("shiroLoginFailure");
	        System.out.println("exception=" + exception);
	        String msg = "";
	        if (exception != null) {
	            if (UnknownAccountException.class.getName().equals(exception)) {
	                System.out.println("UnknownAccountException -- > 账号不存在：");
	                msg = "UnknownAccountException -- > 账号不存在：";
	            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
	                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
	                msg = "IncorrectCredentialsException -- > 密码不正确：";
	            } else if ("kaptchaValidateFailed".equals(exception)) {
	                System.out.println("kaptchaValidateFailed -- > 验证码错误");
	                msg = "kaptchaValidateFailed -- > 验证码错误";
	            } else {
	                msg = "else >> "+exception;
	                System.out.println("else -- >" + exception);
	            }
	        }
	        map.put("msg", msg);
	        // 此方法不处理登录成功,由shiro进行处理
	        return ResultGenerator.genSuccessResult("/login");
	    }*/
	
//	在用Oracle中经常碰到有这种情况，ORA-02291: 违反完整约束条件 (*) - 未找到父项关键字总体说说可能出现的原因：
//	情况场景：表A中有个字段是外键，关联了表B中的某字段，再往表A插入数据时，会出现这种情况。可能原因：
//	1.在往A表插入时，外键关联的字段在B表中必须有数据，如果B表中没有数据则又这种情况。
//	2.产生了外键环，就是B表中被外键关联的字段又关联了C表中的字段，而C中相应字段却没有数据，则产生这种情况。
//	3.如果不是上两种情况，那么就是一个非常容易疏忽的问题：A中的外键字段和B中的被外键关联字段数据类型和长度不一致。
//	特别是数据长度，必须要一致。第三种情况是最容易忽略的，希望大家注意。
	//注册
}
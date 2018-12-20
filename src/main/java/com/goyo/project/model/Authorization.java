package com.goyo.project.model;

import java.io.Serializable;
import java.util.List;

public class Authorization implements Serializable{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6777540313322210968L;
	private UserInfo userInfo;
	private List<String> menuList;
	private List<SysPermission> permissionList;
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<String> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<String> menuList) {
		this.menuList = menuList;
	}
	public List<SysPermission> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<SysPermission> permissionList) {
		this.permissionList = permissionList;
	}
	
}

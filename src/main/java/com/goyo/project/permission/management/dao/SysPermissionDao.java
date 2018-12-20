package com.goyo.project.permission.management.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.goyo.project.model.SysPermission;
import com.goyo.project.model.UserInfo;
//public interface SysPermissionDao extends JpaRepository<SysPermission,Long> {
//}
public interface SysPermissionDao extends JpaRepository<SysPermission,Integer>,JpaSpecificationExecutor<SysPermission> {
	public List<SysPermission> findByName(String name);
}
package com.goyo.project.role.management.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.goyo.project.model.SysRole;
import java.lang.String;
public interface SysRoleDao extends JpaRepository<SysRole,Integer>,JpaSpecificationExecutor<SysRole> {
	public SysRole findByDescription(String description);
	public SysRole findByRole(String role);
}
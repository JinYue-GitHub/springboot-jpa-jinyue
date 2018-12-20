package com.goyo.project.organization.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.goyo.project.model.Organization;
import java.lang.String;
import java.util.List;
public interface OrganizationDao extends JpaRepository<Organization, Integer>,JpaSpecificationExecutor<Organization> {
	public Organization findByName(String name);
	public List<Organization> findByParentIds(String parentids);
}
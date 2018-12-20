package com.goyo.project.partyOrganization.management.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.goyo.project.model.PartyOrganization;
import java.lang.String;
public interface PartyOrganizationDao extends JpaRepository<PartyOrganization, Integer>,JpaSpecificationExecutor<PartyOrganization> {
	public PartyOrganization findByName(String name);
	//查询党组织数量
    @Query("from PartyOrganization t where t.ids like %?1%")
    public List<PartyOrganization> findByPartyOrganizationIds(String partyOrganizationIds);
    @Query("from PartyOrganization t where t.ids=?1")
    public PartyOrganization findByPartyIds(String ids);
    
}
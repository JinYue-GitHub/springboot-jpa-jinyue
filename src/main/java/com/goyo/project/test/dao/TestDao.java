/*package com.goyo.project.test.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import com.goyo.project.model.Test;
import java.lang.String;
import java.util.List;
@Transactional
public interface TestDao extends JpaRepository<Test,String>,JpaSpecificationExecutor<Test>{
    *//**通过typeName信访举报相关信息;*//*
	
	 public List<Test> findByName(String name);
//    public UserInfo findByUsername(String username);
//    public UserInfo findByPhoneNumber(String phoneNumber);
    
    //查询所属政治面貌的人数(指定部门的)
    @Query("from UserInfo t where t.political like %?1% and t.organization.ids like %?2%")
    public List<UserInfo> findByPoliticalAndOrganizationIds(String political,String organizationIds);
    //查询党这条线对应的所属政治面貌的人数
    @Query("from UserInfo t where t.political like %?1% and t.partyOrganization.ids like %?2%")
    public List<UserInfo> findByPoliticalAndPartyOrganizationIds(String political,String partyOrganizationIds);
    @Query("from UserInfo t where t.organization.id = :organizationId ")
    public List<UserInfo> findByOrganizationId(@Param("organizationId") Integer organizationId);
    
}*/
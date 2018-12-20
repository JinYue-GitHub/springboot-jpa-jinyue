package com.goyo.project.problem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.goyo.project.model.Problem;
import java.lang.String;
//@Transactional
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**通过username查找用户信息;*/
	
	@Query(value="update Problem t set personCount=t.personCount+1 where t.id = :problemId ", nativeQuery = true)
	public void updatePersonCountPlus(@Param("problemId")String problemId);
	@Query(value="update Problem t set personCount=t.personCount-1 where t.id = :problemId ", nativeQuery = true)
	public void updatePersonCountMinus(@Param("problemId")String problemId);
//	@Query(value="select user.id from user where user.age > 18", nativeQuery = true)
	
//	update
	
    /*public UserInfo findByUsername(String username);
    public UserInfo findByPhoneNumber(String phoneNumber);
    //查询所属政治面貌的人数(指定部门的)
    @Query("from UserInfo t where t.political like %?1% and t.organization.ids like %?2%")
    public List<UserInfo> findByPoliticalAndOrganizationIds(String political,String organizationIds);
    //查询党这条线对应的所属政治面貌的人数
    @Query("from UserInfo t where t.political like %?1% and t.partyOrganization.ids like %?2%")
    public List<UserInfo> findByPoliticalAndPartyOrganizationIds(String political,String partyOrganizationIds);
    //
    @Query("from UserInfo t where t.political = :political and t.partyOrganization.id = :partyOrganizationId")
    public List<UserInfo> findByPoliticalAndPartyOrganizationId(@Param("political")String political,@Param("partyOrganizationId")Integer partyOrganizationId);
    @Query("from UserInfo t where t.organization.id = :organizationId ")
    public List<UserInfo> findByOrganizationId(@Param("organizationId") Integer organizationId);*/
    
}
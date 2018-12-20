package com.goyo.project.questionnaire.survey.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.goyo.project.model.QuestionnaireSurvey;

import java.lang.String;
import java.util.HashSet;
import java.util.LinkedList;
//@Transactional
import java.util.List;
public interface QuestionnaireSurveyDao extends JpaRepository<QuestionnaireSurvey,String>,JpaSpecificationExecutor<QuestionnaireSurvey>{
    /**通过username查找用户信息;*/
	
	List<QuestionnaireSurvey> findByParentId(String parentid); 
	@Query(value="update QUESTIONNAIRE_SURVEY t set t.PERSON_COUNT=t.PERSON_COUNT+1 where t.ID = :questionnaireSurveyId ", nativeQuery = true)
	public void updatePersonCountPlus(@Param("questionnaireSurveyId")String questionnaireSurveyId);
	@Query(value="update QuestionnaireSurvey t set t.personCount=t.personCount-1 where t.id = :questionnaireSurveyId ", nativeQuery = true)
	public void updatePersonCountMinus(@Param("questionnaireSurveyId")String questionnaireSurveyId);
	
	@Query(value="select t.ID from QUESTIONNAIRE_SURVEY t where t.ROOT = :root ", nativeQuery = true)
	public LinkedList<String> findIdByRoot(@Param("root")byte root);
	@Query(value="select t.PARENT_ID from QUESTIONNAIRE_SURVEY t where t.ROOT = :root and t.U_ID = :uid ", nativeQuery = true)
	public HashSet<String> findParentIdByRootAndUid(@Param("root")byte root,@Param("uid")Integer uid);
	
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
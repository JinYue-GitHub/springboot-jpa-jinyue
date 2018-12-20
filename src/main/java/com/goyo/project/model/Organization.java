package com.goyo.project.model;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goyo.project.core.Comment;
/**
 * @author: JinYue
 * @ClassName: Organization 
 * @Description: 组织类
 */
@Table(name="ORGANIZATION")
@Entity
public class Organization implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 931744981091357031L;
	
	@Comment("主键") 
	@Column(name = "ID")
    @Id
    @GeneratedValue
	private Integer id;
	@Comment("组织名称") 
	@Column(name="NAME")
	private String name;
	@Comment("组织父id") 
	@Column(name="PARENT_ID")
	private int parentId;
	@Comment("组织节点子父关系字符串") 
	@Column(name="PARENT_IDS")
	private String parentIds;
	
	@Comment("组织唯一标识ids") 
	@Column(name="IDS",unique=true)
	private String ids;
	
	@Transient 
	private List<Organization> subOrganization;
	
	@Comment("部门负责人") 
	@Column(name="PERSON_ID")
	private String personId;
	
	@OneToMany(mappedBy="organization",fetch=FetchType.EAGER)
    @JsonManagedReference
	private transient List<UserInfo> userInfoList;// 人员
	
	
	@OneToMany(mappedBy="organization",fetch=FetchType.EAGER)
    @JsonManagedReference
	private transient List<QuestionnaireSurvey> questionnaireSurveyList;//调查问卷
	
	//组织对应的资源权限 	（例如：三会一课添加）
    @ManyToMany//(fetch= FetchType.EAGER)
    @JoinTable(name="OrganizationPermission",joinColumns={@JoinColumn(name="ORGANIZATION_ID")},inverseJoinColumns={@JoinColumn(name="PERMISSION_ID")})
    private List<SysPermission> permissions;
    
    
    
    
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public List<Organization> getSubOrganization() {
		return subOrganization;
	}
	public void setSubOrganization(List<Organization> subOrganization) {
		this.subOrganization = subOrganization;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}
	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}
	public List<QuestionnaireSurvey> getQuestionnaireSurveyList() {
		return questionnaireSurveyList;
	}
	public void setQuestionnaireSurveyList(List<QuestionnaireSurvey> questionnaireSurveyList) {
		this.questionnaireSurveyList = questionnaireSurveyList;
	}
	public List<SysPermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}
}

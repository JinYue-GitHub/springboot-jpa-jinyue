package com.goyo.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goyo.project.core.Comment;
/**
 * @author: JinYue
 * @ClassName: PartyOrganization 
 * @Description: 党组织类
 */
@Table(name="PARTY_ORGANIZATION")
@Entity
public class PartyOrganization implements Serializable{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1028583201381660530L;
	@Comment("主键") 
	@Column(name = "ID")
    @Id
    @GeneratedValue
	private Integer id;
	@Comment("党组织名称") 
	@Column(name="NAME")
	private String name;
	@Comment("党组织父id") 
	@Column(name="PARENT_ID")
	private int parentId;
	@Comment("党组织节点子父关系字符串") 
	@Column(name="PARENT_IDS")
	private String parentIds;
	@Comment("党组织唯一标识ids") 
	@Column(name="IDS")
	private String ids;
	@Comment("简介") 
	@Column(name="PRODUCTION")
	private String production;
	@Comment("工作简介") 
	@Column(name="WORK_PRODUCTION")
	private String workProduction;
	@Transient
	private List<PartyOrganization> subPartyOrganization;
	@Comment("党组织负责人") 
	@Column(name="PERSON_ID")
	private String personId;
	
	@OneToMany(mappedBy="partyOrganization",fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
    @JsonManagedReference
	private transient List<UserInfo> userInfoList;// 人员

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

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}
	public String getWorkProduction() {
		return workProduction;
	}

	public void setWorkProduction(String workProduction) {
		this.workProduction = workProduction;
	}

	public List<PartyOrganization> getSubPartyOrganization() {
		return subPartyOrganization;
	}

	public void setSubPartyOrganization(List<PartyOrganization> subPartyOrganization) {
		this.subPartyOrganization = subPartyOrganization;
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
}

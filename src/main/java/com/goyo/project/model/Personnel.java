package com.goyo.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goyo.project.core.Comment;

@Table(name = "PERSONNEL")
@Entity
public class Personnel implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7368603030082907491L;

	@Comment("主键")
	@Column(name = "ID")
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Comment("名字")
	@Column(name = "NAME")
	private String name;

	@Comment("性别")
	@Column(name = "SEX")
	private String sex;

	@Comment("民族")
	@Column(name = "NATION")
	private String nation;

	@Comment("出生日期")
	@Column(name = "BIRTHDAY")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	@Comment("文化程度")
	@Column(name = "QUALIFICATION")
	private String qualification;

	@Comment("政治面貌")
	@Column(name = "POLITICAL")
	private String political;

	@Comment("二级单位")
	@Column(name = "SECONDARYUNITS")
	private String secondaryUnits;

	@Comment("岗位")
	@Column(name = "POST")
	private String post;

	@Comment("党内职务(兼职)")
	@Column(name = "PIPOST")
	private String pipost;

	@Comment("入团时间")
	@Column(name = "ENTERDATE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enterDate;

	@Comment("毕业院校及专业")
	@Column(name = "UNIVERSITIES")
	private String universities;

	@Comment("单位")
	@Column(name = "COMPANY")
	private String company;

	@Comment("花名册类别(团干部花名册  团员花名册  青年花名册  大学生花名册)")
	@Column(name = "HMC_TYPE")
	private String hmcType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getSecondaryUnits() {
		return secondaryUnits;
	}

	public void setSecondaryUnits(String secondaryUnits) {
		this.secondaryUnits = secondaryUnits;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPipost() {
		return pipost;
	}

	public void setPipost(String pipost) {
		this.pipost = pipost;
	}

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public String getUniversities() {
		return universities;
	}

	public void setUniversities(String universities) {
		this.universities = universities;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getHmcType() {
		return hmcType;
	}

	public void setHmcType(String hmcType) {
		this.hmcType = hmcType;
	}

}

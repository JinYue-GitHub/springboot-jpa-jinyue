package com.goyo.project.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goyo.project.core.Comment;
@Table(name="Questionnaire_Survey")
@Entity
public class QuestionnaireSurvey implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2234857744018043261L;
	
	@Comment("主键")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name="ID")
	@Id
	private String id;
	@Comment("问卷名称")
	@Column(name="NAME")
	private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd HH24:mi:ss")
	@Comment("截止时间")
	@Column(name="END_TIME")
	private Date endTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH24:mi:ss")
	@Comment("发布时间")
//	@CreationTimestamp
	@Column(name="START_TIME",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH24:mi:ss")
	@Comment("答题时间")
//	@CreationTimestamp
	@Column(name="ANSWER_TIME",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date answerTime;
	@Comment("是否置于首页(1置于0不至于)")
	@Column(name="IS_HOME_PAGE")
	private String isHomePage;
	@Comment("父id")
	@Column(name="PARENT_ID")
	private String parentId;
	
	@Comment("是否是root(就是是否是问卷调查根节点,0是根节点,1不是根节点)")
	@Column(name="ROOT")
	private byte root;
	
	@Comment("是否已经填写(0没有填写,1已经填写)")
	@Column(name="FILL_STATUS")
	private byte fillStatus;
	
	@Comment("已经填写人数统计（只有根问卷才是统计后的人数）")
    @Column(name="PERSON_COUNT")
	private Long personCount=0L; 
	
	@Comment("当前账号uid")
	@Column(name="U_ID")
	private Integer uid;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ORGANIZATION_ID")
    private Organization organization;
	
	@OneToMany(mappedBy="questionnaireSurvey",fetch=FetchType.EAGER)
    @JsonManagedReference
	private List<Problem> problemList;//选项

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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}

	public String getIsHomePage() {
		return isHomePage;
	}

	public void setIsHomePage(String isHomePage) {
		this.isHomePage = isHomePage;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public byte getRoot() {
		return root;
	}

	public void setRoot(byte root) {
		this.root = root;
	}
	public byte getFillStatus() {
		return fillStatus;
	}

	public void setFillStatus(byte fillStatus) {
		this.fillStatus = fillStatus;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public List<Problem> getProblemList() {
		return problemList;
	}

	public void setProblemList(List<Problem> problemList) {
		this.problemList = problemList;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Long getPersonCount() {
		return personCount;
	}

	public void setPersonCount(Long personCount) {
		this.personCount = personCount;
	}
	
	
}

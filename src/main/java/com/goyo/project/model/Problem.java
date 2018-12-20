package com.goyo.project.model;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import com.goyo.project.model.ProblemOption;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goyo.project.core.Comment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @author: JinYue
 * @ClassName: Problem 
 * @Description: 题
 */
@Table(name="PROBLEM")
@Entity
public class Problem implements Serializable {
    /**
     * 序列化id
     */
	private static final long serialVersionUID = -1340512369490531011L;
	
	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	@Comment("题目")
    @Column(name="TITLE")
	private String title;
	@Comment("选择题类型,单选或者多选")
    @Column(name="TYPE")
	private String type;//单选或者多选
	@Comment("创建者")
    @Column(name="CREATE_PERSON_ID")
	private String createPersonId; 
	@Comment("父id")
	@Column(name="PARENT_ID")
	private String parentId;
	@Comment("创建时间") 
	@CreationTimestamp 
    @Column(name="CREATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date createDate;
	@Comment("更新时间") 
	@UpdateTimestamp
    @Column(name="UPDATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date updateDate;
	
	@Comment("是否是root(就是是否是题根节点,0是根节点,1不是根节点)")
	@Column(name="ROOT")
	private byte root;
	
	@Comment("题人数统计（只有根问题才是统计后的人数）")
    @Column(name="PERSON_COUNT")
	private Long personCount=0L; 
	
	@OneToMany(mappedBy="problem",fetch=FetchType.EAGER)
    @JsonManagedReference
	private List<ProblemOption> optionList;//选项
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="QUESTIONNAIRESURVEY_ID")
	@JsonBackReference
    private QuestionnaireSurvey questionnaireSurvey;// 问卷调查

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(String createPersonId) {
		this.createPersonId = createPersonId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public byte getRoot() {
		return root;
	}

	public void setRoot(byte root) {
		this.root = root;
	}

	public List<ProblemOption> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<ProblemOption> optionList) {
		this.optionList = optionList;
	}

	public QuestionnaireSurvey getQuestionnaireSurvey() {
		return questionnaireSurvey;
	}

	public void setQuestionnaireSurvey(QuestionnaireSurvey questionnaireSurvey) {
		this.questionnaireSurvey = questionnaireSurvey;
	}

	public Long getPersonCount() {
		return personCount;
	}

	public void setPersonCount(Long personCount) {
		this.personCount = personCount;
	}
	
}
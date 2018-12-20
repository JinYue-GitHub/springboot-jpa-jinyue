package com.goyo.project.model;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.goyo.project.core.Comment;
import java.io.Serializable;
import java.util.Date;
/**
 * @author: JinYue
 * @ClassName: Option 
 * @Description: 题的选项
 */
@Table(name="PROBLEM_OPTION")
@Entity
public class ProblemOption implements Serializable {
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
	@Comment("选项名称")
    @Column(name="NAME")
	private String name;
	@Comment("选项值")
    @Column(name="VALUE")
	private String value;
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
	
	@Comment("选项人数统计（只有根选项才是统计后的人数）")
    @Column(name="PERSON_COUNT")
	private Long personCount=0L; 
	
	@Comment("是否是root(就是是否是题选项根节点,0是根节点,1不是根节点)")
	@Column(name="ROOT")
	private byte root;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PROBLEM_ID")
	@JsonBackReference
    private Problem problem;// 题

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
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
	
	public Long getPersonCount() {
		return personCount;
	}

	public void setPersonCount(Long personCount) {
		this.personCount = personCount;
	}

	public byte getRoot() {
		return root;
	}

	public void setRoot(byte root) {
		this.root = root;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
}
package com.goyo.project.model;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import com.goyo.project.core.Comment;
import java.io.Serializable;
import java.util.Date;
/**
 * @author: JinYue
 * @ClassName: PetitionReport 
 * @Description: 信访举报类
 */
@Table(name="PETITION_REPORT")
@Entity
public class PetitionReport implements Serializable {
	/**
     * 序列化id
     */
	private static final long serialVersionUID = -5233624125132725257L;
	
	
	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	@Comment("举报标题")
    @Column(name="NAME")
	private String name;
	
    @Comment("举报人人员姓名")
    @Column(name="PERSON_NAME")
	private String personName;
   
	@Comment("举报人身份证号")
    @Column(name="ID_NUMBER")
	private String idNumber;  
    
	@Comment("举报人联系方式")
    @Column(name="CONTACT")
	private String contact;  
    
	@Comment("举报人政治面貌")
    @Column(name="POLITICAL")
	private String political;  
	
	@Comment("举报人级别")
    @Column(name="PERSON_LEVEL")
	private String personLevel;  
	
	@Comment("被举报人")
	@Column(name="REPORTED_PERSON_NAME")
	private String reportedPersonName;  
	
	@Comment("被举报人单位")
	@Column(name="REPORTED_PERSON_UNIT")
	private String reportedPersonUnit;  
	@Comment("被举报人职务")
	@Column(name="REPORTED_PERSON_POST")
	private String reportedPersonPost;  
	@Comment("被举报人所在地区")
	@Column(name="REPORTED_PERSON_AREA")
	private String reportedPersonArea;  
	@Comment("被举报人级别")
	@Column(name="REPORTED_PERSON_LEVEL")
	private String reportedPersonLevel;  
	@Comment("举报问题类别")
	@Column(name="PROBLEM_CATEGORY")
	private String problemCategory;  
	@Comment("举报问题细类")
	@Column(name="DETAILED_CATEGORY_OF_PROBLEM")
	private String detailedCategoryOfProblem;  
	@Comment("举报内容") 
    @Column(name="CONTENT")
    private String content;
	@Comment("举报图片") 
    @Column(name="IMAGE")
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
    private byte[] image;
	@Comment("举报创建时间") 
	@CreationTimestamp 
    @Column(name="CREATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date createDate;
	@Comment("举报更新时间") 
	@UpdateTimestamp
    @Column(name="UPDATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date updateDate;
	  
	@Transient
    private String pageNum;//不映射数据库，只是用于接收值
    @Transient
    private String pageSize;//不映射数据库，只是用于接收值
  
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
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPolitical() {
		return political;
	}
	public void setPolitical(String political) {
		this.political = political;
	}

	public String getPersonLevel() {
		return personLevel;
	}
	public void setPersonLevel(String personLevel) {
		this.personLevel = personLevel;
	}
	
	public String getReportedPersonName() {
		return reportedPersonName;
	}
	public void setReportedPersonName(String reportedPersonName) {
		this.reportedPersonName = reportedPersonName;
	}
	public String getReportedPersonUnit() {
		return reportedPersonUnit;
	}
	public void setReportedPersonUnit(String reportedPersonUnit) {
		this.reportedPersonUnit = reportedPersonUnit;
	}
	public String getReportedPersonPost() {
		return reportedPersonPost;
	}
	public void setReportedPersonPost(String reportedPersonPost) {
		this.reportedPersonPost = reportedPersonPost;
	}
	public String getReportedPersonArea() {
		return reportedPersonArea;
	}
	public void setReportedPersonArea(String reportedPersonArea) {
		this.reportedPersonArea = reportedPersonArea;
	}
	public String getReportedPersonLevel() {
		return reportedPersonLevel;
	}
	public void setReportedPersonLevel(String reportedPersonLevel) {
		this.reportedPersonLevel = reportedPersonLevel;
	}
	public String getProblemCategory() {
		return problemCategory;
	}
	public void setProblemCategory(String problemCategory) {
		this.problemCategory = problemCategory;
	}
	public String getDetailedCategoryOfProblem() {
		return detailedCategoryOfProblem;
	}
	public void setDetailedCategoryOfProblem(String detailedCategoryOfProblem) {
		this.detailedCategoryOfProblem = detailedCategoryOfProblem;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
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
	
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
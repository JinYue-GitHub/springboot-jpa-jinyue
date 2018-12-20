package com.goyo.project.model;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goyo.project.core.Comment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @author: JinYue
 * @ClassName: Article 
 * @Description: 文章类
 */
@Table(name="ARTICLE")
@Entity
public class Article implements Serializable {
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
	@Comment("名字")
    @Column(name="NAME")
	private String name;
	@Comment("详细类型:例如 特色活动")
    @Column(name="TYPE_IDS")
	private String typeIds;
	@Comment("发布信息的组织(创建者)")
    @Column(name="SOURCE_ORGANIZATION")
	private String sourceOrganization;  
	@Comment("接收信息的组织(分享者)")
    @Column(name="TARGET_ORGANIZATION")
	private String targetOrganization;  
	@Comment("文章内容") 
    @Column(name="CONTENT")
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
    private String content;
	@Comment("图片") 
    @Column(name="IMAGE")
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
    private byte[] image;
	@Comment("隶属于xxx板块：如-> 廉政建设/工作动态") 
    @Column(name="SUBJECTION")
	private String subjection;
	@Comment("权重(1是大权重,越往后权重越小(如1,2,3:1是排在最前面,3最后))") 
    @Column(name="PRIORITY")
	private String priority;
	@Comment("是否公开(0不公开1公开)") 
    @Column(name="ISPUBLIC")
	private String isPublic;
	@Comment("存储上传时 原来的文件名称(多文件逗号分隔)") 
    @Column(name="FILE_NAME")
	private String fileName;
	@Comment("存储路径 + 上传后自动编号的文件名称(多文件逗号分隔)") 
    @Column(name="FILE_PATH")
	private String filePath;
	@Comment("创建时间") 
	@CreationTimestamp 
    @Column(name="CREATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	@Comment("更新时间") 
	@UpdateTimestamp
    @Column(name="UPDATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;
	
	@ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name = "SysUserArticle", joinColumns = { @JoinColumn(name = "ARTICLE_ID") }, inverseJoinColumns ={@JoinColumn(name = "U_ID") })
    private List<UserInfo> userInfoList;// 人员
	
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
	public String getTypeIds() {
		return typeIds;
	}
	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
	}
	public String getSourceOrganization() {
		return sourceOrganization;
	}
	public void setSourceOrganization(String sourceOrganization) {
		this.sourceOrganization = sourceOrganization;
	}
	public String getTargetOrganization() {
		return targetOrganization;
	}
	public void setTargetOrganization(String targetOrganization) {
		this.targetOrganization = targetOrganization;
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
	public String getSubjection() {
		return subjection;
	}
	public void setSubjection(String subjection) {
		this.subjection = subjection;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}
	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}
}
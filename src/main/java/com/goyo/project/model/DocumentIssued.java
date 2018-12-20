package com.goyo.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.goyo.project.core.Comment;

/**
 * @author: JinYue
 * @ClassName: DocumentIssued 
 * @Description: 文件发布(独立权限管理(本质是临时的权限)，根据直属部门集合区分，用户没有查看文件的权限)
 */
@Table(name="DOCUMENT_ISSUED")
@Entity
public class DocumentIssued implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3519541086199853516L;
	
	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	@Comment("发布信息的组织")
    @Column(name="ORGANIZATION")
	private String organization;
	@Comment("组织权限集合")
    @Column(name="P_C_F_O")
	private String permissionCollectionForOrganization;
	@Comment("文件内容") 
    @Column(name="CONTENT")
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
    private byte[] content;
	@Comment("隶属于xxx板块：如-> 廉政建设/工作动态,首页/重点工作。。。") 
    @Column(name="SUBJECTION")
	private String subjection;
	@Comment("权重(1是大权重,越往后权重越小(如1,2,3:1是排在最前面,3最后))") 
    @Column(name="PRIORITY")
	private String priority;
	@Comment("是否公开(0不公开1公开)") 
    @Column(name="ISPUBLIC")
	private String isPublic;
	@Comment("创建时间") 
	@CreationTimestamp 
    @Column(name="CREATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date createDate;
	@Comment("更新时间") 
	@UpdateTimestamp
    @Column(name="UPDATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date updateDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getPermissionCollectionForOrganization() {
		return permissionCollectionForOrganization;
	}
	public void setPermissionCollectionForOrganization(String permissionCollectionForOrganization) {
		this.permissionCollectionForOrganization = permissionCollectionForOrganization;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
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
	
	
	
	
	
}

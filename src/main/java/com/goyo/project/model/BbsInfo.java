package com.goyo.project.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goyo.project.core.Comment;

/**
 * @author: JinYue
 * @ClassName: BbsInfo 
 * @Description: 书记面对面表
 */
@Table(name="BBSINFO")
@Entity
public class BbsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8091782167628405410L;

	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	
	@Comment("标题")
    @Column(name="TITLE")
	private String title;
	
	@Comment("内容")
    @Column(name="CONTENT")
	private String content;
	
	@Comment("图片") 
    @Column(name="IMAGE")
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
    private byte[] image;
	
	@Comment("发布用户")
    @Column(name="USERNO")
	private String userno;
	
	@Comment("接收用户")
    @Column(name="RECEIVEUSERNO")
	private String receiveUserno;
	
	@Comment("发布时间")
	@CreationTimestamp 
    @Column(name="PUBTIME",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pubtime;

	
	
	@Transient
    private String pageNum;//不映射数据库，只是用于接收值
    @Transient
    private String pageSize;//不映射数据库，只是用于接收值
    

    
    
	public String getReceiveUserno() {
		return receiveUserno;
	}

	public void setReceiveUserno(String receiveUserno) {
		this.receiveUserno = receiveUserno;
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

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public Date getPubtime() {
		return pubtime;
	}

	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}

	
}

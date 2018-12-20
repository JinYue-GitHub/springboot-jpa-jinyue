package com.goyo.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goyo.project.core.Comment;

/**
 * @author: JinYue
 * @ClassName: BBSMSG 
 * @Description:  消息回复表
 */
/**
 * @author 9251209
 *
 */
@Table(name="BBSMSG")
@Entity
public class BbsMsg implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4375307822085644681L;

	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	
	@Comment("回复内容")
    @Column(name="BBSID")
    private String bbsid;
	
	
	@Comment("回复内容")
    @Column(name="CONTENT")
	private String content;
	
	@Comment("用户")
    @Column(name="USERNO")
	private String userno;
	
	@Comment("用户名称")
    @Column(name="USERNAME")
	private String userName;
	
	@Comment("时间")
	@CreationTimestamp 
	@Column(name="PUBTIME",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pubtime;
	
	
	@Transient
    private String pageNum;//不映射数据库，只是用于接收值
    @Transient
    private String pageSize;//不映射数据库，只是用于接收值
    

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


	
	public String getBbsid() {
		return bbsid;
	}

	public void setBbsid(String bbsid) {
		this.bbsid = bbsid;
	}

	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}

package com.goyo.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goyo.project.core.Comment;

/**
 * @author: JinYue
 * @ClassName: Article
 * @Description: 民生诉求表
 */
@Table(name = "SEEKHELP")
@Entity
public class SeekHelp implements Serializable{
	

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8197523339256709910L;

	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	
	@Comment("诉求人姓名")
    @Column(name="NAME")
	private String name;
	
	@Comment("身份证号")
    @Column(name="CODE")
	private String code;
	
	
	@Comment("述求内容")
    @Column(name="CONTENT")
	private String content;
	
	@Comment("日期")
	@Column(name = "PUBTIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pubTime;
	
	@Comment("所属部门")
	@Column(name = "DEPTNO")
	private String deptno;
	
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	
	
	
	
}

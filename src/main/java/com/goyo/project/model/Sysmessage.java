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

/**
 * @author: JinYue
 * @ClassName: Article
 * @Description: 民生诉求表
 */
@Table(name = "SYS_MESSAGE")
@Entity
public class Sysmessage implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8038862602341737255L;

	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	
	@Comment("发布人")
    @Column(name="RELUSERNO")
	private String relUserno;
	
	@Comment("发布部门")
    @Column(name="RELDEPTNO")
	private String relDeptno;
	
	@Comment("发布日期")
	@Column(name = "RELTIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date relTime;
	
	@Comment("接收人")
    @Column(name="RECUSERNO")
	private String recUserno;
	
	@Comment("接收部门")
    @Column(name="RECDEPTNO")
	private String recDeptno;
	
	@Comment("接收状态")
    @Column(name="RECSTATE")
	private String recState;
	
	@Comment("消息内容id")
    @Column(name="ARTICEID")
	private String articeId;
	
	@Comment("消息内容类别")
    @Column(name="SUBJECTION")
	
	private String subjection;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRelUserno() {
		return relUserno;
	}

	public void setRelUserno(String relUserno) {
		this.relUserno = relUserno;
	}

	public String getRelDeptno() {
		return relDeptno;
	}

	public void setRelDeptno(String relDeptno) {
		this.relDeptno = relDeptno;
	}

	public Date getRelTime() {
		return relTime;
	}

	public void setRelTime(Date relTime) {
		this.relTime = relTime;
	}

	public String getRecUserno() {
		return recUserno;
	}

	public void setRecUserno(String recUserno) {
		this.recUserno = recUserno;
	}

	public String getRecDeptno() {
		return recDeptno;
	}

	public void setRecDeptno(String recDeptno) {
		this.recDeptno = recDeptno;
	}

	public String getRecState() {
		return recState;
	}

	public void setRecState(String recState) {
		this.recState = recState;
	}

	public String getArticeId() {
		return articeId;
	}

	public void setArticeId(String articeId) {
		this.articeId = articeId;
	}

	public String getSubjection() {
		return subjection;
	}

	public void setSubjection(String subjection) {
		this.subjection = subjection;
	}
	
	
	
}

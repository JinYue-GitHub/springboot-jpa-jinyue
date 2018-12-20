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
 * @Description: 信访案件表
 */
@Table(name = "CASEINFO")
@Entity
public class Caseinfo implements Serializable {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -657130026779261714L;

	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Comment("来访人姓名")
	@Column(name = "NAMES")
	private String names;

	@Comment("来访人数")
	@Column(name = "NUMBERS")
	// @Range(min = 1, max = 150, message="年龄必须在1-150之间!") //age need between 1
	// and 150
	private String numbers;

	@Comment("来访人单位")
	@Column(name = "UNIT")
	private String unit;

	@Comment("联系人姓名")
	@Column(name = "LINKMAN")
	private String linkman;

	@Comment("联系人联系人身份证号")
	@Column(name = "LINKMANCODE")
	private String linkmanCode;

	@Comment("联系电话")
	@Column(name = "LINKMANTEL")
	private String linkmanTel;

	@Comment("诉求详情")
	@Column(name = "CONTENT")
	private String content;

	@Comment("案件来源")
	@Column(name = "SOURCE")
	private String source;

	@Comment("接待时间")
	@Column(name = "RECDATE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recDate;

	@Comment("接待人")
	@Column(name = "RECNAME")
	private String recName;

	@Comment("处理状态")
	@Column(name = "STATE")
	private String state;

	@Comment("进度详情")
	@Column(name = "PRROGRESS")
	private String progress;

	@Comment("附件")
	@Column(name = "FILEURL")
	private String fileUrl;

	@Comment("附件原始名称")
	@Column(name = "FILENAME")
	private String fileName;
	
	
	@Comment("录入人员")
	@Column(name = "USERNO")
	private String userno;

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

	

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkmanCode() {
		return linkmanCode;
	}

	public void setLinkmanCode(String linkmanCode) {
		this.linkmanCode = linkmanCode;
	}

	public String getLinkmanTel() {
		return linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getRecDate() {
		return recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

}

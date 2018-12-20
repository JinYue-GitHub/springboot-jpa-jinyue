package com.goyo.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.goyo.project.core.Comment;
@Table(name="DATADIC_ITEMS")
@Entity
public class DataDictionaryItems {
	@Comment("数据字典项编码(主键)")
	@Column(name="DATAITEM_CODE")
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String dataitemCode;
	@Comment("数据字典项名称")
	@Column(name="DATAITEM_NAME")
	private String dataitemName;
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="GROUP_CODE")
	private DataDictionaryGroups dataDictionaryGroups;
	public String getDataitemCode() {
		return dataitemCode;
	}
	public void setDataitemCode(String dataitemCode) {
		this.dataitemCode = dataitemCode;
	}
	public String getDataitemName() {
		return dataitemName;
	}
	public void setDataitemName(String dataitemName) {
		this.dataitemName = dataitemName;
	}
	public DataDictionaryGroups getDataDictionaryGroups() {
		return dataDictionaryGroups;
	}
	public void setDataDictionaryGroups(DataDictionaryGroups dataDictionaryGroups) {
		this.dataDictionaryGroups = dataDictionaryGroups;
	}
	
	
}

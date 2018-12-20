package com.goyo.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goyo.project.core.Comment;

/**
 * @author JinYue
 * @ClassName DatadicGroups
 * @Description 数据字典组
 */
@Table(name="DATADIC_GROUPS")
@Entity
public class DataDictionaryGroups implements Serializable{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7188389493777112443L;
	@Comment("组编码(主键)")
	@Column(name="GROUP_CODE")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Id
	private String groupCode;
	@Comment("组名")
	@Column(name="GROUP_NAME")
	private String groupName;
	
	@OneToMany(mappedBy="dataDictionaryGroups",fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<DataDictionaryItems> dataDictionaryItems;// 数据字典项
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<DataDictionaryItems> getDataDictionaryItems() {
		return dataDictionaryItems;
	}
	public void setDataDictionaryItems(List<DataDictionaryItems> dataDictionaryItems) {
		this.dataDictionaryItems = dataDictionaryItems;
	}
	
	
}

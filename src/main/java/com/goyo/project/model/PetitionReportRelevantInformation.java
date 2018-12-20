package com.goyo.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.goyo.project.core.Comment;

/**
 * @author: JinYue
 * @ClassName: PetitionReportRelevantInformation 
 * @Description: 信访举报相关信息类
 */
@Table(name="PETITION_REPORT_RELEVANT_INFO")
@Entity
public class PetitionReportRelevantInformation implements Serializable{

	/**
     * 序列化id
     */
	private static final long serialVersionUID = 4154109480954375274L;
	
	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	@Comment("类型名称")
    @Column(name="TYPE_NAME")
	private String typeName;
	@Comment("具体的值")
    @Column(name="VALUE")
	private String value;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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

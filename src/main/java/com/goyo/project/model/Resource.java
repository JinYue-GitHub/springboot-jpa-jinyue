/*package com.goyo.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.goyo.project.core.Comment;

*//**
 * @author: JinYue
 * @ClassName: Resource 
 * @Description: 资源类
 *//*
@Table(name="RESOURCE")
@Entity
public class Resource implements Serializable{
	*//**
	 * 序列化id
	 *//*
	private static final long serialVersionUID = 2008259968297699622L;
	@Comment("主键") 
	@Column(name="ID")
	@Id
    @GeneratedValue
    private Integer id; 
	@Comment("资源name") 
	@Column(name="NAME")
    private String name; 
	@Comment("资源url") 
	@Column(name="URL")
    private String url; 
	@Comment("资源父id") 
	@Column(name="PARENT_ID")
    private String parentId; 
	@Comment("资源ids") 
	@Column(name="IDS")
    private String ids; 
	@Comment("是否可用,如果不可用将不会添加给用户") 
	@Column(name="AVAILABLE")
    private Short available;
}
*/
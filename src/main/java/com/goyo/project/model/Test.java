package com.goyo.project.model;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import com.goyo.project.core.Comment;
import java.io.Serializable;
/**
 * @author: JinYue
 * @ClassName: TEST 
 * @Description: 测试类
 */
@Table(name="TEST")
@Entity
public class Test implements Serializable {
	/**
     * 序列化id
     */
	private static final long serialVersionUID = -1405575712649065414L;
	
	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	@Comment("名字")
    @Column(name="NAME")
	private String name;
	@Comment("身份证")
    @Column(name="CARD")
	private String card;
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
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
}
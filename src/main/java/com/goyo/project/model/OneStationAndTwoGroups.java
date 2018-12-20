package com.goyo.project.model;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import com.goyo.project.core.Comment;
import java.io.Serializable;
import java.util.Date;
/**
 * @ClassName: OneStationAndTwoGroups 
 * @Description: 一站两群
 */
@Table(name="ONE_STATION_AND_TWO_GROUPS")
@Entity
public class OneStationAndTwoGroups implements Serializable {
	/**
     * 序列化id
     */
	private static final long serialVersionUID = -8245668847448345122L;
	
	@Comment("主键") 
	@Column(name = "ID")
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
	@Comment("名称")
    @Column(name="NAME")
	private String name;//和一站名称对应
	@Comment("创建组织(部门)")//和名称对应
    @Column(name="SOURCE_ORGANIZATION")
	private String sourceOrganization;  
	@Comment("一站负责人") 
    @Column(name="ONE_STATION_PERSON_ID")
	private String oneStationPersonId;//可以存名字或者人员的id
	@Comment("一站人数") 
    @Column(name="ONE_STATION_PERSON_COUNT")
	private String oneStationPersonCount;
	@Comment("微信群") 
    @Column(name="WECHAT")
	private String WECHAT;
	@Comment("微信群负责人") 
    @Column(name="WECHAT_PERSONID")
	private String wechatPersonId;
	@Comment("微信群人数") 
    @Column(name="WECHAT_PERSON_COUNT")
	private String wechatPersonCount;
	@Comment("QQ群") 
    @Column(name="QQ")
	private String qq;
	@Comment("QQ群负责人") 
    @Column(name="QQ_PERSONID")
	private String qqPersonId;
	@Comment("QQ群人数") 
    @Column(name="QQ_PERSON_COUNT")
	private String qqPersonCount;
	@Comment("创建时间") 
	@CreationTimestamp 
    @Column(name="CREATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date createDate;
	@Comment("更新时间") 
	@UpdateTimestamp
    @Column(name="UPDATEDATE",columnDefinition="TIMESTAMP DEFAULT SYSDATE")
	private Date updateDate;
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
	public String getSourceOrganization() {
		return sourceOrganization;
	}
	public void setSourceOrganization(String sourceOrganization) {
		this.sourceOrganization = sourceOrganization;
	}
	public String getOneStationPersonId() {
		return oneStationPersonId;
	}
	public void setOneStationPersonId(String oneStationPersonId) {
		this.oneStationPersonId = oneStationPersonId;
	}
	public String getOneStationPersonCount() {
		return oneStationPersonCount;
	}
	public void setOneStationPersonCount(String oneStationPersonCount) {
		this.oneStationPersonCount = oneStationPersonCount;
	}
	public String getWECHAT() {
		return WECHAT;
	}
	public void setWECHAT(String wECHAT) {
		WECHAT = wECHAT;
	}
	public String getWechatPersonId() {
		return wechatPersonId;
	}
	public void setWechatPersonId(String wechatPersonId) {
		this.wechatPersonId = wechatPersonId;
	}
	public String getWechatPersonCount() {
		return wechatPersonCount;
	}
	public void setWechatPersonCount(String wechatPersonCount) {
		this.wechatPersonCount = wechatPersonCount;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getQqPersonId() {
		return qqPersonId;
	}
	public void setQqPersonId(String qqPersonId) {
		this.qqPersonId = qqPersonId;
	}
	public String getQqPersonCount() {
		return qqPersonCount;
	}
	public void setQqPersonCount(String qqPersonCount) {
		this.qqPersonCount = qqPersonCount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
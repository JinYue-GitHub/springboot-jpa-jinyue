package com.goyo.project.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goyo.project.core.Comment;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
/**
 * @author: JinYue
 * @ClassName: SysRole 
 * @Description: 角色类
 */
@Table(name="SYS_ROLE")
@Entity
public class SysRole implements Serializable{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7474393050430446652L;
	@Comment("主键") 
	@Column(name="ID")
	@Id
    @GeneratedValue
    private Integer id; // 编号
	@Comment("角色标识程序中判断使用,如admin,这个是唯一的") 
	@Column(name="ROLE")
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
	@Comment("角色描述,UI界面显示使用") 
	@Column(name="DESCRIPTION")
    private String description; // 角色描述,UI界面显示使用
//    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户
	@Comment("是否可用,如果不可用将不会添加给用户") 
	@Column(name="AVAILABLE")
    private Short available;

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="SysRolePermission",joinColumns={@JoinColumn(name="ROLE_ID")},inverseJoinColumns={@JoinColumn(name="PERMISSION_ID")})
    private List<SysPermission> permissions;

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name="SysUserRole",joinColumns={@JoinColumn(name="ROLE_ID")},inverseJoinColumns={@JoinColumn(name="U_ID")})
    private List<UserInfo> userInfos;// 一个角色对应多个用户

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
//    public Boolean getAvailable() {
//        return available;
//    }
//
//    public void setAvailable(Boolean available) {
//        this.available = available;
//    }

    public Short getAvailable() {
		return available;
	}

	public void setAvailable(Short available) {
		this.available = available;
	}

	public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }

    @JsonIgnore
    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }
    @Override
    public boolean equals(Object o) {
    	if (this == o) {
    		return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	    	return false;
	    }
	    SysRole t = (SysRole) o;
	    return Objects.equals(this.id, t.id);
    }
    @Override
    public int hashCode() {
    	return this.id.intValue();
    }
}
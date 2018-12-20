package com.goyo.project.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goyo.project.core.Comment;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
/**
 * @author: JinYue
 * @ClassName: SysPermission 
 * @Description: 权限类
 */
@Table(name="SYS_PERMISSION")
@Entity
public class SysPermission implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8633995846648354688L;
	@Comment("主键")
	@Column(name="ID")
	@Id
	@GeneratedValue
    private Integer id;//主键.
	@Comment("名称")
	@Column(name="NAME")
    private String name;//名称.
//  @Column(columnDefinition="enum('menu','button')")
	@Comment("资源类型，[menu|button]")
    @Column(name="RESOURCE_TYPE",columnDefinition="VARCHAR2(255) DEFAULT 'button'") 
    private String resourceType;//资源类型，[menu|button]
	@Comment("资源路径")
	@Column(name="URL")
    private String url;//资源路径.
	@Comment("权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view")
	@Column(name="PERMISSION")
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
	@Comment("父编号")
	@Column(name="PARENT_ID")
    private Long parentId; //父编号
	@Comment("父编号列表")
	@Column(name="PARENT_IDS")
    private String parentIds; //父编号列表
	@Comment("唯一标识资源权限IDS")
	@Column(name="IDS")
    private String ids; //资源权限IDS
//    private Boolean available = Boolean.FALSE; 是否可用,如果不可用将不会添加给用户
	@Comment("是否可用,如果不可用将不会添加给用户")
	@Column(name="AVAILABLE")
    private Short available;
    
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="SysRolePermission",joinColumns={@JoinColumn(name="PERMISSION_ID")},inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
    private List<SysRole> roles;
    
    //资源权限对应的	组织（例如：组织部）
    @ManyToMany//(fetch= FetchType.EAGER)
    @JoinTable(name="OrganizationPermission",joinColumns={@JoinColumn(name="PERMISSION_ID")},inverseJoinColumns={@JoinColumn(name="ORGANIZATION_ID")})
    private transient List<Organization> organizations;

    
    
    
    
    
    public SysPermission() {
		super();
	}

	public SysPermission(Integer id, String name, String resourceType, String url, String permission, Long parentId,
			String parentIds, Short available, List<SysRole> roles) {
		super();
		this.id = id;
		this.name = name;
		this.resourceType = resourceType;
		this.url = url;
		this.permission = permission;
		this.parentId = parentId;
		this.parentIds = parentIds;
		this.available = available;
		this.roles = roles;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
    
    public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Short getAvailable() {
		return available;
	}

	public void setAvailable(Short available) {
		this.available = available;
	}

	@JsonIgnore
	public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
//    this.id = id;
//	this.name = name;
//	this.resourceType = resourceType;
//	this.url = url;
//	this.permission = permission;
//	this.parentId = parentId;
//	this.parentIds = parentIds;
//	this.available = available;
//	this.roles = roles;
    
    public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}
    
    @Override
    public String toString() {
    	return "["+this.id+","+this.name+","+this.resourceType+","+this.url+","+this.permission+","+this.parentId+","+this.parentIds+","+this.available+","+this.roles+"]";
    }

    @Override
    public boolean equals(Object o) {
    	if (this == o) {
    		return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	    	return false;
	    }
	    SysPermission t = (SysPermission) o;
	    return Objects.equals(this.id, t.id);
    }
    @Override
    public int hashCode() {
    	return this.id.intValue();
    }
    
    
}
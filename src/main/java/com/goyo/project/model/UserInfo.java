package com.goyo.project.model;

import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goyo.project.core.Comment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 * @author: JinYue
 * @ClassName: UserInfo 
 * @Description: 用户类
 */
@Table(name="USER_INFO")
@Entity
public class UserInfo implements Serializable {
    
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6441997020641122861L;
	@Comment("主键") 
	@Column(name = "U_ID")
    @Id
    @GeneratedValue
    private Integer uid;
	@Comment("帐号")
    @Column(name="USERNAME",unique =true)
    private String username;//帐号
	@Comment("手机号") 
    @Column(name="PHONE_NUMBER",unique =true)
    private String phoneNumber;//手机号
    @Comment("名称（昵称或者真实姓名，不同系统不同定义）") 
    @Column(name="NAME")
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    @Comment("密码") 
    @Column(name="PASSWORD")
    private String password; //密码;
    @Comment("加密密码的盐") 
    @Column(name="SALT")
    private String salt;//加密密码的盐
    @Comment("用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定") 
    @Column(name="STATE")
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.

    @Comment("用户状态,0:不是超管, 1:是root超管,2:是root的下级超管") 
    @Column(name="SUPER_ADMINISTRATOR")
    private byte superAdministrator;//用户状态,0:不是超管, 1:是超管
    
    @Comment("年龄") 
    @Column(name="AGE")
//    @Range(min = 1, max = 150, message="年龄必须在1-150之间!") //age need between 1 and 150
    private int age;
    
    @Comment("性别") 
    @Column(name="SEX")
    private String sex;
    
    @Comment("出生日期") 
    @Column(name="BIRTHDAY")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ORGANIZATION_ID")
    private Organization organization;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PARTY_ORGANIZATION_ID")
    private PartyOrganization partyOrganization;
    
    @Comment("岗位") 
    @Column(name="POST")
    private String post;
    
    @Comment("党内职务(全职)") 
    @Column(name="FIPOST")
    private String fipost;
    
    @Comment("党内职务(兼职)") 
    @Column(name="PIPOST")
    private String pipost;
    
    @Comment("政治面貌") 
    @Column(name="POLITICAL")
    private String political;
    
    @Comment("学历") 
    @Column(name="QUALIFICATION")
    private String qualification;
    
    @Comment("毕业院校") 
    @Column(name="UNIVERSITIES")
    private String universities;
    
    @Comment("家庭住址") 
    @Column(name="HOME_ADDRESS")
    private String homeAddress;
    
    @Comment("人员情况类别(三不党员   发展党员    积极分子)") 
    @Column(name="QK_TYPE")
    private String qkType;
    
    @Comment("花名册类别(团干部花名册  团员花名册  青年花名册  大学生花名册)") 
    @Column(name="HMC_TYPE")
    private String hmcType;
    
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "U_ID") }, inverseJoinColumns ={@JoinColumn(name = "ROLE_ID") })
    private List<SysRole> roleList;// 一个用户具有多个角色
    
    @JsonIgnore
    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name = "SysUserArticle", joinColumns = { @JoinColumn(name = "U_ID") }, inverseJoinColumns ={@JoinColumn(name = "ARTICLE_ID") })
    private List<Article> articleList;// 文章
    
    @Transient
    private String pageNum;//不映射数据库，只是用于接收值
    @Transient
    private String pageSize;//不映射数据库，只是用于接收值
    
    

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
    
    public byte getSuperAdministrator() {
		return superAdministrator;
	}

	public void setSuperAdministrator(byte superAdministrator) {
		this.superAdministrator = superAdministrator;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public PartyOrganization getPartyOrganization() {
		return partyOrganization;
	}

	public void setPartyOrganization(PartyOrganization partyOrganization) {
		this.partyOrganization = partyOrganization;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getFipost() {
		return fipost;
	}

	public void setFipost(String fipost) {
		this.fipost = fipost;
	}

	public String getPipost() {
		return pipost;
	}

	public void setPipost(String pipost) {
		this.pipost = pipost;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getUniversities() {
		return universities;
	}

	public void setUniversities(String universities) {
		this.universities = universities;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getQkType() {
		return qkType;
	}

	public void setQkType(String qkType) {
		this.qkType = qkType;
	}

	public String getHmcType() {
		return hmcType;
	}

	public void setHmcType(String hmcType) {
		this.hmcType = hmcType;
	}

	public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
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

	/**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }
    //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
    @Override
    public boolean equals(Object o) {
    	if (this == o) {
    		return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	    	return false;
	    }
	    UserInfo t = (UserInfo) o;
	    return Objects.equals(this.uid, t.uid);
    }
    @Override
    public int hashCode() {
    	return this.uid.intValue();
    }
    
}
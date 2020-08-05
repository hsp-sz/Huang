package cn.keyi.bye.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="sysuser")
public class SysUser implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "userId")
	private Long userId;
	// 用户账号，用于在代码中处理
	@Column(unique = true, name = "userName")
	private String userName;
	// 用户昵称，用于在 UI 中显示
	@Column(name = "userAlias")
	private String userAlias;
	// 密码
	private String password;
	// 密码盐
	private String salt;
	// 账户状态：0-待验证，1-正常，2-冻结
	private Short state;
	// 所属角色。用户——角色：多对多关系。一个用户可以从属于不同的角色	
	@ManyToMany(fetch = FetchType.EAGER)	// 立刻加载，在查询主对象的时候同时加载关联对象
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "userId")}, 
    	inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roles;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAlias() {
		return userAlias;
	}
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
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
	/**
	 * 对盐重新进行定义，由  userName 和 salt 组合，使其更加不容易被破解
	 * @return
	 */
	public String getCredentialsSalt() {
        return this.userName + this.salt;
    }
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
}

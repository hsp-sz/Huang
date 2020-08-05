package cn.keyi.bye.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="syspermission")
public class SysPermission implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "permissionId")
	private Long permissionId;
	// 权限描述
	@Column(name = "permissionTitle")
	private String permissionTitle;
	// 权限字符串，例：role:create,role:update,role:delete,role:view
	@Column(name = "permissionCode")
	private String permissionCode;
	// 资源路径
	@Column(name = "resourceUrl")
	private String resourceUrl;
	// 资源类型，[ menu | button ]
	@Column(columnDefinition = "enum('menu', 'button')", name = "resourceType")
	private String resourceType;
	// 是否可用
	@Column(name = "permissionAvailable")
	private Boolean permissionAvailable = Boolean.TRUE;
	// 一个权限可以被不同的角色所拥有
	@ManyToMany
	@JsonIgnore
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "permissionId")}, 
    	inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roles;
	
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionTitle() {
		return permissionTitle;
	}
	public void setPermissionTitle(String permissionTitle) {
		this.permissionTitle = permissionTitle;
	}
	public String getPermissionCode() {
		return permissionCode;
	}
	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Boolean getPermissionAvailable() {
		return permissionAvailable;
	}
	public void setPermissionAvailable(Boolean permissionAvailable) {
		this.permissionAvailable = permissionAvailable;
	}
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
}

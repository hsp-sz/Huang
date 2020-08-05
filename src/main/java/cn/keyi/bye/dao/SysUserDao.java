package cn.keyi.bye.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.keyi.bye.model.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, Long>  {
	
	// 根据用户账户获取该用户信息
	public SysUser findByUserName(String userName);

}

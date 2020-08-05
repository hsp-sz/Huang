package cn.keyi.bye.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.keyi.bye.dao.SysUserDao;
import cn.keyi.bye.model.SysUser;

@Service
public class SysUserService {
	
	@Autowired
	SysUserDao sysUserDao;
	
	/**
	 * 根据用户名（唯一）查找用户信息
	 * @param userName
	 * @return
	 */
	public SysUser findByUserName(String userName) {
		System.out.println("SysUserService.findByUserName()");
		return sysUserDao.findByUserName(userName);
	}
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public String saveUser(SysUser user) {
		System.out.println("SysUserService.saveUser()");
		String rslt = "";
		try {
			sysUserDao.save(user);
		} catch (Exception e) {
			rslt = e.getMessage();
		}
		return rslt;
	}
}

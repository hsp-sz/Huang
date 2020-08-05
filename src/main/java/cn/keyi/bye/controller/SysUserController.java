package cn.keyi.bye.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.keyi.bye.model.SysUser;
import cn.keyi.bye.service.SysUserService;

@RestController
@RequestMapping("/userInfo")
public class SysUserController {
	
	@Autowired
	SysUserService sysUserService;	
	
	/**
	 * comment: 从Shiro安全管理器里获取登录用户信息
	 * author : 兴有林栖
	 * date   : 2020-8-2
	 * @return
	 */
	@RequestMapping("/getLoggedUser")
	public Object getLoggedUser() {
		Subject subject = SecurityUtils.getSubject();
		return (SysUser)subject.getPrincipal();
	}
	
	/**
	 * comment: 修改登录用户密码
	 * author : 兴有林栖
	 * date   : 2020-8-2
	 * @param request
	 * @return
	 */
	@RequestMapping("/modifyPassword")
	public Object modifyPassword(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String oldPassw = request.getParameter("oldpassword");
		String newPassw = request.getParameter("newpassword");
		SysUser loggedUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
		int hashIterations = 2;
		SimpleHash oldPasswordHash = new SimpleHash("md5", oldPassw, 
				ByteSource.Util.bytes(loggedUser.getCredentialsSalt()), hashIterations);
		//System.out.println(oldPasswordHash.toHex());
		if(!oldPasswordHash.toHex().equals(loggedUser.getPassword())) {	
			map.put("status", 0);
			map.put("message", "原始密码不正确！");
		} else {
			SimpleHash newPasswordHash = new SimpleHash("md5", newPassw, 
					ByteSource.Util.bytes(loggedUser.getCredentialsSalt()), hashIterations);
			loggedUser.setPassword(newPasswordHash.toHex());
			String rslt = sysUserService.saveUser(loggedUser);
			if(rslt.isEmpty()) {
				map.put("status", 1);
				map.put("message", "密码修改成功！");
			} else {
				map.put("status", 0);
				map.put("message", rslt);
			}
		}		
		return map;		
	}
	
	@RequestMapping("/removeUser")
    @RequiresPermissions("user:del")	// 删除用户权限
    public Object removeUser(String userName) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", 1);
        map.put("message", "删除成功");
        return map;
    }
}

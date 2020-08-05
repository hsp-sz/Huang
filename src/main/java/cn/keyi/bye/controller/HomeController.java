package cn.keyi.bye.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.keyi.bye.service.SysUserService;

/**
 * comment :用于处理登录及主页跳转相关请求
 * author  :兴有林栖
 * date    :2020-8-1
 */
@Controller
public class HomeController {

	@Autowired
	SysUserService sysUserService;	
	
	@RequestMapping("/login")
	public String doLogin(HttpServletRequest request, Model model) {
		// 若登录失败，则从 request 中获取 shiro 处理的异常信息，shiroLoginFailure 为  shiro 异常类的全类名   
        String error = "";
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println(exception);
        if(exception != null) {
        	if(exception.contains("UnknownAccountException")) {
        		error = "账号不存在";
        	} else if(exception.contains("IncorrectCredentialsException")) {
        		error = "密码不正确";
        	} else {
        		error = exception;
        	}	        
		}
		model.addAttribute("error", error); 
        // 此方法不处理登录成功，由 shiro 自己进行处理
		return "login";
    }
	
	@RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }
	
	@RequestMapping("/user")
	@RequiresPermissions("user:view")
    public String user() {
        return "user";
    }
	
	@RequestMapping("/role")
	@RequiresPermissions("role:view")
    public String role() {
        return "role";
    }
	
	@RequestMapping("/permission")
	@RequiresPermissions("permission:view")
    public String permission() {
        return "permission";
    }
	
	@RequestMapping("/artifact")
    public String artifact() {
        return "artifact";
    }
	
	@RequestMapping("/detail")
    public String detail() {
        return "detail";
    }
	
	@RequestMapping("/importdetail")
    public String importdetail() {
        return "importdetail";
    }
}

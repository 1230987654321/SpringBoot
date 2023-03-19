package com.example.admin.controller;

import com.example.admin.config.enums.ResponseCodeEnum;
import com.example.admin.config.exception.ServiceException;
import com.example.admin.config.response.GlobalResponse;
import com.example.admin.config.util.JWTUtil;
import com.example.admin.entity.Admin;
import com.example.admin.service.AdminService;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class LoginController {
    private final AdminService adminService;

    public LoginController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/toLogin")
    public String toLogin(String username, String password) {
        Admin admin = adminService.getUsername(username);
        // 密码 md5 加密
        password = DigestUtils.md5DigestAsHex( password.getBytes() );
        if (admin == null) { // 用户不存在
            throw new ServiceException(ResponseCodeEnum.NOT_EXIST);
        } else if (!admin.getPassword().equals(password)) { // 登陆失败,用户名或密码错误
            throw new ServiceException(ResponseCodeEnum.LOGIN_FAILED);
        } else if (!admin.getStatus().equals(1)) { // 用户被禁用
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_DISABLED);
        } else {
            return JWTUtil.createJWT(username);
        }
    }

    @GetMapping("/login")
    public GlobalResponse<String> login() {
        // 直接返回未登录信息
        throw new ServiceException(ResponseCodeEnum.NOT_LOGIN);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

//    @GetMapping("/logout")
//    public String logout(){
//        SecurityUtils.getSubject().logout();
//        return "logout";
//    }
//
//    @RequiresRoles("admin")
//    @RequiresPermissions("RoleIndex")
//    @GetMapping("/userLoginRoles")
//    public String userLoginRoles() {
//        System.out.println("登录认证角色与权限成功");
//        return "登录认证角色成功";
//    }
}

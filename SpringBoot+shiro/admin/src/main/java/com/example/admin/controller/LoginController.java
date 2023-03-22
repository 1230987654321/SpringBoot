package com.example.admin.controller;

import com.example.admin.config.enums.ResponseCodeEnum;
import com.example.admin.config.response.GlobalResponse;
import com.example.admin.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @program workspace
 * @description 登录控制器
 * @author 贲玉柱
 * @create 2023/3/22 13:13
 **/
@RestController
@RequestMapping("/common")
public class LoginController {

    private final RoleService roleService;


    public LoginController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/toLogin")
    public String toLogin(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(username,password);
        subject.login(token);
        return "登陆成功";
    }

    @GetMapping("/login")
    public GlobalResponse<String> login() {
        // 直接返回未登录信息
        return GlobalResponse.fail(ResponseCodeEnum.NOT_LOGIN);
    }

    @GetMapping("/hello")
    public String hello() {
        // 直接返回未登录信息
        return "hello";
    }

    @GetMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "logout";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("ModelEditSon")
    @GetMapping("/userLoginRoles")
    public String userLoginRoles() {
        System.out.println("登录认证角色与权限成功");
        return "登录认证角色成功";
    }
}

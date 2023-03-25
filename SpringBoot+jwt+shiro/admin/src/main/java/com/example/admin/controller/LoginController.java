package com.example.admin.controller;

import com.example.admin.common.ResponseCodeEnum;
import com.example.admin.common.ServiceException;
import com.example.admin.util.JWTUtil;
import com.example.admin.util.RedisUtil;
import com.example.admin.common.GlobalResponse;
import com.example.admin.entity.Admin;
import com.example.admin.service.AdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program workspace
 * @description 登录控制器
 * @author 贲玉柱
 * @create 2023/3/21 16:10
 **/
@RestController
@RequestMapping("/admin")
public class LoginController {
    private final AdminService adminService;
    private final RedisUtil redisUtil;

    public LoginController(AdminService adminService, RedisUtil redisUtil) {
        this.adminService = adminService;
        this.redisUtil = redisUtil;
    }

    @GetMapping("/toLogin")
    public String toLogin(String username, String password) {
        Admin admin = adminService.getUsername(username);
        if (admin == null) { // 用户不存在
            throw new ServiceException(ResponseCodeEnum.NOT_EXIST);
        } else if (!admin.getStatus().equals(1)) { // 用户被禁用
            throw new ServiceException(ResponseCodeEnum.ACCOUNT_DISABLED);
        }
        // 密码 md5 加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!admin.getPassword().equals(password)) { // 登陆失败,用户名或密码错误
            throw new ServiceException(ResponseCodeEnum.LOGIN_FAILED);
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        // 获取 TOKEN
        String token =  request.getHeader("token");
        // 将 TOKEN 存入 Redis 中进行废弃
        redisUtil.addToken(token);
        return "退出成功";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("RoleIndex")
    @GetMapping("/userLoginRoles")
    public String userLoginRoles() {
        return "登录认证角色成功";
    }
}

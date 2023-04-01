package com.example.admin.controller;

import com.example.admin.service.AdminService;
import com.example.admin.util.JWTUtil;
import com.example.admin.util.RedisUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 轮登录控制器
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@RestController
@RequestMapping("/admin")
public class LoginController {
    private final AdminService adminService;
    private final RedisUtil redisUtil;

    public LoginController(AdminService adminService, RedisUtil redisUtil) {
        this.adminService = adminService;
        this.redisUtil = redisUtil;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    @PostMapping("/toLogin")
    public String toLogin(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        // 根据账号密码查询用户
        adminService.getUsernameAndPassword(username, password);
        return JWTUtil.createJWT(username);
    }

    /**
     * 注销
     *
     * @param request 请求
     * @return 退出成功
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 获取 TOKEN
        String token = request.getHeader("token");
        // 将 TOKEN 存入 Redis 中进行废弃
        redisUtil.addToken(token);
        return "退出成功";
    }

    /**
     * 验证角色与权限
     *
     * @return 登录认证角色成功
     */
    @RequiresRoles("admin")
    @RequiresPermissions("RoleIndex")
    @GetMapping("/userLoginRoles")
    public String userLoginRoles() {
        return "登录认证角色成功";
    }
}

package com.example.admin.controller;

import com.example.admin.service.AdminService;
import com.example.admin.util.JWTUtil;
import com.example.admin.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 登录控制器
 * @create 2023/3/21 16:10
 **/
// value 方法的名称或描述信息 tags 为该 Controller 或方法所属的模块或业务类型
@Api(value = "登录模块", tags = "登录模块")
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
    @ApiOperation(value = "登录", notes = "使用用户名和密码进行登录")
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
    @ApiOperation(value = "注销", notes = "销毁TOKEN凭证")
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
    @ApiOperation(value = "验证角色与权限", notes = "判断登录的角色以及权限是否可以访问")
    @RequiresRoles("admin")
    @RequiresPermissions("RoleIndex")
    @GetMapping("/userLoginRoles")
    public String userLoginRoles() {
        return "登录认证角色成功";
    }
}

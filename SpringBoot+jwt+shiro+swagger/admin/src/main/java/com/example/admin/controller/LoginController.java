package com.example.admin.controller;

import com.example.admin.config.enums.ResponseCodeEnum;
import com.example.admin.config.exception.ServiceException;
import com.example.admin.config.jwt.JWTUtil;
import com.example.admin.config.redis.RedisUtil;
import com.example.admin.entity.Admin;
import com.example.admin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program workspace
 * @description 登录控制器
 * @author 贲玉柱
 * @create 2023/3/21 16:10
 **/
// value 方法的名称或描述信息 tags 为该 Controller 或方法所属的模块或业务类型
@Api(value = "登录模块",tags = "登录模块")
@RestController
@RequestMapping("/admin")
public class LoginController {
    private final AdminService adminService;
    private final RedisUtil redisUtil;

    public LoginController(AdminService adminService, RedisUtil redisUtil) {
        this.adminService = adminService;
        this.redisUtil = redisUtil;
    }

    @ApiOperation(value = "登录",notes = "使用用户名和密码进行登录")
    @PostMapping("/toLogin")
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

    @ApiOperation(value = "测试登录是否成功",notes = "测试登录是否成功")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation(value = "注销",notes = "销毁TOKEN凭证")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        // 获取 TOKEN
        String token =  request.getHeader("token");
        // 将 TOKEN 存入 Redis 中进行废弃
        redisUtil.addToken(token);
        return "退出成功";
    }

    @ApiOperation(value = "验证角色与权限",notes = "判断登录的角色以及权限是否可以访问")
    @RequiresRoles("admin")
    @RequiresPermissions("RoleIndex")
    @GetMapping("/userLoginRoles")
    public String userLoginRoles() {
        return "登录认证角色成功";
    }
}

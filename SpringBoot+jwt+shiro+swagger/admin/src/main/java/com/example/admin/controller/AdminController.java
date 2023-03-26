package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.service.AdminService;
import com.example.admin.util.JWTUtil;
import com.example.admin.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program workspace
 * @description 登录控制器
 * @author 贲玉柱
 * @create 2023/3/26 20:10
 **/

@Api(value = "管理员模块",tags = "管理员模块")
@RestController
@RequestMapping("/admin/admin")
public class AdminController {
    private final AdminService adminService;
    private final RedisUtil redisUtil;

    public AdminController(AdminService adminService, RedisUtil redisUtil) {
        this.adminService = adminService;
        this.redisUtil = redisUtil;
    }

    @ApiOperation(value = "获取登录用户信息",notes = "获取登录用户信息")
    @GetMapping("/getInfo")
    public Admin toLogin(HttpServletRequest request) {
        String token =  request.getHeader("token");
        String username = JWTUtil.getUsername(token);
        return adminService.getUsername(username);
    }

}

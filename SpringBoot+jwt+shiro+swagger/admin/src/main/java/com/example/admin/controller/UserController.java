package com.example.admin.controller;

import com.example.admin.entity.User;
import com.example.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Api(tags = "用户", value = "用户")
@RestController
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询用户列表
     *
     * @param current 当前页
     * @param size    每页条数
     * @param user    用户
     * @return IPage<User> 用户列表
     */
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    @GetMapping("/list")
    public Object getUserList(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, User user) {
        return userService.getUserList(current, size, user);
    }

    /**
     * 查询用户详情
     *
     * @param id 用户id
     * @return User 用户详情
     */
    @ApiOperation(value = "查询用户详情", notes = "查询用户详情")
    @GetMapping("/detail")
    public Object getUserDetail(@RequestParam(name = "id") Integer id) {
        return userService.getUserDetail(id);
    }
}

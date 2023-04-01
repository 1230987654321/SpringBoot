package com.example.admin.controller;

import com.example.admin.entity.User;
import com.example.admin.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
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
    @GetMapping("/detail")
    public Object getUserDetail(@RequestParam(name = "id") Integer id) {
        return userService.getUserDetail(id);
    }
}

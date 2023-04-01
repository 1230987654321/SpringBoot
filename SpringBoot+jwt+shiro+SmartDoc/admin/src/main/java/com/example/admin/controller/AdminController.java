package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.entity.Admin;
import com.example.admin.entity.vo.AdminVo;
import com.example.admin.service.AdminService;
import com.example.admin.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@RestController
@RequestMapping("/admin/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @return Integer 1：成功 0：失败
     */
    @PostMapping("/addAdmin")
    public Integer addAdmin(Admin admin) {
        return adminService.addAdmin(admin);
    }

    /**
     * 查询管理员列表
     *
     * @param current 当前页
     * @param size    每页显示条数
     * @param adminVo 管理员信息
     * @return IPage<AdminVo> 管理员列表
     */
    @GetMapping("/getAdminList")
    public IPage<AdminVo> getAdminList(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, AdminVo adminVo) {
        return adminService.getAdminList(current, size, adminVo);
    }

    /**
     * 根据id查询管理员
     *
     * @param id 管理员id
     * @return AdminVo 管理员信息
     */
    @GetMapping("/getAdminById")
    public AdminVo getAdminById(@RequestParam(name = "id") Integer id) {
        return adminService.getAdminById(id);
    }

    /**
     * 通过token获取登录用户信息
     *
     * @param request 请求
     * @return AdminVo 管理员信息
     */
    @GetMapping("/getInfo")
    public AdminVo getInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JWTUtil.getUsername(token);
        return adminService.getAdminByUsername(username);
    }

    /**
     * 修改管理员状态
     *
     * @param id     管理员id
     * @param status 管理员状态 0：禁用 1：启用
     * @return Integer 1：成功 0：失败
     */
    @PutMapping("/updateStatus")
    public Integer updateStatus(@RequestParam(name = "id") Integer id, @RequestParam(name = "status") Integer status) {
        return adminService.updateStatus(id, status);
    }

    /**
     * 修改管理员密码
     *
     * @param id       管理员id
     * @param password 管理员密码
     * @return Integer 1：成功 0：失败
     */
    @PutMapping("/updatePassword")
    public Integer updatePassword(@RequestParam(name = "id") Integer id, @RequestParam(name = "password") String password) {
        return adminService.updatePassword(id, password);
    }

    /**
     * 修改管理员信息
     *
     * @param admin 管理员信息
     * @return Integer 1：成功 0：失败
     */
    @PutMapping("/updateAdmin")
    public Integer updateAdmin(Admin admin) {
        return adminService.updateAdmin(admin);
    }

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @return Integer 1：成功 0：失败
     */
    @DeleteMapping("/deleteAdmin")
    public Integer deleteAdmin(@RequestParam(name = "id") Integer id) {
        return adminService.deleteAdmin(id);
    }
}

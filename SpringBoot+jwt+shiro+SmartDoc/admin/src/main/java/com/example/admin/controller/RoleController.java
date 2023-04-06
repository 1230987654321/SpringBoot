package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.entity.Role;
import com.example.admin.entity.vo.RoleVo;
import com.example.admin.service.RoleService;
import org.springframework.web.bind.annotation.*;

/**
 * 角色
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@RestController
@RequestMapping("/admin/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 添加角色
     *
     * @param role 角色
     * @return Integer 1成功 0失败
     */
    @PostMapping("/addRole")
    public Integer addRole(Role role) {
        return roleService.addRole(role);
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return Integer 1成功 0失败
     */
    @PutMapping("/updateRole")
    public Integer updateRole(Role role) {
        return roleService.updateRole(role);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return Integer 1成功 0失败
     */
    @DeleteMapping("/deleteRole")
    public Integer deleteRole(@RequestParam(name = "id") Integer id) {
        return roleService.deleteRole(id);
    }

    /**
     * 获取角色列表
     *
     * @param role 角色
     * @return IPage<Role> 角色列表
     */
    @GetMapping("/getRoleList")
    public IPage<Role> getRoleList(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, Role role) {
        return roleService.getRoleList(current, size, role);
    }

    /**
     * 获取角色详情
     *
     * @param id 角色id
     * @return RoleVo 角色详情
     */
    @GetMapping("/getRoleDetail")
    public RoleVo getRoleDetail(@RequestParam(name = "id") Integer id) {
        return roleService.getRoleDetail(id);
    }
}

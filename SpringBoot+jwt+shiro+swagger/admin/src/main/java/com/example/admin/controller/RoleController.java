package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.entity.Role;
import com.example.admin.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Api(tags = "角色管理", value = "角色管理")
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
    @ApiOperation(value = "添加角色", notes = "添加角色")
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
    @ApiOperation(value = "修改角色", notes = "修改角色")
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
    @ApiOperation(value = "删除角色", notes = "删除角色")
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
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping("/getRoleList")
    public IPage<Role> getRoleList(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, Role role) {
        return roleService.getRoleList(current, size, role);
    }

    /**
     * 获取角色详情
     *
     * @param id 角色id
     * @return Role 角色详情
     */
    @ApiOperation(value = "获取角色详情", notes = "获取角色详情")
    @GetMapping("/getRoleDetail")
    public Role getRoleDetail(@RequestParam(name = "id") Integer id) {
        return roleService.getRoleDetail(id);
    }
}

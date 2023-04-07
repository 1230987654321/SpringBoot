package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.entity.Role;
import com.example.admin.entity.vo.RoleVo;
import com.example.admin.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @PutMapping("/updateRoleById")
    public Integer updateRoleById(Role role) {
        return roleService.updateRoleById(role);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return Integer 1成功 0失败
     */
    @DeleteMapping("/deleteRoleById")
    public Integer deleteRoleById(@RequestParam(name = "id") Integer id) {
        return roleService.deleteRoleById(id);
    }

    /**
     * 获取角色列表
     *
     * @param role 角色
     * @return IPage<Role> 角色列表
     */
    @GetMapping("/getRolePageList")
    public IPage<Role> getRolePageList(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, Role role) {
        return roleService.getRolePageList(current, size, role);
    }

    /**
     * 获取角色详情
     *
     * @param id 角色id
     * @return RoleVo 角色详情
     */
    @GetMapping("/getRoleById")
    public RoleVo getRoleById(@RequestParam(name = "id") Integer id) {
        return roleService.getRoleById(id);
    }

    /**
     * 获取所有角色
     *
     * @return List<Map < String, Object>> 角色列表
     */
    @GetMapping("/getAllRole")
    public List<Map<String, Object>> getAllRole() {
        return roleService.getAllRole();
    }
}

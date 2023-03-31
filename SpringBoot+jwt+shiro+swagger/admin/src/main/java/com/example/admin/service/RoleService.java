package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Role;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
public interface RoleService extends IService<Role> {
    /**
     * 添加角色
     *
     * @param role 角色
     * @return Integer 1成功 0失败
     */
    Integer addRole(Role role);

    /**
     * 修改角色
     *
     * @param role 角色
     * @return Integer 1成功 0失败
     */
    Integer updateRole(Role role);

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return Integer 1成功 0失败
     */
    Integer deleteRole(Integer id);

    /**
     * 获取角色列表
     *
     * @param role 角色
     * @return List<Role> 角色列表
     */
    IPage<Role> getRoleList(Integer current, Integer size, Role role);

    /**
     * 获取角色详情
     *
     * @param id 角色id
     * @return Role 角色详情
     */
    Role getRoleDetail(Integer id);

}

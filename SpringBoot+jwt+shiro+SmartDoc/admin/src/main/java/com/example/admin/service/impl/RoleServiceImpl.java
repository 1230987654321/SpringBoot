package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Admin;
import com.example.admin.entity.Role;
import com.example.admin.entity.vo.RoleVo;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.mapper.RoleMapper;
import com.example.admin.service.RoleService;
import com.example.admin.util.CheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleMapper roleMapper;
    private final AdminMapper adminMapper;

    public RoleServiceImpl(RoleMapper roleMapper, AdminMapper adminMapper) {
        this.roleMapper = roleMapper;
        this.adminMapper = adminMapper;
    }

    /**
     * 添加角色
     *
     * @param role 角色
     * @return Integer 1成功 0失败
     * @throws ServiceException 服务异常
     */
    @Override
    public Integer addRole(Role role) {
        CheckUtil.checkStringNotEmpty(role.getTitle(), "角色名称不能为空");
        CheckUtil.checkStringNotEmpty(role.getMenuId(), "角色权限不能为空");
        CheckUtil.checkStringNotEmpty(role.getDescription(), "角色描述不能为空");
        try {
            return roleMapper.insert(role);
        } catch (Exception e) {
            throw new ServiceException(500, "新增角色失败");
        }
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return Integer 1成功 0失败
     * @throws ServiceException 服务异常
     */
    @Override
    public Integer updateRoleById(Role role) {
        CheckUtil.checkIntegerNotZero(role.getId(), "角色id不能为空");
        CheckUtil.checkStringNotEmpty(role.getTitle(), "角色名称不能为空");
        CheckUtil.checkStringNotEmpty(role.getMenuId(), "角色权限不能为空");
//        CheckUtil.checkStringNotEmpty(role.getDescription(), "角色描述不能为空");
        try {
            return roleMapper.updateById(role);
        } catch (Exception e) {
            throw new ServiceException(500, "修改角色失败");
        }
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return Integer 1成功 0失败
     * @throws ServiceException 服务异常
     */
    @Override
    public Integer deleteRoleById(Integer id) {
        CheckUtil.checkIntegerNotZero(id, "角色id不能为空");
        // 判断角色是否被使用
        Long count = adminMapper.selectCount(Wrappers.lambdaQuery(Admin.class).eq(Admin::getRoleId, id));
        if (count > 0) {
            throw new ServiceException(500, "该角色已被使用，无法删除");
        }
        try {
            return roleMapper.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException(500, "删除角色失败");
        }
    }

    /**
     * 获取角色列表
     *
     * @param role 角色
     * @return List<Role> 角色列表
     */
    @Override
    public IPage<Role> getRolePageList(Integer current, Integer size, Role role) {
        // 参数校验
        CheckUtil.checkPage(current, size);
        Page<Role> page = new Page<>(current, size);
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery(Role.class).orderByDesc(Role::getCreatedAt);
        if (StringUtils.isNotEmpty(role.getTitle())) {
            wrapper.like(Role::getTitle, role.getTitle());
        }
        // 查询轮播图列表
        return roleMapper.selectPage(page, wrapper);
    }

    /**
     * 获取角色详情
     *
     * @param id 角色id
     * @return Role 角色详情
     * @throws ServiceException 业务异常
     */
    @Override
    public RoleVo getRoleById(Integer id) {
        CheckUtil.checkIntegerNotZero(id, "角色id不能为空");
        Role role;
        try {
            role = roleMapper.selectById(id);
        } catch (Exception e) {
            throw new ServiceException(500, "获取角色详情失败");
        }
        CheckUtil.checkObjectNotNull(role, 404, "角色不存在");
        RoleVo roleVo = new RoleVo(role);
        // 将权限 id 从 String 转换成 String[]
        String[] str = role.getMenuId().split(",");
        // 将 String[] 转换成 List<Integer>
        List<Integer> idsList = Arrays.stream(str).map(Integer::parseInt).toList();
        roleVo.setMenuIdList(idsList);
        return roleVo;
    }

    /**
     * 获取角色列表
     *
     * @return List<Role> 角色列表
     */
    @Override
    public List<Map<String, Object>> getAllRole() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("id AS value", "title AS label");
        return roleMapper.selectMaps(wrapper);
    }
}

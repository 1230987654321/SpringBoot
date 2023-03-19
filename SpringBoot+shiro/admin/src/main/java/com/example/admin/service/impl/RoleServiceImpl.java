package com.example.admin.service.impl;

import com.example.admin.entity.Role;
import com.example.admin.mapper.RoleMapper;
import com.example.admin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}

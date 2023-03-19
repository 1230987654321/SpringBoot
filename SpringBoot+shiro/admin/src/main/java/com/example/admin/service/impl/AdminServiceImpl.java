package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.Admin;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin getUsername(String username) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.lambdaQuery(Admin.class).eq(Admin::getUsername,username);
        Admin admin = adminMapper.selectOne(wrapper);
        return admin;
    }
}

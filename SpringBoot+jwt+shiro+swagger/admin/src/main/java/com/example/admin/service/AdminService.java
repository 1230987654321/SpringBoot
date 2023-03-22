package com.example.admin.service;

import com.example.admin.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
public interface AdminService extends IService<Admin> {

    /* 查询 单条信息 */
    Admin getUsername(String username);
}

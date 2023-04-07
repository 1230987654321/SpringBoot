package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.User;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
public interface UserService extends IService<User> {

    /**
     * 查询用户列表
     *
     * @param current 当前页
     * @param size    每页条数
     * @param user    用户
     * @return IPage<User> 用户列表
     */
    IPage<User> getUserPageList(Integer current, Integer size, User user);

    /**
     * 查询用户详情
     *
     * @param id 用户id
     * @return User 用户详情
     */
    User getUserById(Integer id);
}

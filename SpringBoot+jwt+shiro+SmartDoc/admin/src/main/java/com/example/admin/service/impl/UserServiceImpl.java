package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.User;
import com.example.admin.mapper.UserMapper;
import com.example.admin.service.UserService;
import com.example.admin.util.CheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 查询用户列表
     *
     * @param current 当前页
     * @param size    每页条数
     * @param user    用户
     * @return IPage<User> 用户列表
     */
    @Override
    public IPage<User> getUserPageList(Integer current, Integer size, User user) {
        // 参数校验
        CheckUtil.checkPage(current, size);
        Page<User> page = new Page<>(current, size);
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).orderByDesc(User::getCreatedAt);
        if (StringUtils.isNotEmpty(user.getTel())) {
            wrapper.like(User::getTel, user.getTel());
        }
        if (StringUtils.isNotEmpty(user.getNickName())) {
            wrapper.like(User::getNickName, user.getNickName());
        }
        // 查询轮播图列表
        return userMapper.selectPage(page, wrapper);
    }

    /**
     * 查询用户详情
     *
     * @param id 用户id
     * @return User 用户详情
     */
    @Override
    public User getUserById(Integer id) {
        CheckUtil.checkIntegerNotNull(id, "用户id不能为空");
        User user;
        try {
            user = userMapper.selectById(id);
        } catch (Exception e) {
            throw new ServiceException(500, "获取用户详情失败");
        }
        CheckUtil.checkObjectNotNull(user, 404, "用户不存在");
        return user;
    }
}

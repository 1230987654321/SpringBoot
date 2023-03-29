package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Admin;
import com.example.admin.entity.Role;
import com.example.admin.entity.vo.AdminVo;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.mapper.RoleMapper;
import com.example.admin.service.AdminService;
import com.example.admin.util.CheckUtil;
import com.example.admin.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    private final AdminMapper adminMapper;
    private final RoleMapper roleMapper;
    private final RedisUtil redisUtil;
    /**
     * redis key 前缀
     */
    @Value("${spring.redis.admin-prefix}")
    private String REDIS_KEY_ADMIN_PREFIX;

    public AdminServiceImpl(AdminMapper adminMapper, RoleMapper roleMapper, RedisUtil redisUtil) {
        this.adminMapper = adminMapper;
        this.roleMapper = roleMapper;
        this.redisUtil = redisUtil;
    }

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @throws ServiceException 业务异常
     */
    @Override
    public Integer addAdmin(Admin admin) {
        // 判断用户名是否存在
        CheckUtil.checkStringNotEmpty(admin.getUsername(), "用户名不能为空");
        // 判断密码是否存在
        CheckUtil.checkStringNotEmpty(admin.getPassword(), "密码不能为空");
        // 判断角色是否存在
        CheckUtil.checkIntegerNotNull(admin.getRoleId(), "角色不能为空");
        // 判断管理员是否存在
        findAdmin(null, admin.getUsername(), "username");
        // 添加管理员
        try {
            return adminMapper.insert(admin);
        } catch (Exception e) {
            log.error("添加管理员失败 =======>", e);
            throw new ServiceException(500, "添加失败");
        }
    }

    /**
     * 查询管理员列表
     *
     * @param current 当前页
     * @param size    每页显示条数
     * @param adminVo 管理员信息
     * @return IPage<AdminVo> 管理员列表
     */
    @Override
    public IPage<AdminVo> getAdminList(Integer current, Integer size, AdminVo adminVo) {
        // 创建分页对象
        Page<Admin> page = new Page<>(current, size);
        // 创建查询条件
        LambdaQueryWrapper<Admin> wrapper = Wrappers.lambdaQuery();
        // 判断管理员名称是否存在
        // 判断用户名是否为空
        if (StringUtils.isNotEmpty(adminVo.getUsername())) {
            wrapper.like(Admin::getUsername, adminVo.getUsername());
        }
        // 判断管理员角色是否存在
        if (adminVo.getRoleId() != null) {
            wrapper.eq(Admin::getRoleId, adminVo.getRoleId());
        }
        // 设置排序
        wrapper.orderByDesc(Admin::getCreatedAt);
        // 查询管理员列表
        IPage<Admin> adminPage = adminMapper.selectPage(page, wrapper);
        IPage<AdminVo> adminVoIPage = adminPage.convert(AdminVo::new);
        // 补充管理员信息
        if (adminVoIPage.getRecords().size() > 0) {
            addRoleName(adminVoIPage);
        }
        return adminVoIPage;
    }

    /**
     * 根据id查询管理员
     *
     * @param id 管理员id
     * @return AdminVo 管理员信息
     */
    @Override
    public AdminVo getAdminById(Integer id) {
        // 判断管理员id是否存在
        CheckUtil.checkIntegerNotNull(id, "管理员id不能为空");
        // 在数据库中查找管理员
        return findAdmin(id, null, "id");
    }

    /**
     * 根据用户名和密码查询管理员
     *
     * @param username 用户名
     * @param password 密码
     * @throws ServiceException 业务异常
     */
    @Override
    public void getUsernameAndPassword(String username, String password) {
        // 判断用户名是否存在
        CheckUtil.checkStringNotEmpty(username, "用户名不能为空");
        // 判断密码是否存在
        CheckUtil.checkStringNotEmpty(password, "密码不能为空");
        // 在数据库中查找管理员
        Admin admin = findAdmin(null, username, "username");
        // 判断密码是否正确
        if (!password.equals(admin.getPassword())) {
            log.error("管理员不存在 =======>账号：" + username + " 密码：" + password);
            throw new ServiceException(400, "账号或密码错误");
        }
        // 判断管理员状态是否正常
        if (admin.getStatus() != 1) {
            log.error("管理员状态异常 =======>" + username + " 状态：" + admin.getStatus());
            throw new ServiceException(400, "管理员状态异常");
        }
        // 将管理员信息存入redis
        redisUtil.addData(REDIS_KEY_ADMIN_PREFIX + username, admin);
    }

    /**
     * 根据用户名查询管理员
     *
     * @param username 用户名
     * @return AdminVo 管理员信息
     * @throws ServiceException 业务异常
     */
    @Override
    public AdminVo getAdminByUsername(String username) {
        // 判断用户名是否存在
        CheckUtil.checkStringNotEmpty(username, "用户名不能为空");
        // 在数据库中查找管理员
        return findAdmin(null, username, "username");
    }

    /**
     * 修改管理员状态
     *
     * @param id     管理员id
     * @param status 管理员状态 0：禁用 1：启用
     * @return Integer 影响行数
     * @throws ServiceException 业务异常
     */
    @Override
    public Integer updateStatus(Integer id, Integer status) {
        // 判断管理员id是否存在
        CheckUtil.checkIntegerNotNull(id, "管理员id不能为空");
        // 判断管理员状态是否存在
        CheckUtil.checkIntegerNotNull(status, "管理员状态不能为空");
        // 判断管理员状态是否正确
        if (status != 0 && status != 1) {
            log.error("管理员状态错误 =======>" + status);
            throw new ServiceException(400, "管理员状态错误");
        }
        // 在数据库中查找管理员
        Admin admin = findAdmin(id, null, "id");
        // 修改管理员状态
        admin.setStatus(status);
        // 判断管理员状态是否修改成功
        int res = adminMapper.updateById(admin);
        if (res != 1) {
            log.error("管理员状态修改失败 =======>" + id);
            throw new ServiceException(500, "管理员状态修改失败");
        }
        // 将管理员信息存入redis
        redisUtil.addData(REDIS_KEY_ADMIN_PREFIX + admin.getUsername(), admin);
        return res;
    }

    /**
     * 修改管理员密码
     *
     * @param id       管理员id
     * @param password 管理员密码
     * @return Integer 影响行数
     * @throws ServiceException 业务异常
     */
    @Override
    public Integer updatePassword(Integer id, String password) {
        // 判断管理员id是否存在
        CheckUtil.checkIntegerNotNull(id, "管理员id不能为空");
        // 判断管理员密码是否存在
        CheckUtil.checkStringNotEmpty(password, "密码不能为空");
        // 在数据库中查找管理员
        Admin admin = findAdmin(id, null, "id");
        // 修改管理员密码
        admin.setPassword(password);
        // 判断管理员密码是否修改成功
        int res = adminMapper.updateById(admin);
        if (res != 1) {
            log.error("管理员密码修改失败 =======>" + id);
            throw new ServiceException(500, "管理员密码修改失败");
        }
        return res;
    }

    /**
     * 修改管理员信息
     *
     * @param admin 管理员信息
     * @return Integer 影响行数
     * @throws ServiceException 业务异常
     */
    @Override
    public Integer updateAdmin(Admin admin) {
        // 判断管理员id是否存在
        CheckUtil.checkIntegerNotNull(admin.getId(), "管理员id不能为空");
        // 判断管理员用户名是否存在
        CheckUtil.checkStringNotEmpty(admin.getUsername(), "管理员用户名不能为空");
        // 判断管理员密码是否存在
        CheckUtil.checkStringNotEmpty(admin.getPassword(), "管理员密码不能为空");
        // 在数据库中查找管理员
        Admin adminInfo = findAdmin(admin.getId(), null, "id");
        // 修改管理员信息
        adminInfo.setUsername(admin.getUsername());
        adminInfo.setPassword(admin.getPassword());
        adminInfo.setStatus(admin.getStatus());
        // 判断管理员信息是否修改成功
        int res = adminMapper.updateById(adminInfo);
        if (res != 1) {
            log.error("管理员信息修改失败 =======>" + admin.getId());
            throw new ServiceException(500, "管理员信息修改失败");
        }
        // 将管理员信息存入redis
        redisUtil.addData(REDIS_KEY_ADMIN_PREFIX + admin.getUsername(), adminInfo);
        return res;
    }

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @return Integer 影响行数
     * @throws ServiceException 业务异常
     */
    @Override
    public Integer deleteAdmin(Integer id) {
        // 判断管理员id是否存在
        CheckUtil.checkIntegerNotNull(id, "管理员id不能为空");
        // 在数据库中查找管理员
        Admin admin = findAdmin(id, null, "id");
        // 删除管理员
        int res = adminMapper.deleteById(id);
        if (res != 1) {
            log.error("管理员删除失败 =======>" + id);
            throw new ServiceException(500, "管理员删除失败");
        }
        // 将管理员信息从redis中删除
        redisUtil.removeAdmin(REDIS_KEY_ADMIN_PREFIX + admin.getUsername());
        return res;
    }

    /**
     * 根据管理员id或用户名查找管理员
     *
     * @param id       管理员id
     * @param username 管理员用户名
     * @param type     查找方式（id或username）
     * @return AdminVo 管理员信息
     * @throws ServiceException 业务异常
     */
    private AdminVo findAdmin(Integer id, String username, String type) {
        LambdaQueryWrapper<Admin> wrapper;
        if ("id".equals(type)) {
            wrapper = Wrappers.lambdaQuery(Admin.class).eq(Admin::getId, id);
        } else if ("username".equals(type)) {
            wrapper = Wrappers.lambdaQuery(Admin.class).eq(Admin::getUsername, username);
        } else {
            throw new ServiceException(500, "不支持的查找方式");
        }
        Admin admin = adminMapper.selectOne(wrapper);
        CheckUtil.checkObjectNotNull(admin, 404, "管理员不存在");
        // 转化为Vo
        AdminVo adminVo = Optional.ofNullable(admin).map(AdminVo::new).orElse(null);
        // 从其它表查询信息再封装到Vo
        Optional.ofNullable(adminVo).ifPresent(this::addRoleName);
        return adminVo;
    }

    /**
     * 补充管理员信息
     *
     * @param adminVoIPage 管理员信息
     */
    private void addRoleName(IPage<AdminVo> adminVoIPage) {
        // 提取角色roleId，方便批量查询
        Set<Integer> roleIds = adminVoIPage.getRecords().stream().map(Admin::getRoleId).collect(toSet());
        // RoleName
        List<Role> role = roleMapper.selectList(Wrappers.lambdaQuery(Role.class).in(Role::getId, roleIds));
        // 构造映射关系，方便匹配roleId与RoleName
        Map<Integer, String> hashMap = role.stream().collect(toMap(Role::getId, Role::getTitle));
        // 将查询补充的信息添加到Vo中
        adminVoIPage.convert(e -> e.setRoleName(hashMap.get(e.getRoleId())));
    }

    /**
     * 补充管理员信息
     *
     * @param adminVo 管理员信息
     */
    private void addRoleName(AdminVo adminVo) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery(Role.class).eq(Role::getId, adminVo.getRoleId());
        Role role = roleMapper.selectOne(wrapper);
        Optional.ofNullable(role).ifPresent(e -> adminVo.setRoleName(e.getTitle()));
    }
}

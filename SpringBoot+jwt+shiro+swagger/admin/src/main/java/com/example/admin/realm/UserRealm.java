package com.example.admin.realm;

import com.alibaba.fastjson.JSON;
import com.example.admin.common.JWTToken;
import com.example.admin.common.ResponseCodeEnum;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Admin;
import com.example.admin.entity.Role;
import com.example.admin.service.AdminService;
import com.example.admin.service.MenuService;
import com.example.admin.service.RoleService;
import com.example.admin.util.JWTUtil;
import com.example.admin.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author 贲玉柱
 * @program workspace
 * @description Shiro Realm 实现类，用于处理应用程序中的身份认证和授权请求
 * @create 2023/3/21 16:15
 **/
@Component
public class UserRealm extends AuthorizingRealm {

    private final AdminService adminService;

    private final RoleService roleService;

    private final MenuService menuService;

    private final RedisUtil redisUtil;

    public UserRealm(AdminService adminService, RoleService roleService, MenuService menuService, RedisUtil redisUtil) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.menuService = menuService;
        this.redisUtil = redisUtil;
    }

    // Redis 中保存 用户信息 的键名前缀
    @Value("${spring.redis.admin-prefix}")
    private String REDIS_KEY_ADMIN_PREFIX;

    // Redis 中保存 用户角色 的键名前缀
    @Value("${spring.redis.role-prefix}")
    private String REDIS_KEY_ROLE_PREFIX;

    // Redis 中保存 权限 的键名前缀
    @Value("${spring.redis.permissions-prefix}")
    private String REDIS_KEY_PERMISSIONS_PREFIX;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     * 返回该领域是否支持给定的身份验证令牌
     *
     * @param token 身份验证令牌
     * @return 如果支持令牌，则是真的，否则为错误
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    /**
     * 自定义授权
     * 获取当前登录用户角色、权限信息
     * 返回给 Shiro 用来进行授权验证
     *
     * @param principals PrincipalCollection
     * @return SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("走了=>doGetAuthorizationInfo");
        // 获取当前登录用户信息
        String username = JWTUtil.getUsername(principals.toString());
        // 查询 Redis 当前登录用户
        Admin admin = redisUtil.getData(REDIS_KEY_ADMIN_PREFIX + username, Admin.class);
        if (admin == null) {
            // 如果 Redis 中不存在该用户信息，则从数据库中获取并存储到 Redis 中
            admin = adminService.getAdminByUsername(username);
            if (admin == null) {
                throw new ServiceException(ResponseCodeEnum.NOT_EXIST);
            }
            // 将查询用户信息储存 Redis 中
            redisUtil.addData(REDIS_KEY_ADMIN_PREFIX + username, admin);
        }
        // 创建对象,封装当前登录用户的角色、权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 查询 Redis 当前登录角色
        Role role = redisUtil.getData(REDIS_KEY_ROLE_PREFIX + admin.getRoleId(), Role.class);
        if (role == null) {
            // 查询角色
            role = roleService.getRoleDetail(admin.getRoleId());
            if (role == null) {
                throw new ServiceException(401, "当前登录者并无角色");
            } else if (role.getControlId() == null || role.getControlId().equals("")) {
                throw new ServiceException(401, "当前登录者并无权限");
            } else if (role.getStatus() == 0) {
                throw new ServiceException(401, "当前登录着角色已被禁用");
            }
            // 将查询用户信息储存 Redis 中
            redisUtil.addData(REDIS_KEY_ROLE_PREFIX + admin.getRoleId(), role);
        }
        // 存储角色
        info.addRole(role.getTitle());
        // 将权限 id 从 String 转为 List<Integer>
        String[] str = role.getControlId().split(",");
        // 将 String[] 转换成 List<Integer>
        List<Integer> idsList = Arrays.stream(str).map(Integer::parseInt).toList();
        // 查询 Redis 权限信息
        String menuString = redisUtil.getData(REDIS_KEY_PERMISSIONS_PREFIX + JSON.toJSONString(idsList), String.class);
        List<String> menuList;
        if (menuString == null) {
            // 查询权限
            menuList = menuService.getColumnName(idsList);
            if (menuList == null) {
                throw new ServiceException(401, "当前登录者并无权限");
            }
            // 将查询权限储存 Redis 中,储存时将List<String>转换成String
            redisUtil.addData(REDIS_KEY_PERMISSIONS_PREFIX + JSON.toJSONString(idsList), String.join(",", menuList));
        } else {
            // 将从 Redis 中获取的权限 ,将其转换成数组
            menuList = List.of(menuString.split(","));
        }
        // 储存权限
        info.setStringPermissions(new HashSet<>(menuList));
        // 返回信息
        return info;
    }

    /**
     * 自定义认证
     * 获取当前登录用户信息
     * 返回给 Shiro 用来进行身份验证
     *
     * @param authenticationToken 用户身份信息
     * @return SimpleAuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("走了=>doGetAuthenticationInfo");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username
        String username = JWTUtil.getUsername(token);
        return new SimpleAuthenticationInfo(token, token, username);
    }
}

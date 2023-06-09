package com.example.admin.reaml;

import com.alibaba.fastjson.JSON;
import com.example.admin.common.ResponseCodeEnum;
import com.example.admin.common.ServiceException;
import com.example.admin.common.JWTToken;
import com.example.admin.util.JWTUtil;
import com.example.admin.util.RedisUtil;
import com.example.admin.entity.Admin;
import com.example.admin.entity.Role;
import com.example.admin.service.AdminService;
import com.example.admin.service.ControllersService;
import com.example.admin.service.RoleService;
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
 * @program workspace
 * @description Shiro Realm 实现类，用于处理应用程序中的身份认证和授权请求
 * @author 贲玉柱
 * @create 2023/3/21 16:15
 **/
@Component
public class UserRealm extends AuthorizingRealm {

    private final AdminService adminService;

    private final RoleService roleService;

    private final ControllersService controllersService;

    private final RedisUtil redisUtil;

    public UserRealm(AdminService adminService,RoleService roleService,ControllersService controllersService,RedisUtil redisUtil) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.controllersService = controllersService;
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
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 自定义授权
     * 获取当前登录用户角色、权限信息
     * 返回给 Shiro 用来进行授权验证
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("走了=>doGetAuthorizationInfo");
        // 获取当前登录用户信息
        String username = JWTUtil.getUsername(principals.toString());
        // 查询 Redis 当前登录用户
        Admin admin = redisUtil.getData(REDIS_KEY_ADMIN_PREFIX+username,Admin.class);
        if (admin == null) {
            // 如果 Redis 中不存在该用户信息，则从数据库中获取并存储到 Redis 中
            admin = adminService.getUsername(username);
            if (admin == null) {
                throw new ServiceException(ResponseCodeEnum.NOT_EXIST);
            }
            // 将查询用户信息储存 Redis 中
            redisUtil.addData(REDIS_KEY_ADMIN_PREFIX+username,admin);
        }
        // 创建对象,封装当前登录用户的角色、权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 查询 Redis 当前登录角色
        Role role = redisUtil.getData(REDIS_KEY_ROLE_PREFIX+admin.getRoleId(),Role.class);
        if (role == null) {
            // 查询角色
            role = roleService.getById(admin.getRoleId());
            if (role == null) {
                throw new ServiceException(401,"当前登录者并无角色");
            }
            // 将查询用户信息储存 Redis 中
            redisUtil.addData(REDIS_KEY_ROLE_PREFIX+admin.getRoleId(),role);
        }
        // 存储角色
        info.addRole(role.getTitle());
        // 将权限 id 从 String 转为 List<Integer>
        String[] str = role.getControlId().split(",") ;
        // 将 String[] 转换成 List<Integer>
        List<Integer> idsList = Arrays.stream(str).map(Integer::parseInt).toList();
        // 查询 Redis 权限信息
        String controllersString =  redisUtil.getData( REDIS_KEY_PERMISSIONS_PREFIX+JSON.toJSONString(idsList),String.class) ;
        List<String> controllersList;
        if (controllersString == null) {
            // 查询权限
            controllersList = controllersService.getColumnName( idsList );
            if (controllersList == null) {
                throw new ServiceException(401,"当前登录者并无权限");
            }
            // 将查询权限储存 Redis 中,储存时将List<String>转换成String
            redisUtil.addData(REDIS_KEY_PERMISSIONS_PREFIX+JSON.toJSONString(idsList), String.join(",", controllersList) );
        }else {
            // 将从 Redis 中获取的权限 ,将其转换成数组
            controllersList = List.of(controllersString.split(","));
        }
        // 储存权限
        info.setStringPermissions(new HashSet<>(controllersList) );
        // 返回信息
        return info;
    }

    /**
     * 自定义登录
     * 根据用户在页面输入的用户名,查询数据库中的用户名和密码,交给shiro框架
     * 让shiro框架进行对比用户名,密码是否正确
     * 调用时机:在controller调用subject.login(token);方法就会执行这个方法,进行用户名 密码校验
     * @param authenticationToken the authentication token containing the user's principal and credentials.
     * @return SimpleAuthenticationInfo
     * AuthenticationException 异常时Shiro内部进行抛出的，全局异常捕获器在 Filter 之后执行，不能正常进行补捕获，只能在 Filter内部进行处理。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("走了=>doGetAuthenticationInfo");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username
        String username = JWTUtil.getUsername(token);
        return new SimpleAuthenticationInfo( token, token,username );
    }
}

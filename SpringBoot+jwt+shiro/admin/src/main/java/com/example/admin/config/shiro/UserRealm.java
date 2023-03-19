package com.example.admin.config.shiro;

import com.example.admin.config.util.JWTToken;
import com.example.admin.config.util.JWTUtil;
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
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class UserRealm extends AuthorizingRealm {

    private final AdminService adminService;

    private final RoleService roleService;

    private final ControllersService controllersService;

    public UserRealm(AdminService adminService,RoleService roleService,ControllersService controllersService) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.controllersService = controllersService;
    }

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
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("走了=>doGetAuthorizationInfo");
        // 获取当前登录用户信息
        String username = JWTUtil.getUsername(principals.toString());
        Admin admin = adminService.getUsername(username);
        // 查询角色
        Role role = roleService.getById( admin.getRoleId() );
        // 将权限 id 从 String 转为 List<Integer>
        String[] str = role.getControlId().split(",") ;
        List<Integer> idsList = Arrays.stream(str).map(Integer::parseInt).toList();
        // 查询权限信息
        List<String> controllersList = controllersService.getColumnName( idsList );
        // 创建对象,封装当前登录用户的角色、权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 存储角色
        info.addRole(role.getTitle());
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

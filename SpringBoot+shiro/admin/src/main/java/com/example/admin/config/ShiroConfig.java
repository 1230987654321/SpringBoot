package com.example.admin.config;

import com.example.admin.reaml.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 贲玉柱
 * @program workspace
 * @description Shiro 配置类
 * @create 2023/3/22 13:15
 **/
@Configuration
public class ShiroConfig {

    private final UserRealm userRealm;

    public ShiroConfig(UserRealm userRealm) {
        this.userRealm = userRealm;
    }

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        // 创建 defaultWebSecurityManager 对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        userRealm.setCredentialsMatcher(matcher);
        // 将 userRealm 存入 defaultWebSecurityManager 对象中
        securityManager.setRealm(userRealm);
        // 返回
        return securityManager;
    }

    /**
     * 请求过滤器
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition definition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // 设置不需要认证可以访问的资源
        chainDefinition.addPathDefinition("/common/toLogin", "anon");
        // 设置登出过滤器(只设置就行,可以不用写接口,登出操作会直接跳转到 loginUrl 设置的接口上 )
        chainDefinition.addPathDefinition("/common/logout", "logout");
        // 设置需要进行登录认证的拦截范围
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

}

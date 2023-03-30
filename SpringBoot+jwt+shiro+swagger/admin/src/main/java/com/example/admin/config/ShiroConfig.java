package com.example.admin.config;

import com.example.admin.realm.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 贲玉柱
 * @program workspace
 * @description Shiro 安全框架的配置类
 * @create 2023/3/21 16:14
 **/
@Slf4j
@Configuration
public class ShiroConfig {

    private final UserRealm userRealm;

    public ShiroConfig(UserRealm userRealm) {
        this.userRealm = userRealm;
    }

    /**
     * 安全管理器,核心bean，所有的realm都要在这里加进去
     *
     * @return securityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSubjectDAO(subjectDAO());
        return securityManager;
    }

    /**
     * 返回一个带有sessionStorageEvaluator集的DefaultSubjectDAO
     *
     * @return DefaultSubjectDAO
     */
    public DefaultSubjectDAO subjectDAO() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return subjectDAO;
    }

    /**
     * 返回一个带有sessionStorageEnabled=false的DefaultSessionStorageEvaluator
     * 用于禁用session
     *
     * @return DefaultSessionStorageEvaluator
     * @throws Exception Exception
     */
    private DefaultSessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        try {
            defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        } catch (Exception e) {
            log.error("禁用Session时出现错误", e);
        }
        return defaultSessionStorageEvaluator;
    }

    /**
     * ShiroFilterFactoryBean，
     * 它负责配置过滤器链和安全管理器。
     * 也可以使用FilterChainResolver以自定义过滤器链。
     *
     * @param securityManager 安全管理器
     * @param environment     环境变量
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager, Environment environment) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setFilters(filterMap(environment));
        bean.setSecurityManager(securityManager);
        bean.setFilterChainDefinitionMap(map());
        return bean;
    }

    /**
     * 配置过滤器
     *
     * @param environment 环境变量
     * @return 过滤器集合
     */
    private Map<String, Filter> filterMap(Environment environment) {
        Map<String, Filter> filterMap = new HashMap<>();
        // JWT过滤器用于验证JWT令牌
        filterMap.put("jwt", new JWTFilter(environment));
        return filterMap;
    }

    /**
     * 配置过滤器链
     * anon: 无需认证就可以访问
     * authc: 必须认证才可以访问
     * user: 必须拥有 记住我 功能才能使用
     * perms: 拥有对某个资源的权限才能访问
     * role: 拥有某个角色权限才能访问
     * jwt: 需要JWT令牌才能访问
     */
    private Map<String, String> map() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/admin/toLogin", "anon");
        map.put("/v2/**", "anon");
        map.put("/admin/**", "jwt");
        return map;
    }
}

package com.example.admin.config.shiro;

import com.example.admin.config.jwt.JWTFilter;
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
 * @program workspace
 * @description Shiro 安全框架的配置类
 * @author 贲玉柱
 * @create 2023/3/21 16:14
 **/
@Configuration
public class ShiroConfig {

    private final UserRealm userRealm;

    public ShiroConfig(UserRealm userRealm) {
        this.userRealm = userRealm;
    }

    /**
     * 安全管理器,核心bean，所有的realm都要在这里加进去
     * @return securityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        // 创建 defaultWebSecurityManager 对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 将 userRealm 存入 defaultWebSecurityManager 对象中
        securityManager.setRealm(userRealm);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 返回
        return securityManager;
    }

    /**
     * 设置过滤器
     */
    @Bean(name="shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager, Environment environment){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JWTFilter(environment) );
        bean.setFilters(filterMap);
        bean.setSecurityManager(securityManager);
        // 设置拦截器与url映射关系map
        Map<String, String> map = new LinkedHashMap<>();
        // 设置不需要认证可以访问的资源
        map.put("/admin/logout", "logout");
        map.put("/admin/toLogin", "anon");
        // 设置需要进行登录认证的拦截范围(所有的路径都走自定义的过滤器)
        map.put("/admin/**", "jwt");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }
}

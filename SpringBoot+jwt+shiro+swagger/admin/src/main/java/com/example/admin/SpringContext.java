package com.example.admin;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 用于访问 Spring 容器上下文信息的工具类，可以在应用程序中获取和管理 Spring 容器中的 Bean 对象。
 * @create 2023/3/21 16:16
 **/
@Configuration
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 通过实现 ApplicationContextAware 接口，可以获取到 Spring 容器的上下文环境，从而可以获取容器中定义的各种 Bean 对象。
     *
     * @param applicationContext ApplicationContext
     * @throws BeansException BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 获取上下文
     *
     * @param
     * @return org.springframework.context.ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name String
     * @return java.lang.Object
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz class
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name, 以及Clazz返回指定的Bean
     *
     * @param name  String
     * @param clazz class
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
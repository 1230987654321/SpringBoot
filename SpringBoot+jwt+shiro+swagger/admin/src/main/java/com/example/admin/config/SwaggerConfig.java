package com.example.admin.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 贲玉柱
 * @program workspace
 * @description Swagger 配置
 * @create 2023/3/22 14:57
 **/
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Profile({"dev", "test"})
public class SwaggerConfig implements WebMvcConfigurer {
    /**
     * 创建 Swagger 示例
     *
     * @return Swagger 示例
     */
    @Bean
    public Docket docket() {
        // 创建 Swagger 示例
        return new Docket(DocumentationType.SWAGGER_2)
                // 选择 API 扫描方式:扫描指定包下的所有 Controller 类
                .select().apis(RequestHandlerSelectors.basePackage("com.example.admin.controller"))
                // 扫描所有路径
                .paths(PathSelectors.any())
                .build()
                // 配置 Swagger 文档信息
                .apiInfo(new ApiInfoBuilder()
                        // 设置作者信息
                        .contact(new Contact("筱贲", "", ""))
                        // 设置简介信息
                        .description("接口文档，包含了所有API接口的详细说明和示例代码。")
                        // 设置文档标题
                        .title("实战SpringBoot+Vue实现前后端分离")
                        // 设置文档版本号
                        .version("v1.0").build());
    }

    /**
     * 配置静态资源
     *
     * @param registry 静态资源注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}

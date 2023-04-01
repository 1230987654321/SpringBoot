package com.example.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author 贲玉柱
 * @program workspace
 * @description Swagger 配置
 * @create 2023/3/22 14:57
 **/
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    /**
     * 创建 Swagger 示例
     *
     * @return Swagger 示例
     */
    @Bean
    public Docket createRestApi() {
        // 创建 Swagger 示例
        Docket docket = new Docket(DocumentationType.OAS_30);
        docket.apiInfo(apiInfo())
                .select()
                // 选择 API 扫描方式:扫描指定包下的所有 Controller 类
                .apis(RequestHandlerSelectors.basePackage("com.example.admin.controller"))
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置作者信息
                .contact(new Contact("筱贲", "", "1765417572@qq.com"))
                // 设置简介信息
                .description("接口文档，包含了所有API接口的详细说明和示例代码。")
                // 设置文档标题
                .title("SpringBoot接口文档")
                // 设置文档版本号
                .version("v1.0")
                // 协议
                .license("Apache 2.0")
                // 协议地址
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
}

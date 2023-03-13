package com.zhu.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author GH
 * @descriptons swagger接口文档
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhu.blog.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("xiaozhu", "https://github.com/Cvxiaozhu", "cvxiaozhu@outlook.com");
        return new ApiInfoBuilder()
                .title("个人博客")
                .description("三更的个人博客学习")
                // 联系方式
                .contact(contact)
                // 版本
                .version("1.1.1")
                .build();
    }
}
package com.zhu.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author GH
 * @Description: 启动类
 * @date 2023/3/8 19:22
 */
@SpringBootApplication
@MapperScan("com.zhu.blog.mapper")
@EnableSwagger2
public class BolgAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BolgAdminApplication.class, args);
    }
}

package com.zhu.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author GH
 * @Description: 开启定时任务
 *
 * @EnableScheduling 开启定时任务
 * @EnableSwagger2 开启swagger API 文档
 * @date 2023/2/16 15:48
 */

@SpringBootApplication
@MapperScan("com.zhu.blog.mapper")
@EnableScheduling
@EnableSwagger2
public class ZblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZblogApplication.class, args);
    }
}

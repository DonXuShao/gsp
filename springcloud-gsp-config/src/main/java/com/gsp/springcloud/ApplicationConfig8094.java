package com.gsp.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author Don
 * @Date: 2020/7/8 19:53
 * @Discription:配置中心启动类
 * @Version 1.0
 **/
@EnableConfigServer
@SpringBootApplication
public class ApplicationConfig8094 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig8094.class, args);
    }
}

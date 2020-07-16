package com.gsp.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author Don
 * @Date: 2020/7/8 19:40
 * @Discription:Eureka7003启动类
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class ApplicationEureka7003 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEureka7003.class, args);
    }
}

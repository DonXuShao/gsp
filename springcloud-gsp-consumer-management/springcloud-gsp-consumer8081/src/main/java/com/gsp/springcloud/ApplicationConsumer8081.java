package com.gsp.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Don
 * @Date: 2020/7/15 8:24
 * @Discription:Consumer启动类
 * @Version 1.0
 **/
@SpringBootApplication
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
public class ApplicationConsumer8081 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsumer8081.class, args);
    }
}

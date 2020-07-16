package com.gsp.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author Don
 * @Date: 2020/7/13 14:46
 * @Discription:Provider8091启动类
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.gsp.springcloud.mapper")
public class ApplicationProvider8091 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationProvider8091.class, args);
    }
}

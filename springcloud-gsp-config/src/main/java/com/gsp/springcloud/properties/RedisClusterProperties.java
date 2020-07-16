package com.gsp.springcloud.properties;

import lombok.Data;

/**
 * @Author Don
 * @Date: 2020/7/10 15:19
 * @Discription:Redis配置实体类
 * @ConfigurationProperties:默认只会从application.properties中去读取属性
 * @Version 1.0
 **/
//@Component
//@PropertySource("classpath:properties/redis_cluster.properties")
//@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisClusterProperties {
    private String nodes;
    private Integer maxAttempts;
    private Integer commonTimeout;
}

package com.gsp.springcloud.config;

import com.gsp.springcloud.properties.RedisClusterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author Don
 * @Date: 2020/7/10 15:27
 * @Discription:获取RedisCluster链接配置
 * @Version 1.0
 **/
//@Configuration
public class RedisClusterConfig {

    @Autowired
    private RedisClusterProperties redisClusterProperties;

    /**
     * @Author Don
     * @Description  获取链接与节点 用于连接Redis服务器
     * @Date 2020/7/10 16:04
     **/
//    @Bean
    public JedisCluster getJedisCluster() {
        String nodes = redisClusterProperties.getNodes();
        String[] split = nodes.split(",");
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        for (String hostPort : split) {
            String[] split1 = hostPort.split(":");//0:ip 1:port
            HostAndPort hostAndPort = new HostAndPort(split1[0], Integer.parseInt(split1[1]));
            hostAndPortSet.add(hostAndPort);
        }
        return new JedisCluster(hostAndPortSet, redisClusterProperties.getCommonTimeout(), redisClusterProperties.getMaxAttempts());
    }
}

package com.gsp.springcloud.properties;

import lombok.Data;

/**
 * @Author Don
 * @Date: 2020/7/10 15:25
 * @Discription:FTP服务器配置实体类
 * @Version 1.0
 **/
//@Component
//@PropertySource("classpath:properties/ftp.properties")
//@ConfigurationProperties(prefix = "spring.ftp")
@Data
public class FtpProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String basePath;
    private String httpPath;
}

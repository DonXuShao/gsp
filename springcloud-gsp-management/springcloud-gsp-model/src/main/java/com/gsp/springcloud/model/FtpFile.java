package com.gsp.springcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Don
 * @Date: 2020/7/17 21:36
 * @Discription:
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FtpFile {
    private String filePath;
    private String dir;
    private String fileName;
}

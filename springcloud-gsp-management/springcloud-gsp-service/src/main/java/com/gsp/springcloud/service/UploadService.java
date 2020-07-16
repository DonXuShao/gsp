package com.gsp.springcloud.service;

import com.gsp.springcloud.properties.FtpProperties;
import com.gsp.springcloud.utils.FileNameUtils;
import com.gsp.springcloud.utils.FtpUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static com.gsp.springcloud.staticproperties.RedisProperties.POINT;
import static com.gsp.springcloud.staticproperties.TimeFormatProperties.DATE_FORMAT;

/**
 * @Author Don
 * @Date: 2020/7/10 16:47
 * @Discription:文件上传
 * @Version 1.0
 **/
//@Service
public class UploadService {

//    @Autowired
    private FtpProperties ftpProperties;

    /**
     * @Author Don
     * @Description  文件上传
     * @Date 2020/7/10 16:54 
     **/
    public Boolean upload(MultipartFile file) {
        //1.获取文件的远程名称（为了获取后缀名）
        String oldFileName = file.getOriginalFilename();
        //2.生成新的文件名
        String newFileName = FileNameUtils.getFileName();
        //3.截取后缀名 拼接到新的文件名上
        newFileName = newFileName + oldFileName.substring(oldFileName.lastIndexOf(POINT));
        //4.获取文件的上传路径
        String filePath = DateUtil.formatDate(new Date(), DATE_FORMAT);
        //5.调用文件上传工具类
        try {
            return FtpUtils.upload(ftpProperties.getHost(), ftpProperties.getPort(), ftpProperties.getUsername(), ftpProperties.getPassword(), ftpProperties.getBasePath(), filePath, newFileName, file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

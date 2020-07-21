package com.gsp.springcloud.service;

import com.gsp.springcloud.model.FtpFile;
import com.gsp.springcloud.properties.FtpProperties;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.FileNameUtils;
import com.gsp.springcloud.utils.FtpUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

import static com.gsp.springcloud.staticproperties.RedisProperties.POINT;
import static com.gsp.springcloud.staticproperties.TimeFormatProperties.DATE_FORMAT;

/**
 * @Author Don
 * @Date: 2020/7/10 16:47
 * @Discription:文件上传
 * @Version 1.0
 **/
@Service
public class UploadService {

    @Autowired
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

    /**
     * @Author Don
     * @Description :  文件上传方法
     * @Date 2020/7/17 21:36
     * @Parameter : [file, newFileName]
     * @Return  FtpFile
     **/
    public FtpFile upload(MultipartFile file, String newFileName){
        FtpFile ftpFile = new FtpFile();
        //1.获取文件的远程名称（为了获取后缀名）
        String oldFileName = file.getOriginalFilename();
        //2.判断前端的文件名是否传过来
        if(newFileName == null){
            newFileName = FileNameUtils.getFileName();
        }
        //3.截取后缀名，拼接到新的文件名上
        newFileName = newFileName + oldFileName.substring(oldFileName.lastIndexOf(POINT));
        //4.获取文件的上传路径（2020/07/10）
        String filePath = DateUtils.formatDate(new Date(),DATE_FORMAT);
        //5.调用文件上传工具类
        try {
            //6.上传文件
            Boolean upload = FtpUtils.upload(ftpProperties.getHost(), ftpProperties.getPort(), ftpProperties.getUsername(), ftpProperties.getPassword(), ftpProperties.getBasePath(),
                    filePath, newFileName, file.getInputStream());
            //7.判断上传是否成功
            if(upload){
                //成功
                return ftpFile.setFilePath(filePath).setDir(oldFileName.substring(oldFileName.lastIndexOf(POINT))).setFileName(newFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
      * @author XRF
      * @date  2020/7/20 10:57
     * 文件上传，
     * file 上传的文件
     * path 文件路径
     * newFileName 新文件的名字
      * @description
      */
    public Boolean upload(MultipartFile file,String filePath,String newFileName){
        try {
            return FtpUtils.upload(ftpProperties.getHost(), ftpProperties.getPort(), ftpProperties.getUsername(), ftpProperties.getPassword(), filePath, ftpProperties.getBasePath(), newFileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

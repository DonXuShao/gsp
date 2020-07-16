package com.gsp.springcloud.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Don
 * @Date: 2020/7/10 15:34
 * @Discription:文件上传工具类
 * @Version 1.0
 **/
public class FtpUtils {
    private FtpUtils() {
    }

    /**
     * @Author Don
     * @Description FTP文件上传方法
     * 最终按照每天日期的文件夹来上传文件
     * 2020-7-10---->20文件夹
     * cd /home/redis/src
     * @Date 2020/7/10 15:37
     **/
    public static Boolean upload(String host, Integer port, String username, String password, String basePath, String filePath, String fileName, InputStream inputStream) {
        //1.创建临时路径
        String tempPath = "";
        //2.创建FTPClient对象 该对象为FTP给JAVA提供的API
        FTPClient ftpClient = new FTPClient();
        try {
            //3.定义返回状态码
            int replyCode;
            //4.链接ftp
            ftpClient.connect(host, port);
            //5.登录ftp服务器
            ftpClient.login(username, password);
            //6.接受返回的状态码 成功为230 失败为503
            replyCode = ftpClient.getReplyCode();
            //判断
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                //链接失败 断开连接
                ftpClient.disconnect();
                return false;
            }

            //8.预先检测即将上传的文件夹是否存在 basePath:/home/ftp/www  filePath:/2020/07/10
            //---->/home/ftp/www/2020/07/10
            if (!ftpClient.changeWorkingDirectory(basePath + filePath)) {
                //进入判断中则证明该文件夹不存在
                //9.创建文件夹
                String[] dirs = filePath.split("/"); //[2020,07,10]["","2020","07","10"]
                //10.将basePath赋值给临时路径 tempPath:/home/ftp/www
                tempPath = basePath;
                //11.循环
                for (String dir : dirs) {
                    if (null == dir || StringUtils.isEmpty(dir)) {
                        continue;//咩有数据
                    }
                    //12.拼接临时路径 如没有进入上一个if 则取到的值应该为2020
                    tempPath += "/" + dir;
                    // tempPath:/home/ftp/www/2020
                    //13.再次检测tempPath是否存在
                    if (!ftpClient.changeWorkingDirectory(tempPath)) {
                        //文件夹不存在
                        //14.创建文件夹
                        if (!ftpClient.makeDirectory(tempPath)) {
                            //说明文件夹创建失败
                            return false;
                        } else {
                            //15.判断出创建出来的目录确实存在
                            ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //16.将文件转换为二进制的形式来进行上传
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //17.开启真正的文件上传
            if (!ftpClient.storeFile(fileName, inputStream)) {
                //说明上传失败
                return false;
            }
            //18.关闭输入流
            inputStream.close();
            //19.退出FTP
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    //说明还在连接中
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}

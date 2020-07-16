package com.gsp.springcloud.controller;

import com.gsp.springcloud.annotation.LoginAnnotation;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.CommonController;
import com.gsp.springcloud.model.Log;
import com.gsp.springcloud.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Don
 * @Date: 2020/7/16 8:51
 * @Discription:
 * @Version 1.0
 **/
@RestController
public class LogController extends CommonController<Log> {

    @Autowired
    private LogService logService;

    @Override
    public BaseService<Log> getBaseService() {
        return logService;
    }

    /**
     * @Author Don
     * @Description 保存登录日志
     * @Date 2020/7/16 8:54
     **/
    @PostMapping("/addLoginLog")
    @LoginAnnotation(opeationType = "登录操作", opeationName = "管理员登录")
    public Integer addLoginlog(@RequestBody Log log) {
        return getBaseService().add(log);
    }
}

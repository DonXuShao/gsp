package com.gsp.springcloud.controller;

import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.CommonController;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.model.User;
import com.gsp.springcloud.service.LoginService;
import com.gsp.springcloud.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.gsp.springcloud.status.LoginStatus.*;

/**
 * @Author Don
 * @Date: 2020/7/16 8:39
 * @Discription: 登录Controller
 * @Version 1.0
 **/
@RestController
public class LoginController extends CommonController<User> {
    @Autowired
    private LoginService loginService;

    @Override
    public BaseService<User> getBaseService() {
        return loginService;
    }


    /**
     * @Author Don
     * @Description 执行登录操作
     * @Date 2020/7/16 8:47
     **/
    @PostMapping("/dologin")
    public ResultData doLogin(@RequestBody User user) {
        TokenVo tokenVo = loginService.doLogin(user);
        if (tokenVo.getIfSuccess()) {
            return super.loginSuccess(tokenVo.getToken());
        } else if (tokenVo.getType() == 1) {
            return super.loginFailed(USER_NOT_EXIST.getMsg());
        } else if (tokenVo.getType() == 2) {
            return super.loginFailed(PASSWORD_WRONG.getMsg());
        } else {
            return super.loginFailed(SYSTEM_EXCEPTION.getMsg());
        }
    }

}

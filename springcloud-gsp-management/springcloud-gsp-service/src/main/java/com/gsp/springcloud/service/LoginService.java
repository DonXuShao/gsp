package com.gsp.springcloud.service;

import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.model.User;
import com.gsp.springcloud.vo.TokenVo;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author Don
 * @Date: 2020/7/15 19:58
 * @Discription: 登录Service
 * @Version 1.0
 **/
@Service
public class LoginService extends BaseService<User> {

    /**
     * @Author Don
     * @Description 执行登录操作
     * @Date 2020/7/15 20:10
     * pojo：实体类
     * povo:封装类型(当在实际开发中，会有很多种情况导致多表联查的时候无法装载数据--->我就根据返回前端的数据自己
     * 去封装一个对象出来---->view object)
     **/
    public TokenVo doLogin(User user) {
        TokenVo tokenVo = new TokenVo();
        User user1 = new User();
        //判断是否user是否为空 判断前端传递参数是否正确
        if (null != user1) {
            user1.setUsername(user.getUsername());
            User user2 = super.selectOne(user1);
            //判断user2是否为空 判断用户是否存在
            if (null == user2) {
                tokenVo.setIfSuccess(false).setType(1);
                return tokenVo;
            } else {
                user1.setPassword(user.getPassword());
                User user3 = super.selectOne(user1);
                //判断user3是否为空 判断密码是否正确
                if (null == user3) {
                    tokenVo.setIfSuccess(false).setType(2);
                    return tokenVo;
                } else {
                    //登录成功 将随机生成的token存入数据库
                    //!!!!!!mybatis是无法检测连接符的，他会把连接符进行转译(\\-)
                    // 需要把连接符替换掉
                    String token = UUID.randomUUID().toString().replaceAll("-", "");
                    user3.setToken(token);
                    Integer updateResult = super.update(user3);
                    if (updateResult > 0) {
                        tokenVo.setIfSuccess(true).setToken(token);
                    } else {
                        tokenVo.setIfSuccess(false).setType(4);
                        return tokenVo;
                    }
                }
            }
        } else {
            tokenVo.setIfSuccess(false).setType(4);
            return tokenVo;
        }
        return tokenVo;
    }
}

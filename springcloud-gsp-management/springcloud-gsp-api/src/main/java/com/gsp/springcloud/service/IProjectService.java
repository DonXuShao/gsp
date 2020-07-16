package com.gsp.springcloud.service;

import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.model.Log;
import com.gsp.springcloud.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author Don
 * @Description  
 * @Date 2020/7/16 9:10 
 **/
@FeignClient(value = "gsp-interface1")
public interface IProjectService {

   /**
    * @Author Don
    * @Description  执行登录操作
    * @Date 2020/7/16 9:06 
    **/
    @PostMapping("/doLogin")
    ResultData doLogin(@RequestBody User user);

  /**
   * @Author Don
   * @Description   新增日志
   * @Date 2020/7/16 9:06 
   **/
    @PostMapping("/addLoginLog")
    Integer addLoginLog(@RequestBody Log loginLog);

}

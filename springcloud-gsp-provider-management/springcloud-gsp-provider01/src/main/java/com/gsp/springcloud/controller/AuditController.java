package com.gsp.springcloud.controller;

import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.CommonController;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.model.Audit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author XRF
 * @date 2020/7/17 15:57
 * @description
 * 审核表
 */
public class AuditController extends CommonController<Audit> {
    @Override
    public BaseService<Audit> getBaseService() {
        return null;
    }

    /**
      * @author XRF
      * @date  2020/7/17 15:59
     * 向审核表插入数据
      * @description
      */
    @PostMapping("/addMpAndResult")
    public ResultData addMpAndResult(@RequestParam Map map) {
        return super.add(map);
    }
}

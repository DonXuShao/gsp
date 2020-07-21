package com.gsp.springcloud.controller;

import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.CommonController;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.model.ResultCommit;
import com.gsp.springcloud.service.ResultCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author XRF
 * @date 2020/7/16 10:57
 * @description
 * 汇交成果控制层
 */
@RestController
public class ResultCommitController extends CommonController<ResultCommit> {
    @Autowired
    private ResultCommitService resultCommitService;

    @Override
    public BaseService<ResultCommit> getBaseService() {
        return resultCommitService;
    }

    /**
      * @author XRF
      * @date  2020/7/16 11:14
     * 根据项目id查询该项目详细信息和该项目下的附件信息和汇交成果信息
      * @description
      */
    @GetMapping("/selectAllMpAndResById")
    public ResultData selectAllMpAndResById(Long id){
        HashMap<String, Object> stringObjectHashMap = resultCommitService.selectAllMpAndResById(id);
        if (stringObjectHashMap.size() > 0) {
            return operationSuccess(stringObjectHashMap);
        }else {
            return operationFailed("查询失败！");
        }
    }

    /**
      * @author XRF
      * @date  2020/7/16 15:55
     * 查询所有项目信息--分页
      * @description
      */
    @GetMapping("/resSelectAlls")
    public ResultData resSelectAlls(HashMap hashMap){
        PageInfo pageInfo = resultCommitService.resSelectAlls(hashMap);
        if (null != pageInfo && !"".equals(pageInfo)){
            return operationSuccess(pageInfo);
        } else {
            return operationFailed("查询失败！");
        }
    }

    /**
      * @author XRF
      * @date  2020/7/16 15:58
     * 根据项目名称模糊查询项目信息
      * @description
      */
    @GetMapping("/resProjectListSelect")
    public ResultData resProjectListSelect(HashMap hashMap){
        PageInfo pageInfo = resultCommitService.resProjectListSelect(hashMap);
        if (null != pageInfo && !"".equals(pageInfo)){
            return operationSuccess(pageInfo);
        } else {
            return operationFailed("查询失败！");
        }
    }


}

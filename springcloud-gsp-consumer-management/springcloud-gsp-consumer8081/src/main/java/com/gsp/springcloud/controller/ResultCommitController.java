package com.gsp.springcloud.controller;

import com.gsp.springcloud.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author XRF
 * @date 2020/7/17 9:24
 * @description
 */
@RestController
public class ResultCommitController {
    @Autowired
    private ResultCommitController resultCommitController;

    /**
     * @author XRF
     * @date  2020/7/16 11:14
     * 根据项目id查询该项目详细信息和该项目下的附件信息和汇交成果信息
     * @description
     */
    @GetMapping("/selectAllMpAndResById")
    public ResultData selectAllMpAndResById(Long id){
        return resultCommitController.selectAllMpAndResById(id);
    }

    /**
     * @author XRF
     * @date  2020/7/16 15:55
     * 查询所有项目信息--分页
     * @description
     */
    @GetMapping("/resSelectAlls")
    public ResultData resSelectAlls(HashMap hashMap){
        return resultCommitController.resSelectAlls(hashMap);
    }

    /**
     * @author XRF
     * @date  2020/7/16 15:58
     * 根据项目名称模糊查询项目信息
     * @description
     */
    @GetMapping("/resProjectListSelect")
    public ResultData resProjectListSelect(HashMap hashMap){
        return resultCommitController.resProjectListSelect(hashMap);
    }
}

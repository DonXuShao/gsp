package com.gsp.springcloud.controller;

import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.service.SpringCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author XRF
 * @date 2020/7/17 9:18
 * @description
 */
@RestController
public class MappingProjectController {
    @Autowired
    private SpringCloudService springCloudService;

    /**
     * @author XRF
     * @date  2020/7/15 19:49
     * 查询所有项目信息--->分页
     * @description
     */
    @GetMapping("/selectAllPros")
    public ResultData selectAllPros(@RequestParam HashMap hashMap){
        return springCloudService.selectAllPros(hashMap);
    }

    /**
     * @author XRF
     * @date  2020/7/15 19:49
     * 点击项目审核查询所有项目信息--->分页
     * @description
     */
    @GetMapping("/auditSelectAllsMp")
    public ResultData auditSelectAllsMp(@RequestParam HashMap hashMap){
        return springCloudService.auditSelectAllsMp(hashMap);
    }

    /**
     * @author XRF
     * @date  2020/7/16 16:48
     * 点击成果汇交查看成果汇交已经处于一个提交状态的项目信息---分页
     * @description
     */
    @GetMapping("/resSelectAllsMp")
    public ResultData resSelectAllsMp(@RequestParam HashMap hashMap){
        return springCloudService.resSelectAllsMp(hashMap);
    }

    /**
     * @author XRF
     * @date  2020/7/15 19:49
     * 根据项目的编号查询该项目下的所有审核信息--》分页
     * @description
     */
    @GetMapping("/selectAuditByMpId")
    public ResultData selectAuditByMpId(@RequestParam HashMap hashMap){
        return springCloudService.selectAuditByMpId(hashMap);
    }

    /**
     * @author XRF
     * @date  2020/7/15 19:55
     * 根据项目名称模糊查询----》分页
     * @description
     */
    @GetMapping("/projectListSelect")
    public ResultData projectListSelect(@RequestParam HashMap hashMap){
        return springCloudService.projectListSelect(hashMap);
    }

    /**
     * @author XRF
     * @date  2020/7/16 11:07
     * 根据项目id查询该项目详细信息
     * @description
     */
    @GetMapping("/selectProjectById")
    public ResultData selectProjectById(@RequestParam Long id){
        return springCloudService.selectProjectById(id);
    }

    /**
     * @author XRF
     * @date  2020/7/16 17:19
     * 根据项目编号查看项目的详细信息，以及该项目下的附件
     * 并且查看该项目的汇交结果信息以及该汇交成果下的附件
     * @description
     */
    @GetMapping("/selectAllMpAndResult")
    public ResultData selectAllMpAndResult(@RequestParam Long id){
        return springCloudService.selectAllMpAndResult(id);
    }
}

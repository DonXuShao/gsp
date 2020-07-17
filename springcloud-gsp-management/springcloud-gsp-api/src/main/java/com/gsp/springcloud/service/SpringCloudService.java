package com.gsp.springcloud.service;


import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.model.CheckPerson;
import com.gsp.springcloud.model.MappingUnit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * @Author Don
 * @Date: 2020/7/13 11:28
 * @Discription:
 * @Version 1.0
 **/
@FeignClient(value = "gsp-interface")
 public interface SpringCloudService {

    /**
     * @Author Don
     * @Description 查询表中所有单位数据
     * @Date 2020/7/14 22:31
     **/
    @GetMapping("/selectAll")
    ResultData selectAll();

    /**
     * @Author Don
     * @Description 按条件查询单位
     * @Date 2020/7/14 22:32
     **/
    @GetMapping("/selectUnit")
    ResultData selectUnit(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 评分记录
     * @Date 2020/7/14 22:32
     **/
    @GetMapping("/selectScoreRecords")
    ResultData selectScoreRecords(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 负责人列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectHeaderOfUnit")
    ResultData selectHeaderOfUnit(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 负责人详细信息
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectHeaderOfUnitDetail")
    ResultData selectHeaderOfUnitDetail(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 技术人员列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectTechnicalPerson")
    ResultData selectTechnicalPerson(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 技术人员列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectTechnicalPersonDetail")
    ResultData selectTechnicalPersonDetail(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 查询审核记录
     * @Date 2020/7/14 21:47
     **/
    @GetMapping("/selectAuditRecords")
    ResultData selectAuditRecords(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 查询仪器设备列表
     * @Date 2020/7/14 21:55
     **/
    @GetMapping("/selectEquipment")
    ResultData selectEquipment(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 查询仪器设备详细信息
     * @Date 2020/7/14 22:00
     **/
    @GetMapping("/selectEquipmentDetail")
    ResultData selectEquipmentDetail(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 特殊岗位人员信息列表
     * @Date 2020/7/14 22:12
     **/
    @GetMapping("/selectSpecialPost")
    ResultData selectSpecialPost(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 查看特殊岗位人员详细信息
     * @Date 2020/7/14 22:15
     **/
    @GetMapping("/selectSpecialPostDetail")
    ResultData selectSpecialPostDetail(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 根据单位查询资源信息
     * @Date 2020/7/14 22:28
     **/
    @GetMapping("/selectResource")
    ResultData selectResource(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 查询黑白名单
     * @Date 2020/7/16 15:07
     **/
    @GetMapping("/selectWhiteOrBlackList")
    ResultData selectWhiteOrBlackList(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 随机按照比例以及区进行查询
     * @Date 2020/7/16 15:30
     **/
    @GetMapping("/selectRandomCheckUnit")
    ResultData selectRandomCheckUnit(@RequestParam Map map);

    /**
     * @Author Don
     * @Description 随机抽查人员
     * @Date 2020/7/16 16:42
     **/
    @GetMapping("/selectRandomPerson")
    ResultData selectRandomPerson(@RequestParam Map map);

    /**
     * @Author Don
     * @Description :  新增或者修改抽查人员信息
     * @Date 2020/7/17 16:37
     * @Parameter : [map]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/addOrUpdateCheckPerson")
     ResultData addOrUpdateCheckPerson(@RequestBody CheckPerson checkPerson);

    /**
     * @Author Don
     * @Description :  删除抽查人员
     * @Date 2020/7/17 17:33
     * @Parameter : [map]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/deleteCheckPerson")
     ResultData deleteCheckPerson(@RequestBody CheckPerson checkPerson);

    /**
     * @Author Don
     * @Description : 单位审核
     * @Date 2020/7/17 15:55
     * @Parameter : [id, audit_status]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/updateMappingUnitAudit")
     ResultData updateMappingUnitAudit(@RequestParam Map map);

    /**
     * @Author Don
     * @Description :  单位注册或者修改
     * @Date 2020/7/17 15:18
     * @Parameter : [map]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/addOrUpdateMappingUnit")
     ResultData addOrUpdateMappingUnit(@RequestBody MappingUnit mappingUnit);

    /**
     * @Author Don
     * @Description :  分值的新增与修改方法
     * @Date 2020/7/17 18:39 
     * @Parameter : [map]
     * @Return  com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/addOrUpdateScoreRecords")
     ResultData addOrUpdateScoreRecords(@RequestParam Map map);
}

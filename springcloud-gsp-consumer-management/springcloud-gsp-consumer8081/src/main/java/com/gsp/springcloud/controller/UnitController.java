package com.gsp.springcloud.controller;

import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.service.SpringCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @Author Don
 * @Date: 2020/7/13 14:37
 * @Discription:Consumer 消费者
 * @Version 1.0
 **/
@RestController
public class UnitController {

    @Autowired
    private SpringCloudService springCloudService;

    /**
     * @Author Don
     * @Description 查询表中所有单位数据
     * @Date 2020/7/14 22:31
     **/
    @GetMapping("/selectAll")
    public ResultData selectAll() {
        return springCloudService.selectAll();
    }

    /**
     * @Author Don
     * @Description 按条件查询单位
     * @Date 2020/7/14 22:32
     **/
    @GetMapping("/selectUnit")
    public ResultData selectUnit(@RequestParam Map map) {
        return springCloudService.selectUnit(map);
    }

    /**
     * @Author Don
     * @Description 评分记录
     * @Date 2020/7/14 22:32
     **/
    @GetMapping("/selectScoreRecords")
    public ResultData selectScoreRecords(@RequestParam Map map) {
        return springCloudService.selectScoreRecords(map);
    }

    /**
     * @Author Don
     * @Description 负责人列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectHeaderOfUnit")
    public ResultData selectHeaderOfUnit(@RequestParam Map map) {
        return springCloudService.selectHeaderOfUnit(map);
    }

    /**
     * @Author Don
     * @Description 负责人详细信息
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectHeaderOfUnitDetail")
    public ResultData selectHeaderOfUnitDetail(@RequestParam Map map) {
        return springCloudService.selectHeaderOfUnitDetail(map);
    }

    /**
     * @Author Don
     * @Description 技术人员列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectTechnicalPerson")
    public ResultData selectTechnicalPerson(@RequestParam Map map) {
        return springCloudService.selectTechnicalPerson(map);
    }

    /**
     * @Author Don
     * @Description 技术人员列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectTechnicalPersonDetail")
    public ResultData selectTechnicalPersonDetail(@RequestParam Map map) {
        return springCloudService.selectTechnicalPersonDetail(map);
    }

    /**
     * @Author Don
     * @Description 查询审核记录
     * @Date 2020/7/14 21:47
     **/
    @GetMapping("/selectAuditRecords")
    public ResultData selectAuditRecords(@RequestParam Map map) {
        return springCloudService.selectAuditRecords(map);
    }

    /**
     * @Author Don
     * @Description 查询仪器设备列表
     * @Date 2020/7/14 21:55
     **/
    @GetMapping("/selectEquipment")
    public ResultData selectEquipment(@RequestParam Map map) {
        return springCloudService.selectEquipment(map);
    }

    /**
     * @Author Don
     * @Description 查询仪器设备详细信息
     * @Date 2020/7/14 22:00
     **/
    @GetMapping("/selectEquipmentDetail")
    public ResultData selectEquipmentDetail(@RequestParam Map map) {
        return springCloudService.selectEquipmentDetail(map);
    }

    /**
     * @Author Don
     * @Description 特殊岗位人员信息列表
     * @Date 2020/7/14 22:12
     **/
    @GetMapping("/selectSpecialPost")
    public ResultData selectSpecialPost(@RequestParam Map map) {
        return springCloudService.selectSpecialPost(map);
    }

    /**
     * @Author Don
     * @Description 查看特殊岗位人员详细信息
     * @Date 2020/7/14 22:15
     **/
    @GetMapping("/selectSpecialPostDetail")
    public ResultData selectSpecialPostDetail(@RequestParam Map map) {
        return springCloudService.selectSpecialPostDetail(map);
    }

    /**
     * @Author Don
     * @Description 根据单位查询资源信息
     * @Date 2020/7/14 22:28
     **/
    @GetMapping("/selectResource")
    public ResultData selectResource(@RequestParam Map map) {
        return springCloudService.selectResource(map);
    }

    /**
     * @Author Don
     * @Description 查询黑白名单
     * @Date 2020/7/16 15:07
     **/
    @GetMapping("/selectWhiteOrBlackList")
    public ResultData selectWhiteOrBlackList(@RequestParam Map map) {
        return springCloudService.selectWhiteOrBlackList(map);
    }
}

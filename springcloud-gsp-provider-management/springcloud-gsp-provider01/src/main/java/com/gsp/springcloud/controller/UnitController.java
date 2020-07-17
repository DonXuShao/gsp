package com.gsp.springcloud.controller;

import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.CommonController;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.mapper.CheckPersonMapper;
import com.gsp.springcloud.model.Audit;
import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Score;
import com.gsp.springcloud.service.*;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.AddStatus.ADD_DATA_SUCCESS;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;
import static com.gsp.springcloud.status.UpdateStatus.UPDATE_DATA_SUCCESS;

/**
 * @Author Don
 * @Date: 2020/7/13 11:22
 * @Discription:单位操作Controller
 * @Version 1.0
 **/
@RestController
public class UnitController extends CommonController<MappingUnit> {
    @Autowired
    private UnitService unitService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private TechnicistService technicistService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private SpecialPostService specialPostService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CheckPersonService checkPersonService;

    @Override
    public BaseService<MappingUnit> getBaseService() {
        return unitService;
    }

    /**
     * @Author Don
     * @Description 查询表中所有单位数据
     * @Date 2020/7/13 19:40
     **/
    @GetMapping("/selectAll")
    public ResultData selectAll() {
        List<MappingUnit> list = unitService.selectAll();
        if (null != list || list.size() > 0) {
            return super.operationSuccess(list);
        } else {
            return super.operationFailed("查询失败");
        }
    }

    /**
     * @Author Don
     * @Description 按条件查询单位
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectUnit")
    public ResultData selectUnit(@RequestParam Map map) {
        Map<String, Object> resultMap = unitService.selectUnit(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description :  单位注册
     * @Date 2020/7/17 15:18
     * @Parameter : [map]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PutMapping("/addUnit")
    public ResultData addUnit(@RequestParam Map map) {
        return super.add(map);
    }

    /**
     * @Author Don
     * @Description : 单位审核
     * @Date 2020/7/17 15:55
     * @Parameter : [id, audit_status]
     * @Return  com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/updateProjectById")
    public ResultData updateProjectById(Long id,Integer audit_status){
        MappingUnit mappingUnit = new MappingUnit();
        Map map = new HashMap();
        mappingUnit.setId(id);
        if (audit_status == 0){
            mappingUnit.setAuditStatus(0);
            map.put("mappingUnit",mappingUnit);
            ResultData update = super.update(map);
            if (update != null && !update.equals("")){
                Audit tAudit = new Audit();
                tAudit.setId(Long.parseLong(FileNameUtils.getFileName())).setAuditTime(DateUtils.formatDate(new Date())).setRefId(id).setUserId(mappingUnit.getUserId()).setStatus(0);
                Integer addAudit = auditService.addAudit(tAudit);
                if (addAudit != null && addAudit > 0) {
                    return super.operationSuccess(addAudit,"已将该项目审核状态修改为通过并增加了记录");
                }
                return super.operationSuccess(update,"已将该项目审核状态修改为通过,增加记录失败");
            }
            return super.operationFailed("系统异常，无法修改当前项目的审核状态");
        }if (audit_status == 1){
            mappingUnit.setAuditStatus(1);
            map.put("mappingUnit",mappingUnit);
            ResultData update = super.update(map);
            if (update == null || update.equals("")){
                Audit tAudit = new Audit();
                tAudit.setId(Long.parseLong(FileNameUtils.getFileName())).setAuditTime(DateUtils.formatDate(new Date())).setRefId(id).setUserId(mappingUnit.getUserId()).setStatus(1);
                Integer addAudit = auditService.addAudit(tAudit);
                if (addAudit != null && addAudit > 0) {
                    return super.operationSuccess(addAudit,"未通过审核并增加了记录");
                }
                return super.operationSuccess(update,"未通过审核,增加记录失败");
            }
            return super.operationFailed("系统异常，无法修改当前项目的审核状态");
        }
        return super.operationFailed();
    }

    /**
     * @Author Don
     * @Description 评分记录
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectScoreRecords")
    public ResultData selectScoreRecords(@RequestParam Map map) {
        Map<String, Object> resultMap = scoreService.selectScoreRecords(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description :  新增或者修改评分记录
     * @Date 2020/7/17 10:09
     * @Parameter : [map]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/addOrUpdateScoreRecords")
    public ResultData addOrUpdateScoreRecords(@RequestParam Map map) {
        Map<String, Object> resultMap = scoreService.addScoreRecords(map);
        if (ADD_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            //记录新增或者修改成功 修改单位表的分值
            Map<String, Object> updateUnitResult = unitService.updateMappingUnit(map);
            //TODO 文件添加操作 需联合资源表与单位表
            if (UPDATE_DATA_SUCCESS.getCode().equals(updateUnitResult.get("code"))) {
                return super.updateSuccess(resultMap.get("data") + "");
            } else {
                return super.updateFailed();
            }
        } else {
            return super.updateFailed();
        }
    }

    /**
     * @Author Don
     * @Description 负责人列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectHeaderOfUnit")
    public ResultData selectHeaderOfUnit(@RequestParam Map map) {
        Map<String, Object> resultMap = principalService.selectHeaderOfUnit(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 负责人详细信息
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectHeaderOfUnitDetail")
    public ResultData selectHeaderOfUnitDetail(@RequestParam Map map) {
        Map<String, Object> resultMap = principalService.selectHeaderOfUnitDetail(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 技术人员列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectTechnicalPerson")
    public ResultData selectTechnicalPerson(@RequestParam Map map) {
        Map<String, Object> resultMap = technicistService.selectTechnicalPerson(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 技术人员列表
     * @Date 2020/7/14 10:40
     **/
    @GetMapping("/selectTechnicalPersonDetail")
    public ResultData selectTechnicalPersonDetail(@RequestParam Map map) {
        Map<String, Object> resultMap = technicistService.selectTechnicalPersonDetail(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 查询审核记录
     * @Date 2020/7/14 21:47
     **/
    @GetMapping("/selectAuditRecords")
    public ResultData selectAuditRecords(@RequestParam Map map) {
        Map<String, Object> resultMap = auditService.selectAuditRecords(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 查询仪器设备列表
     * @Date 2020/7/14 21:55
     **/
    @GetMapping("/selectEquipment")
    public ResultData selectEquipment(@RequestParam Map map) {
        Map<String, Object> resultMap = equipmentService.selectEquipment(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 查询仪器设备详细信息
     * @Date 2020/7/14 22:00
     **/
    @GetMapping("/selectEquipmentDetail")
    public ResultData selectEquipmentDetail(@RequestParam Map map) {
        Map<String, Object> resultMap = equipmentService.selectEquipmentDetail(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 特殊岗位人员信息列表
     * @Date 2020/7/14 22:12
     **/
    @GetMapping("/selectSpecialPost")
    public ResultData selectSpecialPost(@RequestParam Map map) {
        Map<String, Object> resultMap = specialPostService.selectSpecialPost(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 查看特殊岗位人员详细信息
     * @Date 2020/7/14 22:15
     **/
    @GetMapping("/selectSpecialPostDetail")
    public ResultData selectSpecialPostDetail(@RequestParam Map map) {
        Map<String, Object> resultMap = specialPostService.selectSpecialPostDetail(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 根据单位查询资源信息
     * @Date 2020/7/14 22:28
     **/
    @GetMapping("/selectResource")
    public ResultData selectResource(@RequestParam Map map) {
        Map<String, Object> resultMap = resourceService.selectResource(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 查询黑白名单
     * @Date 2020/7/16 15:07
     **/
    @GetMapping("/selectWhiteOrBlackList")
    public ResultData selectWhiteOrBlackList(@RequestParam Map map) {
        Map<String, Object> resultMap = unitService.selectWhiteOrBlackList(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 随机按照比例以及区进行查询
     * @Date 2020/7/16 15:30
     **/
    @GetMapping("/selectRandomCheckUnit")
    public ResultData selectRandomCheckUnit(@RequestParam Map map) {
        Map<String, Object> resultMap = unitService.selectRandomCheckUnit(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }

    /**
     * @Author Don
     * @Description 随机抽查人员
     * @Date 2020/7/16 16:42
     **/
    @GetMapping("/selectRandomPerson")
    public ResultData selectRandomPerson(@RequestParam Map map) {
        Map<String, Object> resultMap = checkPersonService.selectRandomPerson(map);
        if (SELECT_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            return super.selectSuccess(resultMap.get("data"));
        } else {
            return super.selectFailed();
        }
    }
}

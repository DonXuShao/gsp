package com.gsp.springcloud.controller;

import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.CommonController;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;

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
}

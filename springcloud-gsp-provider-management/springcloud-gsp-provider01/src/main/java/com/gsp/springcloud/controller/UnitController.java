package com.gsp.springcloud.controller;

import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.CommonController;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.model.*;
import com.gsp.springcloud.service.*;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.AddStatus.ADD_DATA_SUCCESS;
import static com.gsp.springcloud.status.DeleteStatus.DELETE_DATA_SUCCESS;
import static com.gsp.springcloud.status.OperationStatus.SUCCESS;
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
    @Autowired
    private UploadService uploadService;

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
     * @Description :  单位注册或者修改
     * @Date 2020/7/17 15:18
     * @Parameter : [map]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/addOrUpdateMappingUnit")
    public ResultData addOrUpdateMappingUnit(MappingUnit mappingUnit) {
        Map<String, Object> map = unitService.addOrUpdateMappingUnit(mappingUnit);
        if (SUCCESS.getCode().equals(map.get("code"))) {
            return super.operationSuccess(map);
        } else {
            return super.operationFailed();
        }
    }

    /**
     * @Author Don
     * @Description : 单位审核
     * @Date 2020/7/17 15:55
     * @Parameter : [id, audit_status]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/updateMappingUnitAudit")
    public ResultData updateMappingUnitAudit(@RequestParam Map map) {
        Map<String, Object> resultMap = unitService.updateMappingUnitAudit(map);

        if (UPDATE_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            //判断如果审核状态修改后 要在审核记录表中添加一条审核记录
            Integer addAuditResult;
            Integer audit_status = Integer.parseInt(map.get("audit_status") + "");

            MappingUnit mappingUnit = new MappingUnit();
            Audit audit = new Audit();
            mappingUnit.setId(Long.parseLong(map.get("id") + ""));
            MappingUnit mappingUnit1 = unitService.selectOne(mappingUnit);

            audit.setId(Long.parseLong(FileNameUtils.getFileName()));
            audit.setName(map.get("name") + "");
            audit.setType(Integer.parseInt(map.get("type") + ""));
            audit.setUserId(mappingUnit1.getUserId());
            audit.setAuditTime(DateUtils.formatDate(new Date()));
            audit.setRefId(mappingUnit1.getUserId());
            audit.setStatus(audit_status);
            audit.setCreateTime(DateUtils.formatDate(new Date()));
            audit.setMemo(map.get("memo") + "");

            addAuditResult = auditService.add(audit);
            if (addAuditResult > 0) {
                return super.operationSuccess(resultMap.get("data"));
            }
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
    public ResultData addOrUpdateScoreRecords(@RequestParam Map map,MultipartFile file) {

        Map<String, Object> resultMap = scoreService.addScoreRecords(map);
        //如果添加分值成功 则判断
        if (ADD_DATA_SUCCESS.getCode().equals(resultMap.get("code"))) {
            //记录新增或者修改成功 修改单位表的分值
            Map<String, Object> updateUnitResult = unitService.updateMappingUnit(map);

            if (UPDATE_DATA_SUCCESS.getCode().equals(updateUnitResult.get("code"))) {
                //添加文件操作
                Resource resource = new Resource();
//                MultipartFile file = (MultipartFile) map.get("file");
                if (file != null) {
                    //7.2.文件上传到ftp服务器上
                    FtpFile fileName = uploadService.upload(file, (String) map.get("fileName"));
                    //7.3.判断文件是否上传成功
                    if (fileName != null) {
                        //成功
                        //7.4.将map中的resource的相关value值放到resource中
                        resource.setName(fileName.getFileName());
                        resource.setSize(Long.parseLong(map.get("size") + ""));
                        resource.setPath(fileName.getFilePath());
                        resource.setType(map.get("type") + "");
                        resource.setExtName(fileName.getDir());
                        resource.setRefBizType("附件");
                        resource.setRefBizId(Long.parseLong(map.get("id") + ""));
                        resource.setMemo((String) map.get("memo"));
                        //7.5.对resource添加到数据库中
                        Map<String, Object> addResourceResult = resourceService.insertResouce(resource);
                        if (ADD_DATA_SUCCESS.getCode().equals(addResourceResult.get("code"))) {
                            return super.updateSuccess(resultMap.get("data") + "");
                        }
                    }
                }
                return super.updateFailed();
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

    /**
     * @Author Don
     * @Description :  新增或者修改抽查人员信息
     * @Date 2020/7/17 16:37
     * @Parameter : [map]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/addOrUpdateCheckPerson")
    public ResultData addOrUpdateCheckPerson(CheckPerson checkPerson) {
        Map<String, Object> map = checkPersonService.addOrUpdateCheckPerson(checkPerson);
        if (SUCCESS.getCode().equals(map.get("code"))) {
            return super.operationSuccess(map);
        } else {
            return super.operationFailed();
        }
    }

    /**
     * @Author Don
     * @Description :  删除抽查人员
     * @Date 2020/7/17 17:33
     * @Parameter : [map]
     * @Return com.gsp.springcloud.base.ResultData
     **/
    @PostMapping("/deleteCheckPerson")
    public ResultData deleteCheckPerson(CheckPerson checkPerson) {
        Map<String, Object> map = checkPersonService.deleteCheckPerson(checkPerson);
        if (DELETE_DATA_SUCCESS.getCode().equals(map.get("code"))) {
            return super.operationSuccess(map);
        } else {
            return super.operationFailed();
        }
    }
}

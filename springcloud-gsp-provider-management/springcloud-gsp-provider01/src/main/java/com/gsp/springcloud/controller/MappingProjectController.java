package com.gsp.springcloud.controller;

import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.CommonController;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.model.Audit;
import com.gsp.springcloud.model.MappingProject;
import com.gsp.springcloud.service.AuditService;
import com.gsp.springcloud.service.MappingProjectService;
import com.gsp.springcloud.service.UploadService;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.gsp.springcloud.status.AddStatus.*;

/**
 * @author XRF
 * @date 2020/7/14 21:22
 * 测绘项目的控制层
 */
@RestController
public class MappingProjectController extends CommonController<MappingProject> {

    @Autowired
    private MappingProjectService mappingProjectService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private UploadService uploadService;

    @Override
    public BaseService getBaseService() {
        return mappingProjectService;
    }


    /**
     * 功能描述: 
     * 查询所有项目信息--->分页
     * paramter : [hashMap]
     * return : com.gsp.springcloud.base.ResultData
     * @author : XRF
     * @date : 2020/7/21 16:27
     */
    @GetMapping("/selectAllPros")
    public ResultData selectAllPros(@RequestParam HashMap hashMap) {
        PageInfo pageInfo = mappingProjectService.selectAlls(hashMap);
        if (null != pageInfo && !"".equals(pageInfo)) {
            return operationSuccess(pageInfo);
        } else {
            return operationFailed("查询失败！");
        }
    }

    /**
     * @author XRF
     * @date 2020/7/15 19:49
     * 点击项目审核查询所有项目信息--->分页
     * @description
     */
    @GetMapping("/auditSelectAllsMp")
    public ResultData auditSelectAllsMp(@RequestParam HashMap hashMap) {
        PageInfo pageInfo = mappingProjectService.auditSelectAllsMp(hashMap);
        if (null != pageInfo && !"".equals(pageInfo)) {
            return operationSuccess(pageInfo);
        } else {
            return operationFailed("查询失败！");
        }
    }

    /**
     * @author XRF
     * @date 2020/7/16 16:48
     * 点击成果汇交查看成果汇交已经处于一个提交状态的项目信息---分页
     * @description
     */
    @GetMapping("/resSelectAllsMp")
    public ResultData resSelectAllsMp(@RequestParam HashMap hashMap) {
        PageInfo pageInfo = mappingProjectService.resSelectAllsMp(hashMap);
        if (null != pageInfo && !"".equals(pageInfo)) {
            return operationSuccess(pageInfo);
        } else {
            return operationFailed("查询失败！");
        }
    }


    /**
     * @author XRF
     * @date 2020/7/15 19:49
     * 根据项目的编号查询该项目下的所有审核信息--》分页
     * @description
     */
    @GetMapping("/selectAuditByMpId")
    public ResultData selectAuditByMpId(@RequestParam HashMap hashMap) {
        PageInfo pageInfo = mappingProjectService.selectAuditByMpId(hashMap);
        if (null != pageInfo && !"".equals(pageInfo)) {
            return operationSuccess(pageInfo);
        } else {
            return operationFailed("查询失败！");
        }
    }

    /**
     * @author XRF
     * @date 2020/7/15 19:55
     * 根据项目名称模糊查询----》分页
     * @description
     */
    @GetMapping("/projectListSelect")
    public ResultData projectListSelect(@RequestParam HashMap hashMap) {
        PageInfo pageInfo = mappingProjectService.projectListSelect(hashMap);
        if (null != pageInfo && !"".equals(pageInfo)) {
            return operationSuccess(pageInfo);
        } else {
            return operationFailed("查询失败！");
        }
    }

    /**
     * @author XRF
     * @date 2020/7/16 11:07
     * 根据项目id查询该项目详细信息
     * @description
     */
    @GetMapping("/selectProjectById")
    public ResultData selectProjectById(@RequestParam("id") Long id) {
        HashMap<String, Object> stringObjectHashMap = mappingProjectService.selectProjectById(id);
        if (stringObjectHashMap.size() > 0) {
            return operationSuccess(stringObjectHashMap);
        } else {
            return operationFailed("查询失败！");
        }
    }

    /**
     * @author XRF
     * @date 2020/7/16 17:19
     * 根据项目编号查看项目的详细信息，以及该项目下的附件
     * 并且查看该项目的汇交结果信息以及该汇交成果下的附件
     * @description
     */
    @GetMapping("/selectAllMpAndResult")
    public ResultData selectAllMpAndResult(@RequestParam("id") Long id) {
        HashMap<String, Object> stringObjectHashMap = mappingProjectService.selectAllMpAndResult(id);
        if (stringObjectHashMap.size() > 0) {
            return operationSuccess(stringObjectHashMap);
        } else {
            return operationFailed("查询失败！");
        }
    }

    /**
     * @author XRF
     * @date 2020/7/17 16:54
     * 成果汇交审核
     * 0 通过，1 未通过
     * @description
     */
    @PostMapping("/updateResultCommitById")
    public ResultData updateResultCommitById(@RequestParam("id") Long id, @RequestParam("results_status") Integer results_status) {
        MappingProject mappingProject = new MappingProject();
        Map map = new HashMap();
        mappingProject.setId(id);
        //如果传进来的audit_status是0代表通过，那么久修改项目表中项目的汇交审核状态，并且向审核表中插入一条数据
        if (results_status == 0) {
            //将状态添加到对象中
            mappingProject.setResultsStatus(0);
            map.put("id", id);
            map.put("results_status", results_status);
            //修改mappingProject表中audit_status数据
            Map<String, Object> update = mappingProjectService.updateMProject(map);
            //todo 在这里应该加上事务处理，在这里，修改操作和添加操作应该是个原子操作
            if (null != update && !("").equals(update)) {
                Audit audit = new Audit();
                //向审核对象中放数据
                audit.setId(Long.parseLong(FileNameUtils.getFileName())).setName("项目成果汇交").setType(4).setUserId(mappingProject.getUserId()).setStatus(mappingProject.getResultsStatus()).setAuditTime(DateUtils.formatDate(new Date())).setCreateTime(DateUtils.formatDate(new Date()));
                //向审核表中添加修改数据
                Integer addAudit = auditService.addAudit(audit);
                if (null != addAudit && addAudit > 0) {
                    return operationSuccess(addAudit, "已经修改项目成果汇交状态，并且向审核记录中添加了一条数据！");
                }
                return operationFailed("已经修改项目成果汇交状态,但是没有向审核表中添加数据！");
            }
            return operationFailed("系统异常，没有修改测绘项目表中数据");
        } else if (results_status == 1) {
            //将状态添加到对象中
            mappingProject.setResultsStatus(1);
            map.put("MappingProject", mappingProject);
            //修改mappingProject表中audit_status数据
            ResultData update = super.update(map);
            if (null != update && !("").equals(update)) {
                Audit audit = new Audit();
                //向审核对象中放数据
                audit.setId(Long.parseLong(FileNameUtils.getFileName())).setName("项目成果汇交").setType(4).setUserId(mappingProject.getUserId()).setStatus(mappingProject.getResultsStatus()).setAuditTime(DateUtils.formatDate(new Date()));
                //向审核表中添加修改数据
                Integer addAudit = auditService.addAudit(audit);
                if (null != addAudit && addAudit > 0) {
                    return operationSuccess(addAudit, "已经修改项目成果汇交状态，并且向审核记录中添加了一条数据！");
                }
                return operationFailed("已经修改项目成果汇交状态,但是没有向审核表中添加数据！");
            }
            return operationFailed("系统异常，没有修改测绘项目表中数据");
        }
        return operationFailed();
    }

    /**
     * @author XRF
     * @date 2020/7/17 19:16
     * 项目审核
     * 0 通过
     * 1 未通过
     * @description
     */
    @PostMapping("/updateMProject")
    public ResultData updateMProject(@RequestParam("id") Long id, @RequestParam("audit_status") Integer audit_status) {
        MappingProject mappingProject = new MappingProject();
        Map map = new HashMap();
        mappingProject.setId(id);
        if (audit_status == 0) {
            //如果是0的话就是审核通过，放进mappingProject对象中
            mappingProject.setAuditStatus(0);
            //将对象放入map中，便于后面的修改操作
            map.put("id", id);
            map.put("audit_status", audit_status);
            //修改t_mapping_project表中数据
            //ResultData update = super.update(map);
            Map<String, Object> update = mappingProjectService.updateMProject(map);
            if (null != update && update.size() > 0) {
                //如果修改成功了，就向审核表中添加一条数据
                Audit audit = new Audit();
                //向对象中添加数据
                audit.setId(Long.parseLong(FileNameUtils.getFileName())).setName(mappingProject.getProjectType()).setType(4).setUserId(mappingProject.getUserId()).setStatus(mappingProject.getAuditStatus()).setAuditTime(DateUtils.formatDate(new Date())).setCreateTime(DateUtils.formatDate(new Date()));
                //执行向审核表中添加操作
                //todo 执行向审核表中添加一条数据抛出异常
                Integer addAudit = auditService.addAudit(audit);
                if (null != addAudit && addAudit > 0) {
                    //如果非空并且大于0的话就代表添加成功了，返回查询成功的信息
                    return operationSuccess(addAudit, "已经修改项目的审核状态，并且向审核表中添加了一条数据");
                }
                return operationFailed("已经修改了项目的审核状态，但是没有向审核表中添加数据");
            }
            return operationFailed("系统异常，没有修改测绘项目表中数据");
        } else if (audit_status == 1) {//如果审核状态是1
            mappingProject.setAuditStatus(1);
            map.put("MappingProject", mappingProject);
            ResultData update = super.update(map);
            if (null != update && !("").equals(update)) {//如果非空，就代表修改数据成功，向审核表中添加一条数据
                Audit audit = new Audit();
                audit.setId(Long.parseLong(FileNameUtils.getFileName())).setName(mappingProject.getProjectType()).setType(4).setUserId(mappingProject.getUserId()).setStatus(mappingProject.getAuditStatus()).setAuditTime(DateUtils.formatDate(new Date()));
                Integer addAudit = auditService.addAudit(audit);
                if (null != addAudit && addAudit > 0) {//如果大于0，也就是添加成功，返回成功的消息
                    return operationSuccess(addAudit, "已经修改项目的审核状态，并且向审核表中添加了一条数据");
                }
                return operationFailed("已经修改了项目的审核状态，但是没有向审核表中添加数据");
            }
            return operationFailed("系统异常，没有修改测绘项目表中数据");
        }
        return operationFailed();
    }

    /**
      * @author XRF
      * @date  2020/7/20 19:23
     * 添加项目
     * 没有高程基准
      * @description
      */
    @PostMapping("/addMProjectOrUpdateMProject")
    public ResultData addMProjectOrUpdateMProject(HashMap hashMap, MultipartFile[] files){
        MappingProject mappingProject = new MappingProject();
        mappingProject.setProjectName(hashMap.get("projectName")+"").setCoordinateSystem(hashMap.get("coordinateSystem")+"").setMeridian(hashMap.get("meridian")+"").setProjectType(hashMap.get("projectType")+"").setManagementLevel(hashMap.get("managementLevel")+"")
        .setFundingSource(hashMap.get("fundingSource")+"").setEntrustUnit(hashMap.get("entrustUnit")+"")
        .setEntrustUnit(hashMap.get("entrustUnit")+"").setAcceptUnit(hashMap.get("acceptUnit")+"").setProjectAmount(Double.valueOf(hashMap.get("projectAmount")+""))
        .setProjectLeader(hashMap.get("projectLeader")+"").setMobilePhone(hashMap.get("mobilePhone")+"").setPhone(hashMap.get("phone")+"")
        .setAddress(hashMap.get("address")+"").setStartDate(DateUtils.formatDate(hashMap.get("startDate"))).setEndDate(DateUtils.formatDate(hashMap.get("endDate")))
        .setAcceptanceDepartment(hashMap.get("acceptanceDepartment")+"").setAcceptanceReport(hashMap.get("acceptanceReport")+"").setTaskSource(hashMap.get("taskSource")+"")
        .setProjectArea(Double.valueOf(hashMap.get("projectArea")+"")).setScale(hashMap.get("scale")+"")
        .setSheetNumber(hashMap.get("sheetNumber")+"").setAwardsDepartment(hashMap.get("awardsDepartment")+"").setPrizeLevel(hashMap.get("prizeLevel")+"").setProjectQualityApproval(hashMap.get("projectQualityApproval")+"")
        .setWinningTime(hashMap.get("winningTime")+"").setAcceptanceTime(hashMap.get("acceptanceTime")+"").setBasicContent(hashMap.get("basicContent")+"").setCreditStatus(hashMap.get("creditStatus")+"")
        .setSubmitStatus(hashMap.get("submitStatus")+"").setMemo(hashMap.get("memo")+"");
        Map<String, Object> addMProjectMap = mappingProjectService.addMProject(mappingProject, files, uploadService);
        if (ADD_DATA_SUCCESS.getCode().equals(addMProjectMap.get("code"))) {
            return super.addSuccess();
        }else {
            return super.addFailed();
        }
    }

}

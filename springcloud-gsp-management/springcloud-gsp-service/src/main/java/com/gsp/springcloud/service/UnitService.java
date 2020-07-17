package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.mapper.AuditMapper;
import com.gsp.springcloud.mapper.UnitMapper;
import com.gsp.springcloud.model.Audit;
import com.gsp.springcloud.model.CheckPerson;
import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Score;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.OperationStatus.SUCCESS;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;
import static com.gsp.springcloud.status.UpdateStatus.UPDATE_DATA_FAILED;
import static com.gsp.springcloud.status.UpdateStatus.UPDATE_DATA_SUCCESS;

/**
 * @Author Don
 * @Date: 2020/7/13 11:19
 * @Discription:
 * @Version 1.0
 **/
@Service
public class UnitService extends BaseService<MappingUnit> {

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    AuditService auditService;

    /**
     * @return
     * @Author Don
     * @Description 查询所有单位信息
     * @Date 2020/7/13 19:55
     */
    public List<MappingUnit> selectAll() {
        return super.selectList(null);
    }

    /**
     * @Author Don
     * @Description 条件查询单位
     * @Date 2020/7/14 9:56
     **/
    public Map<String, Object> selectUnit(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();
        List<HashMap> resultdata;

        MappingUnit tMappingUnit = new MappingUnit();
        if (null != map.get("id")) {
            tMappingUnit.setId(Long.parseLong(map.get("id") + ""));
        }
        if (null != map.get("auditStatus")) {
            tMappingUnit.setAuditStatus(Integer.valueOf(map.get("auditStatus") + ""));
        }
        if (null != map.get("qualificationLevel")) {
            tMappingUnit.setQualificationLevel(map.get("qualificationLevel") + "");
        }
        if (null != map.get("ownedDistrict")) {
            tMappingUnit.setOwnedDistrict(map.get("ownedDistrict") + "");
        }
        if (null != map.get("unit_name")) {
            tMappingUnit.setUnitName(map.get("unit_name") + "");
        }


        if (null == tMappingUnit) {
            resultdata = unitMapper.selectAllUnit();
        } else {
            resultdata = unitMapper.selectUnit(tMappingUnit);
        }
        PageHelper.startPage(Integer.parseInt(map.get("currentPage") + ""), Integer.parseInt(map.get("pageSize") + ""));
        PageInfo pageInfo = new PageInfo(resultdata);

        if (resultdata != null && resultdata.size() > 0) {
            resultMap.put("code", SELECT_DATA_SUCCESS.getCode());
            resultMap.put("msg", SELECT_DATA_SUCCESS.getMsg());
            resultMap.put("data", pageInfo);
        } else {
            resultMap.put("code", SELECT_DATA_FAILED.getCode());
            resultMap.put("msg", SELECT_DATA_FAILED.getMsg());
        }
        return resultMap;
    }

    /**
     * @Author Don
     * @Description 查询黑白名单
     * @Date 2020/7/16 15:06
     **/
    public Map<String, Object> selectWhiteOrBlackList(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();
        MappingUnit tMappingUnit = new MappingUnit();
        if (null != map.get("unit_status")) {
            tMappingUnit.setUnitStatus(Integer.parseInt(map.get("unit_status") + ""));
        }
        List<MappingUnit> mappingUnits = super.selectList(tMappingUnit);
        PageHelper.startPage(Integer.parseInt(map.get("currentPage") + ""), Integer.parseInt(map.get("pageSize") + ""));
        PageInfo pageInfo = new PageInfo(mappingUnits);
        if (mappingUnits != null && mappingUnits.size() > 0) {
            resultMap.put("code", SELECT_DATA_SUCCESS.getCode());
            resultMap.put("msg", SELECT_DATA_SUCCESS.getMsg());
            resultMap.put("data", pageInfo);
        } else {
            resultMap.put("code", SELECT_DATA_FAILED.getCode());
            resultMap.put("msg", SELECT_DATA_FAILED.getMsg());
        }
        return resultMap;
    }

    /**
     * @Author Don
     * @Description 随机按照比例查询
     * @Date 2020/7/16 15:29
     **/
    public Map<String, Object> selectRandomCheckUnit(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        List<HashMap> resultdata = unitMapper.selectRandomCheckUnit(map);

        PageHelper.startPage(Integer.parseInt(map.get("currentPage") + ""), Integer.parseInt(map.get("pageSize") + ""));
        PageInfo pageInfo = new PageInfo(resultdata);

        if (resultdata != null && resultdata.size() > 0) {
            resultMap.put("code", SELECT_DATA_SUCCESS.getCode());
            resultMap.put("msg", SELECT_DATA_SUCCESS.getMsg());
            resultMap.put("data", pageInfo);
        } else {
            resultMap.put("code", SELECT_DATA_FAILED.getCode());
            resultMap.put("msg", SELECT_DATA_FAILED.getMsg());
        }
        return resultMap;
    }

    /**
     * @Author Don
     * @Description :  单位审核
     * 更新单位审核状态，添加审核记录
     * @Date 2020/7/17 19:06
     * @Parameter : [map]
     * @Return  java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> updateMappingUnitAudit(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Audit audit = new Audit();
        MappingUnit mappingUnit = new MappingUnit();
        Integer updateResult;
        Integer addAuditResult;

        mappingUnit.setId(Long.parseLong(map.get("id") + ""));
        Integer audit_status = Integer.parseInt(map.get("audit_status") + "");
        if (audit_status == 0) {
            mappingUnit.setAuditStatus(0);
        } else if (audit_status == 1) {
            mappingUnit.setAuditStatus(1);
        }
        updateResult = super.update(mappingUnit);
        MappingUnit mappingUnit1 = super.selectOne(mappingUnit);
        if (updateResult > 0) {
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
            if (addAuditResult != null && addAuditResult > 0) {
                resultMap.put("code", UPDATE_DATA_SUCCESS.getCode());
                resultMap.put("msg", UPDATE_DATA_SUCCESS.getMsg());
                resultMap.put("data", updateResult);
            } else {
                resultMap.put("code", UPDATE_DATA_FAILED.getCode());
                resultMap.put("msg", UPDATE_DATA_FAILED.getMsg());
            }
        }
        return resultMap;
    }

    /**
     * @Author Don
     * @Description :  更新单位表信息
     * @Date 2020/7/17 10:07
     * @Parameter : [map]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    public Map<String, Object> updateMappingUnit(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        MappingUnit mappingUnit = new MappingUnit();

        if (map.get("id") != null) {
            mappingUnit.setId(Long.parseLong(map.get("id") + ""));
        }
        //分值操作
        if (map.get("score") != null) {
            if (map.get("score_plus") != null && !"".equals(map.get("score_plus"))) {
                mappingUnit.setScore(Integer.parseInt(map.get("score") + "") + Integer.parseInt(map.get("score_plus") + ""));
            }
            if (map.get("score_subtract") != null && !"".equals(map.get("score_subtract"))) {
                mappingUnit.setScore(Integer.parseInt(map.get("score") + "") - Integer.parseInt(map.get("score_subtract") + ""));
            }
        }
        //审核状态
        if (map.get("audit_status") != null) {
            mappingUnit.setAuditStatus((Integer) map.get("audit_status"));
        }


        Integer updateResult = super.update(mappingUnit);

        //根据分数判断其黑白名单状态
        MappingUnit mappingUnit1 = super.selectOne(mappingUnit);
        if (mappingUnit1.getScore() >= 100) {
            mappingUnit.setUnitStatus(1);
        } else if (mappingUnit1.getScore() < 60) {
            mappingUnit.setUnitStatus(2);
        } else {
            mappingUnit.setUnitStatus(3);
        }
        super.update(mappingUnit);


        if (updateResult > 0) {
            resultMap.put("code", UPDATE_DATA_SUCCESS.getCode());
            resultMap.put("msg", UPDATE_DATA_SUCCESS.getMsg());
            resultMap.put("data", updateResult);
        }
        return resultMap;
    }

    /**
     * @Author Don
     * @Description :  注册或者修改单位信息
     * @Date 2020/7/17 17:13
     * @Parameter : [mappingUnit]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    public Map<String, Object> addOrUpdateMappingUnit(MappingUnit mappingUnit) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Integer operation = null;
        if (mappingUnit.getId() != null) {
            mappingUnit.setModifyTime(DateUtils.formatDate(new Date()));
            operation = super.update(mappingUnit);
        } else {
            mappingUnit.setId(Long.parseLong(FileNameUtils.getFileName()));
            mappingUnit.setCreateTime(DateUtils.formatDate(new Date()));
            operation = super.add(mappingUnit);
        }

        if (operation > 0) {
            resultMap.put("code", SUCCESS.getCode());
            resultMap.put("msg", SUCCESS.getMsg());
            resultMap.put("data", operation);
        } else {
            resultMap.put("code", SUCCESS.getCode());
            resultMap.put("msg", SUCCESS.getMsg());
        }
        return resultMap;
    }

}

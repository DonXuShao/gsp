package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.mapper.UnitMapper;
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

import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;
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
     * @Description :  更新单位表信息
     * @Date 2020/7/17 10:07
     * @Parameter : [map]
     * @Return  java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> updateMappingUnit(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        MappingUnit mappingUnit = new MappingUnit();

        if (map.get("id") != null) {
            mappingUnit.setId(Long.parseLong(map.get("id") + ""));
        }
        //分值操作
        if (map.get("score") != null) {
            if (map.get("score_plus") != null && ! "".equals(map.get("score_plus"))) {
                mappingUnit.setScore(Integer.parseInt(map.get("score") + "") + Integer.parseInt(map.get("score_plus") + ""));
            }
            if (map.get("score_subtract") != null && ! "".equals(map.get("score_subtract"))) {
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


}

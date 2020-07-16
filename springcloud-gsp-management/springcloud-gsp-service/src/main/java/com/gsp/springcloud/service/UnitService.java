package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.UnitMapper;
import com.gsp.springcloud.model.MappingUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;

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



}

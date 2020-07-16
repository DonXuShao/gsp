package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.EquipmentMapper;
import com.gsp.springcloud.model.Equipment;
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
 * @Date: 2020/7/14 21:52
 * @Discription:仪器设备操作Service
 * @Version 1.0
 **/
@Service
public class EquipmentService extends BaseService<Equipment> {

    @Autowired
    private EquipmentMapper equipmentMapper;

    /**
     * @Author Don
     * @Description 仪器设备列表
     * @Date 2020/7/14 21:54
     **/
    public Map<String, Object> selectEquipment(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        MappingUnit tMappingUnit = new MappingUnit();
        if (null != map.get("id")) {
            tMappingUnit.setId(Long.parseLong(map.get("id") + ""));
        }

        List<HashMap> resultdata = equipmentMapper.selectEquipment(tMappingUnit);
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
     * @Description 查看仪器设备详细信息
     * @Date 2020/7/14 22:14
     **/
    public Map<String, Object> selectEquipmentDetail(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        Equipment equipment = new Equipment();
        if (null != map.get("id")) {
            equipment.setId(Long.parseLong(map.get("id") + ""));
        }

        List<HashMap> resultdata = equipmentMapper.selectEquipmentDetail(equipment);

        if (resultdata != null && resultdata.size() > 0) {
            resultMap.put("code", SELECT_DATA_SUCCESS.getCode());
            resultMap.put("msg", SELECT_DATA_SUCCESS.getMsg());
            resultMap.put("data", resultdata);
        } else {
            resultMap.put("code", SELECT_DATA_FAILED.getCode());
            resultMap.put("msg", SELECT_DATA_FAILED.getMsg());
        }
        return resultMap;
    }
}

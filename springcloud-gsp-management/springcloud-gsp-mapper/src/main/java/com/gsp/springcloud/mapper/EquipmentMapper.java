package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.Equipment;
import com.gsp.springcloud.model.MappingUnit;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Don
 * @Date: 2020/7/14 21:50
 * @Discription:仪器设备Mapper
 * @Version 1.0
 **/
@Repository
public interface EquipmentMapper extends Mapper<Equipment> {
    /**
     * @Author Don
     * @Description 仪器设备列表
     * @Date 2020/7/14 21:39
     **/
    List<HashMap> selectEquipment(MappingUnit tMappingUnit);

    /**
     * @Author Don
     * @Description  查看设备详细信息
     * @Date 2020/7/14 21:59
     **/
    List<HashMap> selectEquipmentDetail(Equipment equipment);
}

package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.MappingUnit;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Don
 * @Date: 2020/7/13 11:17
 * @Discription:单位操作Mapper
 * @Version 1.0
 **/
@Repository
public interface UnitMapper extends Mapper<MappingUnit> {

    /**
     * @Author Don
     * @Description 查询所有的单位信息
     * @Date 2020/7/14 9:45
     **/
    List<HashMap> selectAllUnit();

    /**
     * @Author Don
     * @Description 条件查询单位
     * @Date 2020/7/14 9:56
     **/
    List<HashMap> selectUnit(MappingUnit tMappingUnit);

    /**
     * @Author Don
     * @Description 随机抽查单位
     * @Date 2020/7/16 15:15
     **/
    List<HashMap> selectRandomCheckUnit(Map map);


}

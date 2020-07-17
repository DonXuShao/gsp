package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Technicist;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Don
 * @Date: 2020/7/14 21:13
 * @Discription:技术人员Mapper
 * @Version 1.0
 **/
@Repository
public interface TechnicistMapper extends Mapper<Technicist> {
    /**
     * @Author Don
     * @Description  技术人员信息列表
     * @Date 2020/7/14 20:30
     **/
    List<HashMap> selectTechnicalPerson(MappingUnit tMappingUnit);


}

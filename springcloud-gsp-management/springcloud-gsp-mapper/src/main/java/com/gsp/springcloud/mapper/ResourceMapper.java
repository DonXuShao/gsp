package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Resource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Don
 * @Date: 2020/7/14 22:21
 * @Discription:资源mapper
 * @Version 1.0
 **/
@Repository
public interface ResourceMapper extends Mapper<Resource> {

    /**
     * @Author Don
     * @Description  根据单位查询资源信息
     * @Date 2020/7/14 22:23 
     **/
    List<HashMap> selectResource(MappingUnit tMappingUnit);
}

package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Principal;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Don
 * @Date: 2020/7/14 20:23
 * @Discription:
 * @Version 1.0
 **/
@Repository
public interface PrincipalMapper extends Mapper<Principal> {
    /**
     * @Author Don
     * @Description  负责人信息列表
     * @Date 2020/7/14 20:30
     **/
    List<HashMap> selectHeaderOfUnit(MappingUnit tMappingUnit);

    /**
     * @Author Don
     * @Description  负责人详细
     * @Date 2020/7/14 20:30
     **/
    List<HashMap> selectHeaderOfUnitDetail(Principal principal);
}

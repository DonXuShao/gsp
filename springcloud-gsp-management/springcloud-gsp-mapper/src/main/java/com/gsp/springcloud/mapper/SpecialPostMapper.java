package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.SpecialPost;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Don
 * @Date: 2020/7/14 22:07
 * @Discription:
 * @Version 1.0
 **/

@Repository
public interface SpecialPostMapper extends Mapper<SpecialPost> {
    /**
     * @Author Don
     * @Description 特殊岗位人员信息列表
     * @Date 2020/7/14 22:06
     **/
    List<HashMap> selectSpecialPost(MappingUnit tMappingUnit);

    /**
     * @Author Don
     * @Description 特殊岗位人员详细信息
     * @Date 2020/7/14 22:12
     **/
    List<HashMap> selectSpecialPostDetail(SpecialPost specialPost);
}
package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.CheckPerson;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Don
 * @Date: 2020/7/16 16:29
 * @Discription: 随机抽查人员
 * @Version 1.0
 **/
@Repository
public interface CheckPersonMapper extends Mapper<CheckPerson> {

    /**
     * @Author Don
     * @Description  随机抽查人员
     * @Date 2020/7/16 16:30 
     **/
    List<HashMap> selectRandomPerson(Map map);

}

package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Score;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Don
 * @Date: 2020/7/14 17:16
 * @Discription:分数操作Mapper
 * @Version 1.0
 **/
@Repository
public interface ScoreMapper extends Mapper<Score> {
    /**
     * @Author Don
     * @Description  评分记录
     * @Date 2020/7/14 10:49
     **/
    List<HashMap> selectScoreRecords(MappingUnit tMappingUnit);
}

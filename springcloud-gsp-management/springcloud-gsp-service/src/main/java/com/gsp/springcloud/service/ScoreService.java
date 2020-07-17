package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.ScoreMapper;
import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Score;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.AddStatus.ADD_DATA_SUCCESS;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;
import static com.gsp.springcloud.status.UpdateStatus.UPDATE_DATA_SUCCESS;

/**
 * @Author Don
 * @Date: 2020/7/14 19:02
 * @Discription:分值操作Service
 * @Version 1.0
 **/
@Service
public class ScoreService extends BaseService<Score> {
    @Autowired
    private ScoreMapper scoreMapper;

    /**
     * @Author Don
     * @Description 评分记录
     * @Date 2020/7/14 9:56
     **/
    public Map<String, Object> selectScoreRecords(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        MappingUnit tMappingUnit = new MappingUnit();
        if (null != map.get("id")) {
            tMappingUnit.setId(Long.parseLong(map.get("id") + ""));
        }

        List<HashMap> resultdata = scoreMapper.selectScoreRecords(tMappingUnit);
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
     * @Description 新增分值记录
     * @Date 2020/7/16 18:46
     **/
    public Map<String, Object> addScoreRecords(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        Score score = new Score();
        if (map.get("score_plus") != null && ! "".equals(map.get("score_plus"))) {
            score.setScorePlus(Integer.parseInt(map.get("score_plus")+""));
        }
        if (map.get("score_subtract") != null && ! "".equals(map.get("score_subtract"))) {
            score.setScoreSubtract(Integer.parseInt( map.get("score_subtract")+""));
        }
        if (map.get("score") != null) {
            score.setScore(Integer.parseInt( map.get("score")+""));
        }
        if (map.get("id") != null) {
            score.setUnitId(Long.parseLong(map.get("id")+""));
        }
        if (map.get("reason") != null) {
            score.setReason(map.get("reason")+"");
        }
        score.setId(Long.parseLong(FileNameUtils.getFileName()));
        score.setCreateTime(DateUtils.formatDate(new Date()));
        Integer addResult = super.add(score);


        if (addResult > 0) {
            resultMap.put("code", ADD_DATA_SUCCESS.getCode());
            resultMap.put("msg", ADD_DATA_SUCCESS.getMsg());
            resultMap.put("data", addResult);
        }
        return resultMap;
    }
}

package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.mapper.CheckPersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;

/**
 * @Author Don
 * @Date: 2020/7/16 16:39
 * @Discription:
 * @Version 1.0
 **/
@Service
public class CheckPersonService {

    @Autowired
    private CheckPersonMapper checkPersonMapper;
    
    /**
     * @Author Don
     * @Description  随机抽查人员
     * @Date 2020/7/16 16:40 
     **/
    public Map<String, Object> selectRandomPerson(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        List<HashMap> resultdata = checkPersonMapper.selectRandomPerson(map);

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
}

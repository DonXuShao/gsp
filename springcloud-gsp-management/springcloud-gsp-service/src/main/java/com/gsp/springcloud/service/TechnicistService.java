package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.TechnicistMapper;
import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Technicist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;

/**
 * @Author Don
 * @Date: 2020/7/14 21:24
 * @Discription:技术人员Service
 * @Version 1.0
 **/
@Service
public class TechnicistService extends BaseService<Technicist> {
    @Autowired
    private TechnicistMapper technicistMapper;

    /**
     * @Author Don
     * @Description 查询负责人信息
     * @Date 2020/7/14 20:31
     **/
    public Map<String, Object> selectTechnicalPerson(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        MappingUnit tMappingUnit = new MappingUnit();
        if (null != map.get("id")) {
            tMappingUnit.setId(Long.parseLong(map.get("id") + ""));
        }

        List<HashMap> resultdata = technicistMapper.selectTechnicalPerson(tMappingUnit);
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
     * @Description  技术人员详细信息
     * @Date 2020/7/14 21:38
     **/
    public Map<String, Object> selectTechnicalPersonDetail(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        Technicist technicist = new Technicist();
        if (null != map.get("id")) {
            technicist.setId(Long.parseLong(map.get("id") + ""));
        }

        technicist = super.selectOne(technicist);

        if (technicist != null) {
            resultMap.put("code", SELECT_DATA_SUCCESS.getCode());
            resultMap.put("msg", SELECT_DATA_SUCCESS.getMsg());
            resultMap.put("data", technicist);
        } else {
            resultMap.put("code", SELECT_DATA_FAILED.getCode());
            resultMap.put("msg", SELECT_DATA_FAILED.getMsg());
        }
        return resultMap;
    }
}

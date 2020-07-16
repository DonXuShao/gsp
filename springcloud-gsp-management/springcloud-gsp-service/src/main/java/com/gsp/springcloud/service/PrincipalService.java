package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.PrincipalMapper;
import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;

/**
 * @Author Don
 * @Date: 2020/7/14 20:30
 * @Discription:负责人Service
 * @Version 1.0
 **/
@Service
public class PrincipalService extends BaseService<Principal> {

    @Autowired
    private PrincipalMapper principalMapper;

    /**
     * @Author Don
     * @Description 查询负责人信息
     * @Date 2020/7/14 20:31
     **/
    public Map<String, Object> selectHeaderOfUnit(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        MappingUnit tMappingUnit = new MappingUnit();
        if (null != map.get("id")) {
            tMappingUnit.setId(Long.parseLong(map.get("id") + ""));
        }

        List<HashMap> resultdata = principalMapper.selectHeaderOfUnit(tMappingUnit);
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
     * @Description 查询负责人详细信息
     * @Date 2020/7/14 20:31
     **/
    public Map<String, Object> selectHeaderOfUnitDetail(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        Principal principal = new Principal();
        if (null != map.get("id")) {
            principal.setId(Long.parseLong(map.get("id") + ""));
        }

        List<HashMap> resultdata = principalMapper.selectHeaderOfUnitDetail(principal);

        if (resultdata != null && resultdata.size() > 0) {
            resultMap.put("code", SELECT_DATA_SUCCESS.getCode());
            resultMap.put("msg", SELECT_DATA_SUCCESS.getMsg());
            resultMap.put("data", resultdata);
        } else {
            resultMap.put("code", SELECT_DATA_FAILED.getCode());
            resultMap.put("msg", SELECT_DATA_FAILED.getMsg());
        }
        return resultMap;
    }
}

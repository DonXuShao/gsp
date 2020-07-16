package com.gsp.springcloud.service;

import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.ResourceMapper;
import com.gsp.springcloud.model.MappingUnit;
import com.gsp.springcloud.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;

/**
 * @Author Don
 * @Date: 2020/7/14 22:25
 * @Discription:
 * @Version 1.0
 **/
@Service
public class ResourceService extends BaseService<Resource> {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * @Author Don
     * @Description 根据单位查询资源信息
     * @Date 2020/7/14 22:26
     **/
    public Map<String, Object> selectResource(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();

        MappingUnit tMappingUnit = new MappingUnit();
        if (null != map.get("id")) {
            tMappingUnit.setId(Long.parseLong(map.get("id") + ""));
        }

        List<HashMap> resultdata = resourceMapper.selectResource(tMappingUnit);

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

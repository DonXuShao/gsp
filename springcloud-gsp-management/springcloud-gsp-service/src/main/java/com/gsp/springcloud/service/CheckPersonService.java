package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.CheckPersonMapper;
import com.gsp.springcloud.model.CheckPerson;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.status.DeleteStatus.DELETE_DATA_FAILED;
import static com.gsp.springcloud.status.DeleteStatus.DELETE_DATA_SUCCESS;
import static com.gsp.springcloud.status.OperationStatus.SUCCESS;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_SUCCESS;

/**
 * @Author Don
 * @Date: 2020/7/16 16:39
 * @Discription:
 * @Version 1.0
 **/
@Service
public class CheckPersonService extends BaseService<CheckPerson> {

    @Autowired
    private CheckPersonMapper checkPersonMapper;

    /**
     * @Author Don
     * @Description 随机抽查人员
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

    /**
     * @Author Don
     * @Description :  新增或者修改检查人员
     * @Date 2020/7/17 17:10
     * @Parameter : [checkPerson]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    public Map<String, Object> addOrUpdateCheckPerson(CheckPerson checkPerson) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Integer operation = null;
        if (checkPerson.getId() != null) {
            checkPerson.setModifyTime(DateUtils.formatDate(new Date()));
            operation = super.update(checkPerson);
        } else {
            checkPerson.setId(Long.parseLong(FileNameUtils.getFileName()));
            checkPerson.setCreateTime(DateUtils.formatDate(new Date()));
            operation = super.add(checkPerson);
        }

        if (operation > 0) {
            resultMap.put("code", SUCCESS.getCode());
            resultMap.put("msg", SUCCESS.getMsg());
            resultMap.put("data", operation);
        } else {
            resultMap.put("code", SUCCESS.getCode());
            resultMap.put("msg", SUCCESS.getMsg());
        }
        return resultMap;
    }

    /**
     * @Author Don
     * @Description :  删除抽查人员
     * @Date 2020/7/17 17:42 
     * @Parameter : [checkPerson]
     * @Return  java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> deleteCheckPerson(CheckPerson checkPerson) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Integer operation = super.delete(checkPerson);

        if (operation > 0) {
            resultMap.put("code", DELETE_DATA_SUCCESS.getCode());
            resultMap.put("msg", DELETE_DATA_SUCCESS.getMsg());
            resultMap.put("data", operation);
        } else {
            resultMap.put("code", DELETE_DATA_FAILED.getCode());
            resultMap.put("msg", DELETE_DATA_FAILED.getMsg());
        }
        return resultMap;
    }
}

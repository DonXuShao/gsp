package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.ResultCommitMapper;
import com.gsp.springcloud.model.ResultCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_BY_ID_FAILED;
import static com.gsp.springcloud.status.SelectStatus.SELECT_DATA_BY_ID_SUCCESS;

/**
 * @author XRF
 * @date 2020/7/16 10:47
 * 汇交成果业务层
 * @description
 */
@Service
public class ResultCommitService extends BaseService<ResultCommit> {
    @Autowired
    private ResultCommitMapper resultCommitMapper;

    /**
      * @author XRF
      * @date  2020/7/16 15:35
     * 查询所有测绘项目信息---分页
      * @description
      */
    public PageInfo resSelectAlls(HashMap hashMap){
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo")+""),Integer.parseInt(hashMap.get("pageSize")+""));
        PageInfo pageInfo = new PageInfo(resultCommitMapper.resSelectAlls());
        if (null != pageInfo && !("").equals(pageInfo)) {
            return pageInfo;
        }
        return null;
    }

    /**
      * @author XRF
      * @date  2020/7/16 15:46
     * 根据项目名称模糊查询--分页
      * @description
      */
    public PageInfo resProjectListSelect(HashMap hashMap){
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo")+""),Integer.parseInt(hashMap.get("pageSize")+""));
        PageInfo pageInfo = new PageInfo<>(resultCommitMapper.resProjectListSelect(hashMap));
        if (null != pageInfo && !("").equals(pageInfo)) {
            return pageInfo;
        }
        return null;
    }


    /**
      * @author XRF
      * @date  2020/7/16 10:51
     * 根据项目id查询所有项目信息、附件信息和汇交成果信息
      * @description
      */
    public HashMap<String, Object> selectAllMpAndResById(Long id){
        HashMap<String, Object> resultMap = new HashMap<>();
        if (null != id && !("").equals(id)) {
            List<HashMap> restdata = resultCommitMapper.selectAllMpAndResById(id);
            //当查出来的有结果时
            if (restdata.size() > 0) {
                resultMap.put("code", SELECT_DATA_BY_ID_SUCCESS.getCode());
                resultMap.put("msg", SELECT_DATA_BY_ID_SUCCESS.getMsg());
                resultMap.put("data", restdata);
                return resultMap;
            }
        }
        //否则的话就是查询失败
        resultMap.put("code", SELECT_DATA_BY_ID_FAILED.getCode());
        resultMap.put("msg", SELECT_DATA_BY_ID_FAILED.getMsg());
        return resultMap;
    }



}

package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.MappingProject;
import com.gsp.springcloud.model.ResultCommit;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
  * @author XRF
  * @date  2020/7/16 9:51
 * 汇交成果mapper层
  * @description
  */
@Repository
public interface ResultCommitMapper extends Mapper<ResultCommit> {

    /**
      * @author XRF
      * @date  2020/7/16 10:15
     *根据项目的id 查询所有项目信息和附件信息和汇交成果信息
      * @description
      */
    @Select("select * from t_resource tr left join t_mapping_project tmp on tr.ref_biz_id = tmp.id left join t_result_commit trc on trc.ref_id = tmp.id where tr.ref_biz_id=#{id}")
    List<HashMap> selectAllMpAndResById(Long id);

    /**
      * @author XRF
      * @date  2020/7/16 15:27
     * 分页查询所有测绘项目信息
      * @description
      */
    @Select("select tmp.id,tmp.project_type,tmp.project_name,tmp.project_leader,tmp.start_date,tmp.end_date,tmp.complete_time,tmp.acceptance_department,tmp.project_amount from t_result_commit trc inner join t_mapping_project tmp on trc.ref_id=tmp.id ")
    List<MappingProject> resSelectAlls();


    /**
      * @author XRF
      * @date  2020/7/16 15:42
     * 根据项目名称模糊查询---分页
      * @description
      */
    @Select("select tmp.id,tmp.project_type,tmp.project_name,tmp.project_leader,tmp.start_date,tmp.end_date,tmp.complete_time,tmp.acceptance_department,tmp.project_amount from t_result_commit trc inner join t_mapping_project tmp on trc.ref_id=tmp.id where tmp.project_name like '%阳%'")
    List<HashMap> resProjectListSelect(HashMap hashMap);
}
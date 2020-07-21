package com.gsp.springcloud.mapper;

import com.gsp.springcloud.model.Audit;
import com.gsp.springcloud.model.MappingProject;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author XRF
 * 项目测绘Mapper
 * @date 2020/7/14 20:18
 */
@Repository
public interface MappingProjectMapper extends Mapper<MappingProject> {
    /**
     * 根据项目名称模糊查询
     *
     * @param hashMap
     * @return
     */
    List<HashMap> projectListSelect(HashMap hashMap);


    /**
     * 分页查询所有的测绘项目信息
     *
     * @return
     */
    @Select("select id,project_type,project_name,project_leader,start_date,end_date,complete_time,acceptance_department,project_amount,schedule  from t_mapping_project")
    List<MappingProject> selectAlls();


    /**
      * @author XRF
      * @date  2020/7/16 16:17
     * 点击项目审核，查询要审核的项目信息
      * @description
      */
    @Select("select id,project_type,project_name,project_leader,start_date,end_date,acceptance_department,project_amount from t_mapping_project where audit_status = 2")
    List<MappingProject> auditSelectAllsMp();
    /**
      * @author XRF
      * @date  2020/7/16 16:28
     * 点击成果汇交，查看测绘项目信息,也就是说查看的是处于一个提交状态的项目
      * @description
      */
    @Select("select id,project_type,project_name,project_leader,start_date,end_date,acceptance_department,project_amount from t_mapping_project where results_status = 2")
    List<MappingProject> resSelectAllsMp();


    /**
     * 根据项目的id查询测绘项目的信息
     *
     * @return
     */
    List<MappingProject> selectProjectById(Long id);

    /**
     * 根据用户的id查询测绘项目的信息
     *
     * @return
     */
   /* List<MappingProject> selectAllPros(Long id);*/

    /**
     * 根据测绘项目的id查询该测绘项目下的审核记录信息
     *
     * @param hashMap
     * @return
     */
    List<Audit> selectAuditByMpId(HashMap hashMap);

    /**
      * @author XRF
      * @date  2020/7/16 16:52
     * 根据项目编号查看项目的详细信息，以及该项目下的附件
     * 并且查看该项目的汇交结果信息以及该汇交成果下的附件
      * @description
      */
    @Select("select * from t_mapping_project tmp left join t_resource tr on tmp.id=tr.ref_biz_id left join t_result_commit trc on tmp.id=trc.ref_id where tmp.id=#{id}")
    List<HashMap> selectAllMpAndResult(Long id);



}

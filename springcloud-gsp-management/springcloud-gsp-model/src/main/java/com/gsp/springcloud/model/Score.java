package com.gsp.springcloud.model;

import com.gsp.springcloud.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @Author Don
 * @Description  分值实体
 * @Date 2020/7/14 20:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "t_score")
public class Score extends BaseModel {
    /**
     * 编号
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 增加的分值
     */
    @Column(name = "score_plus")
    private Integer scorePlus;

    /**
     * 减少的分值
     */
    @Column(name = "score_subtract")
    private Integer scoreSubtract;

    /**
     * 当前分值
     */
    private Integer score;
    /**
     * 关联单位编号
     */
    @Column(name = "unit_id")
    private Long unitId;
    /**
     * 增加/减少分值的原因
     */
    private String reason;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;
    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private String modifyTime;


}
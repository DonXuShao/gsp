package com.gsp.springcloud.model;

import com.gsp.springcloud.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Don
 * @Description  项目成果汇交实体
 * @Date 2020/7/14 20:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "t_result_commit")
public class ResultCommit extends BaseModel implements Serializable {

    /**
     * 编号
     */
    @Id
    private Long id;

    /**
     * 比例尺
     */
    @Column(name = "plotting_scale")
    private String plottingScale;

    /**
     * 新图号
     */
    @Column(name = "new_figure")
    private String newFigure;

    /**
     * 旧图号
     */
    @Column(name = "old_figure")
    private String oldFigure;

    /**
     * 图名
     */
    private String figure;

    /**
     * 介质类型
     */
    @Column(name = "medium_type")
    private String mediumType;

    /**
     * 成果日期
     */
    @Column(name = "result_date")
    private String resultDate;

    /**
     * 数据格式
     */
    @Column(name = "data_format")
    private String dataFormat;

    /**
     * 成果名称
     */
    private String name;

    /**
     * 生产日期
     */
    @Column(name = "create_date")
    private String createDate;

    /**
     * 高程基准
     */
    @Column(name = "height_datum")
    private Integer heightDatum;

    /**
     * 备注说明
     */
    private String memo;

    /**
     * 关联项目编号
     */
    @Column(name = "ref_id")
    private Long refId;

    /**
     * 中央子午线
     */
    private String meridian;

    /**
     * 坐标系
     */
    private String coordinate;


}
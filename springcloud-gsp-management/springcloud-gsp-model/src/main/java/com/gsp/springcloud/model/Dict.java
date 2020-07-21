package com.gsp.springcloud.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Don
 * @Description  字典实体
 * @Date 2020/7/14 20:38
 **/
@Table(name = "t_dict")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Dict implements Serializable {
    /**
     * 字典ID
     */
    @Id
    @Column(name = "DICT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号", width = 5, orderNum = "1")
    private Long dictId;

    /**
     * 键
     */
    @Column(name = "KEYY")
    @Excel(name = "键", width = 15, orderNum = "2")
    private Long keyy;

    /**
     * 值
     */
    @Column(name = "VALUEE")
    @Excel(name = "值", width = 15, orderNum = "3")
    private String valuee;

    /**
     * 字段名称
     */
    @Column(name = "FIELD_NAME")
    @Excel(name = "字段名称", width = 20, orderNum = "4")
    private String fieldName;

    /**
     * 表名
     */
    @Column(name = "TABLE_NAME")
    @Excel(name = "表名", width = 20, orderNum = "5")
    private String tableName;

}
package com.gsp.springcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Don
 * @Description 随机抽查人员实体类
 * @Date 2020/7/16 16:27
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class CheckPerson implements Serializable {
    private Long id;

    private String name;
    @Column(name = "unit_name")
    private String unitName;

    private String duty;

    private String phone;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;

    private String memo;
}
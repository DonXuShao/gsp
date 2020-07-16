package com.gsp.springcloud.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author Don
 * @Date: 2020/7/8 14:48
 * Discription: 通用返回
 **/
@Data
@Accessors(chain = true)
public class ResultData<T> implements Serializable {
    /**
     * 状态码
     */
    private String code;
    /**
     * 备注
     */
    private String msg;
    /**
     * 详细介绍
     */
    private String detail;
    /**
     * 返回数据
     */
    private T data;
}

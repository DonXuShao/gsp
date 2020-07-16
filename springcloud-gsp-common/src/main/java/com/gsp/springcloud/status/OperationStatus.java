package com.gsp.springcloud.status;

/**
 * @Author Don
 * @Date: 2020/7/9 16:07
 * @Discription: 设置操作状态码
 * @Version 1.0
 **/
public enum OperationStatus {
    SUCCESS("1","操作成功"),
    FAILED("2","操作失败"),
    DELETE_OPERATION_SUCCESS("3","删除成功"),
    DELETE_OPERATION_FAILED("4","删除失败"),
    DELETE_OPERATION_EXIST("5","删除数据不存在"),
    UPDATE_OPERATION_SUCCESS("6","修改成功"),
    UPDATE_OPERATION_FAILED("7","修改失败"),
    UPDATE_OPERATION_EXIST("8","修改数据已存在"),
    INSERT_OPERATION_SUCCESS("9","新增成功"),
    INSERT_OPERATION_FAILED("10","新增失败"),
    INSERT_OPERATION_EXIST("11","新增数据已存在"),
    ZUUL_FILTER_SUCCESS("12","路由过滤成功"),
    ZUUL_FILTER_FAILED("13","路由过滤失败"),
    ZUUL_FILTER_TOKEN_SUCCESS("14","token值存在"),
    ZUUL_FILTER_TOKEN_FAILED("15","token值不存在"),
    REQUEST_IS_NULL("16","request对象为null");


    OperationStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

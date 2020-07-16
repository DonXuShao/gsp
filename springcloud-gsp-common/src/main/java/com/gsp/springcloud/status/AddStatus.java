package com.gsp.springcloud.status;

/**
 * @Author Don
 * @Description 添加操作的状态码
 * @Date 2020/7/14 10:17
 **/
public enum AddStatus {
    ADD_DATA_SUCCESS("21001", "添加数据成功"),
    ADD_DATA_FAILED("11001", "添加数据失败"),
    ADD_DATA_EXIST("11002", "该数据已存在，请修改后重试！"),


    TEST("11111", "测试一下");

    AddStatus(String code, String msg) {
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

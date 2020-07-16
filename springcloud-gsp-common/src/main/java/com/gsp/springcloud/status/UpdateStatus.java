package com.gsp.springcloud.status;

/**
 * @Author Don
 * @Description 修改的状态码
 * @Date 2020/7/14 10:16
 **/
public enum UpdateStatus {

    UPDATE_DATA_SUCCESS("23001", "修改数据成功"),
    UPDATE_DATA_FAILED("13001", "修改数据失败"),
    UPDATE_DATA_EXIST("13002", "该数据已存在，请修改后重试！"),
    UPDATE_DATA_NULL("13003", "传入数据为空");


    UpdateStatus(String code, String msg) {
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

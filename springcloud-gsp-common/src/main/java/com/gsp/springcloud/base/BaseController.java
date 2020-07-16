package com.gsp.springcloud.base;

import static com.gsp.springcloud.status.AddStatus.ADD_DATA_EXIST;
import static com.gsp.springcloud.status.AddStatus.ADD_DATA_FAILED;
import static com.gsp.springcloud.status.DeleteStatus.*;
import static com.gsp.springcloud.status.FileStatus.UPLOAD_FAILED;
import static com.gsp.springcloud.status.FileStatus.UPLOAD_SUCCESS;
import static com.gsp.springcloud.status.LoginStatus.*;
import static com.gsp.springcloud.status.OperationStatus.FAILED;
import static com.gsp.springcloud.status.OperationStatus.SUCCESS;
import static com.gsp.springcloud.status.SelectStatus.*;
import static com.gsp.springcloud.status.UpdateStatus.*;


/**
 * @Author Don
 * @Date: 2020/7/8 14:48
 * Discription: 统一Controller
 * 也就是说所有的controller都需要继承这个controller，进行统一返回
 **/

public class BaseController {


    /**
     * @return
     * @Author Don
     * @Description 登录成功
     * 使用系统消息
     * @Date 2020/7/8 15:06
     * @Param
     **/
    protected ResultData loginSuccess() {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(LOGIN_SUCCESS.getMsg());
        return resultData;
    }

    /**
     * @return
     * @Author Don
     * @Description 登录成功 自定义消息
     * @Date 2020/7/8 15:07
     * @Param
     **/
    protected ResultData loginSuccess(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @return
     * @Author Don
     * @Description 登录成功
     * 返回数据信息 使用系统消息
     * @Date 2020/7/8 15:07
     * @Param
     **/
    protected ResultData loginSuccess(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(LOGIN_SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @return
     * @Author Don
     * @Description 登录成功返回数据信息
     * 使用自定义消息
     * @Date 2020/7/8 15:08
     * @Param
     **/
    protected ResultData loginSuccess(String msg, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 超时退出
     * @Date 2020/7/14 10:20
     **/
//    protected ResultData loginTimeoutExit() {
//        ResultData resultData = new ResultData();
//        resultData.setCode(LOGIN_TIMEOUT_EXIT.getCode());
//        resultData.setMsg(LOGIN_TIMEOUT_EXIT.getMsg());
//        return resultData;
//    }


    /**
     * @Author Don
     * @Description 超时退出，自定义消息
     * @Date 2020/7/14 10:20
     **/
//    protected ResultData loginTimeoutExit(String msg) {
//        ResultData resultData = new ResultData();
//        resultData.setCode(LOGIN_TIMEOUT_EXIT.getCode());
//        resultData.setMsg(msg);
//        return resultData;
//    }

    /**
     * @return
     * @Author Don
     * @Description 登录失败
     * 使用系统消息
     * @Date 2020/7/8 15:09
     * @Param
     **/
    protected ResultData loginFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_FAILED.getCode());
        resultData.setMsg(LOGIN_FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 登录失败
     * 使用系统消息 详细说明
     * @Date 2020/7/8 15:25
     * @Param
     * @Return
     * @Throws
     **/
    protected ResultData loginFailed(String detail) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_FAILED.getCode());
        resultData.setMsg(LOGIN_FAILED.getMsg());
        resultData.setDetail(detail);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 操作成功，返回系统消息
     * @Date 2020/7/10 17:02
     **/
    protected ResultData operationSuccess(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(SUCCESS.getCode());
        resultData.setMsg(SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 操作成功 返回系统消息
     * @Date 2020/7/9 18:59
     **/
    protected ResultData operationSuccess() {
        ResultData resultData = new ResultData();
        resultData.setCode(SUCCESS.getCode());
        resultData.setMsg(SUCCESS.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 操作失败 返回系统消息
     * @Date 2020/7/9 19:00
     **/
    protected ResultData operationFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(FAILED.getCode());
        resultData.setMsg(FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 操作失败，返回系统消息
     * @Date 2020/7/10 17:03
     **/
    protected ResultData operationFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 添加数据失败，返回自定义消息
     * @Date 2020/7/14 10:21
     **/
    protected ResultData addFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(ADD_DATA_FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 添加的数据已经存在，添加失败，返回系统消息
     * @Date 2020/7/14 10:21
     **/
    protected ResultData addDataEexist() {
        ResultData resultData = new ResultData();
        resultData.setCode(ADD_DATA_EXIST.getCode());
        resultData.setMsg(ADD_DATA_EXIST.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 添加的数据已经存在，添加失败，返回自定义消息
     * @Date 2020/7/14 10:21
     **/
    protected ResultData addDataEexist(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(ADD_DATA_EXIST.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 要修改的数据已存在
     * @Date 2020/7/14 10:17
     **/
    protected ResultData updateDataExist() {
        ResultData resultData = new ResultData();
        resultData.setCode(UPDATE_DATA_EXIST.getCode());
        resultData.setMsg(UPDATE_DATA_EXIST.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 查询数据成功
     * @Date 2020/7/14 10:15
     **/
    protected ResultData selectSuccess(Object obj) {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_SUCCESS.getCode());
        resultData.setMsg(SELECT_DATA_SUCCESS.getMsg());
        resultData.setData(obj);
        return resultData;
    }


    /**
     * @Author Don
     * @Description 查询数据成功，返回自定义消息
     * @Date 2020/7/14 10:15
     **/
    protected ResultData selectSuccess(Object obj, String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(obj);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 查询数据失败
     * @Date 2020/7/14 10:15
     **/
    protected ResultData selectFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_FAILED.getCode());
        resultData.setMsg(SELECT_DATA_FAILED.getMsg());
        return resultData;
    }


    /**
     * @Author Don
     * @Description 查询数据失败，返回自定义消息
     * @Date 2020/7/14 10:15
     **/
    protected ResultData selectFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 查询的数据不存在
     * @Date 2020/7/14 10:15
     **/
    protected ResultData selectDataNotExist() {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_NOT_EXIST.getCode());
        resultData.setMsg(SELECT_DATA_NOT_EXIST.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 查询的数据不存在，返回自定义消息
     * @Date 2020/7/14 10:15
     **/
    protected ResultData selectDataNotExist(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_NOT_EXIST.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 根据ID查询数据成功
     * @Date 2020/7/14 10:15
     **/
    protected ResultData selectByIdSuccess(Object obj) {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_BY_ID_SUCCESS.getCode());
        resultData.setMsg(SELECT_DATA_BY_ID_SUCCESS.getMsg());
        resultData.setData(obj);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 根据ID查询数据成功，返回自定义消息
     * @Date 2020/7/14 10:12
     **/
    protected ResultData selectByIdSuccess(String msg, Object obj) {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_BY_ID_SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(obj);
        return resultData;
    }


    /**
     * @Author Don
     * @Description 根据ID查询数据失败
     * @Date 2020/7/14 10:13
     **/
    protected ResultData selectByIdFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_BY_ID_FAILED.getCode());
        resultData.setMsg(SELECT_DATA_BY_ID_FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 根据ID查询数据失败，返回自定义消息
     * @Date 2020/7/14 10:14
     **/
    protected ResultData selectByIdFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(SELECT_DATA_BY_ID_FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 上传成功
     * @Date 2020/7/14 10:19
     **/
    protected ResultData uploadSuccess() {
        ResultData resultData = new ResultData();
        resultData.setCode(UPLOAD_SUCCESS.getCode());
        resultData.setMsg(UPLOAD_SUCCESS.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 上传成功，自定义返回值
     * @Date 2020/7/14 10:19
     **/
    protected ResultData uploadSuccess(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(UPLOAD_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }


    /**
     * @Author Don
     * @Description 上传失败
     * @Date 2020/7/14 10:19
     **/
    protected ResultData uploadFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(UPLOAD_FAILED.getCode());
        resultData.setMsg(UPLOAD_FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 上传失败，自定义返回值
     * @Date 2020/7/14 10:19
     **/
    protected ResultData uploadFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(UPLOAD_FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 删除数据成功，返回系统消息
     * @Date 2020/7/14 10:23
     **/
    protected ResultData deleteSuccess() {
        ResultData resultData = new ResultData();
        resultData.setCode(DELETE_DATA_SUCCESS.getCode());
        resultData.setMsg(DELETE_DATA_SUCCESS.getMsg());
        return resultData;
    }


    /**
     * @Author Don
     * @Description 删除数据成功，返回自定义消息
     * @Date 2020/7/14 10:23
     **/
    protected ResultData deleteSuccess(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(DELETE_DATA_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 删除数据失败
     * @Date 2020/7/14 10:23
     **/
    protected ResultData deleteFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(DELETE_DATA_FAILED.getCode());
        resultData.setMsg(DELETE_DATA_FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 删除数据失败，返回自定义数据
     * @Date 2020/7/14 10:23
     **/
    protected ResultData deleteFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(DELETE_DATA_FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }


    /**
     * @Author Don
     * @Description 删除的数据不存在，删除失败
     * @Date 2020/7/14 10:23
     **/
    protected ResultData deleteDataNotExist() {
        ResultData resultData = new ResultData();
        resultData.setCode(DELETE_DATA_NOT_EXIST.getCode());
        resultData.setMsg(DELETE_DATA_NOT_EXIST.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 出现未知错误，请稍后再试！
     * @Date 2020/7/14 10:22
     **/
    protected ResultData deleteDataError() {
        ResultData resultData = new ResultData();
        resultData.setCode(DELETE_DATA_ERROR.getCode());
        resultData.setMsg(DELETE_DATA_ERROR.getMsg());
        return resultData;
    }

    /**
     * @Author Don
     * @Description 修改数据成功
     * @Date 2020/7/14 10:25
     **/
    protected ResultData updateSuccess() {
        ResultData resultData = new ResultData();
        resultData.setCode(UPDATE_DATA_SUCCESS.getCode());
        resultData.setMsg(UPDATE_DATA_SUCCESS.getMsg());
        return resultData;
    }


    /**
     * @Author Don
     * @Description 修改数据成功，返回自定义消息
     * @Date 2020/7/14 10:25
     **/
    protected ResultData updateSuccess(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(UPDATE_DATA_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Author Don
     * @Description 修改数据失败
     * @Date 2020/7/14 10:25
     **/
    protected ResultData updateFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(UPDATE_DATA_FAILED.getCode());
        resultData.setMsg(UPDATE_DATA_FAILED.getMsg());
        return resultData;
    }


    /**
     * @Author Don
     * @Description 修改数据失败，返回自定义消息
     * @Date 2020/7/14 10:24
     **/
    protected ResultData updateFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(UPDATE_DATA_FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }


}

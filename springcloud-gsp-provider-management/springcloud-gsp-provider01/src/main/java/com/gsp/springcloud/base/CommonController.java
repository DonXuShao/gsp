package com.gsp.springcloud.base;

import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseController;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.base.ResultData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author Don
 * @Date: 2020/7/8 16:52
 * @Discription:通用Controller
 * @Version 1.0
 **/
public abstract class CommonController<T> extends BaseController {

    public abstract BaseService<T> getBaseService();

    /**
     * @Author Don
     * @Description 在新增前需要执行的操作
     * @Date 2020/7/9 19:01
     **/
    protected void beforeAdd(Map map) {
        // TODO AddMethod Before add new data
    }

    /**
     * @Author Don
     * @Description 在新增方法后执行的操作
     * @Date 2020/7/9 19:03
     **/
    protected void afterAdd(Map map) {
        //TODO After AddMethod
    }


    /**
     * @Author Don
     * @Description 通用新增方法
     * @Date 2020/7/9 19:05
     **/
    public ResultData add(@RequestBody Map map) {
        beforeAdd(map);
        //1.Map转换为实体类
        T instance = getBaseService().newInstance(map);
        //2.通用Serveice
        Integer addResult = getBaseService().add(instance);
        if (addResult > 0) {
            afterAdd(map);
            return super.operationSuccess();
        }
        return super.operationFailed();
    }

    /**
     * @Author Don
     * @Description 通用删除方法
     * @Date 2020/7/9 19:07
     **/
    public ResultData delete(@RequestBody Map map) {
        T instance = getBaseService().newInstance(map);
        Integer deleteResult = getBaseService().delete(instance);
        if (deleteResult > 0) {
            return super.operationSuccess();
        }
        return super.operationFailed();
    }

    /**
     * @Author Don
     * @Description 批量删除
     * @Date 2020/7/9 19:09
     **/
    public ResultData batchDelete(@RequestParam("ids[]") Integer[] ids) {
        Integer deleteResult = getBaseService().deleteByIds(Arrays.asList(ids));
        if (deleteResult > 0) {
            return super.operationSuccess();
        }
        return super.operationFailed();
    }

    /**
     * @Author Don
     * @Description 更新操作
     * @Date 2020/7/10 16:59
     **/
    public ResultData update(@RequestBody Map map) {
        T t = getBaseService().newInstance(map);
        int updateResult = getBaseService().update(t);
        if (updateResult > 0) {
            return operationSuccess();
        }
        return operationFailed();
    }

    /**
     * @Author Don
     * @Description 查询一条数据
     * @Date 2020/7/10 16:59
     **/
    public ResultData getOne(@RequestBody Map map) {
        T t = getBaseService().newInstance(map);
        t = getBaseService().selectOne(t);
        if (null != t) {
            return operationSuccess(t);
        }
        return operationFailed();
    }

    /**
     * @Author Don
     * @Description 条件查询多条结果
     * @Date 2020/7/10 16:59
     **/
    public ResultData getList(@RequestBody Map map) {
        T t = getBaseService().newInstance(map);
        List<T> resultT = getBaseService().selectList(t);
        if (resultT.size() > 0) {
            return operationSuccess(resultT);
        }
        return operationFailed("未找到查询结果");
    }

    /**
     * @Author Don
     * @Description 带条件的分页查询
     * @Date 2020/7/10 16:59
     **/
    public ResultData getListByPage(@RequestBody Map map, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        T t = getBaseService().newInstance(map);
        PageInfo<T> pageInfo = getBaseService().selectListByPage(t, pageNo, pageSize);
        List<T> resultList = pageInfo.getList();
        if (resultList.size() > 0) {
            return operationSuccess(pageInfo);
        }
        return operationFailed("未找到查询结果");
    }

    /**
     * @Author Don
     * @Description 不带条件的分页查询
     * @Date 2020/7/10 16:59
     **/
    public ResultData getListByPage(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        PageInfo<T> pageInfo = getBaseService().selectListByPage(null, pageNo, pageSize);
        List<T> resultList = pageInfo.getList();
        if (resultList.size() > 0) {
            return operationSuccess(pageInfo);
        }
        return operationFailed("未找到查询结果");
    }


    /**
     * @Author Don
     * @Description 从本地当前线程中获取request对象
     * @Date 2020/7/10 17:00
     **/
    public HttpServletRequest getServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes;
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    /**
     * @Author Don
     * @Description 获取当前客户端session对象(如果没有则创建一个新的session)
     * @Date 2020/7/10 17:00
     **/
    public HttpSession getSession() {
        return getServletRequest().getSession();
    }

    /**
     * @Author Don
     * @Description 获取当前客户端session对象(如果没有则直接返回null)
     * @Date 2020/7/10 17:00
     **/
    public HttpSession getExistSession() {
        return getServletRequest().getSession(false);
    }
}

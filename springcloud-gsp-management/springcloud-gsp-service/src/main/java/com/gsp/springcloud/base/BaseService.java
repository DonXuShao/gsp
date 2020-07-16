package com.gsp.springcloud.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.utils.Map2BeanUtils;
import com.gsp.springcloud.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.gsp.springcloud.staticproperties.OrderStatic.ASC;
import static com.gsp.springcloud.staticproperties.OrderStatic.DESC;

/**
 * @Author Don
 * @Date: 2020/7/8 15:30
 * Discription:通用Service
 * 封装相关操作方法
 * @Version 1.0
 **/
public abstract class BaseService<T> {

    //通用缓存 缓存子类的数据类型
    private Class<T> cache = null;


    @Autowired
    private Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    /**
     * @Author Don
     * @Description 获取子类泛型类型
     * @Date 2020/7/9 16:43
     **/
    public Class<T> getTypeArgument() {
        if (null == cache) {
            cache = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return cache;
    }

    /**
     * @Author Don
     * @Description Map转换实体类型
     * @Date 2020/7/9 17:14
     **/
    public T newInstance(Map map) {
        return (T) Map2BeanUtils.map2Bean(map, getTypeArgument());
    }

    /**
     * @Author Don
     * @Description 新增数据
     * @Date 2020/7/9 19:11
     **/
    public Integer add(T t) {
        return mapper.insert(t);
    }

    /**
     * @Author Don
     * @Description 根据主键进行删除
     * @Date 2020/7/9 16:39
     **/
    public Integer delete(T t) {
        return mapper.deleteByPrimaryKey(t);
    }

    /**
     * @Author Don
     * @Description 根据主键进行批量删除
     * @Date 2020/7/9 16:45
     **/
    public Integer deleteByIds(List<Integer> ids) {
        Example example = Example.builder(getTypeArgument()).where(Sqls.custom().andIn("id", ids)).build();
        return mapper.deleteByExample(example);
    }

    /**
     * @Author Don
     * @Description 更新操作
     * @Date 2020/7/9 16:45
     **/
    public Integer update(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * @Author Don
     * @Description 批量修改数据
     * @Date 2020/7/9 17:00
     **/
    public Integer bachUpdate(T t, Integer[] ids) {
        Example example = Example.builder(getTypeArgument()).where(Sqls.custom().andIn("id", Arrays.asList(ids))).build();
        return mapper.updateByExample(t, example);
    }

    /**
     * @Author Don
     * @Description 查询一条数据
     * 形参中的t所传递的数据 传递的为主键或者唯一键
     * @Date 2020/7/9 17:04
     **/
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    /**
     * @Author Don
     * @Description 查询集合 条件查询
     * @Date 2020/7/9 17:09
     **/
    public List<T> selectList(T t) {
        return mapper.select(t);
    }


    /**
     * @Author Don
     * @Description 实现查询通用
     * 不仅可以作用于分页 还可以作用于排序 以及多条件查询
     * orderByFiled:所要排序的字段
     * @Date 2020/7/9 18:49
     **/
    private List<T> selectByFileds(Integer pageNo, Integer pageSize, Sqls where, String orderByFiled, String orderWord, String... fileds) {
        Example.Builder builder = null;
        if (null == fileds || fileds.length == 0) {
            //查询所有数据
            builder = Example.builder(getTypeArgument());
        } else {
            //说明需要进行条件查询
            builder = Example.builder(getTypeArgument()).select(fileds);
        }

        if (null != where) {
            //说明用户有自定义的where语句
            builder = builder.where(where);
        }
        if (null != orderByFiled) {
            //说明接下来需要对某个字段进行排序
            if (DESC.equals(orderWord.toUpperCase())) {
                builder = builder.orderByDesc(orderByFiled);
            } else if (ASC.equals(orderByFiled.toUpperCase())) {
                builder = builder.orderByAsc(orderByFiled);
            } else {
                builder = builder.orderByDesc(orderByFiled);
            }
        }
        Example example = builder.build();
        //实现分页
        if (pageNo != null & pageSize != null) {
            PageHelper.startPage(pageNo, pageSize);
        }
        return getMapper().selectByExample(example);
    }

    /**
     * @Author Don
     * @Description 通过条件查询一个列表
     * @Date 2020/7/9 17:08
     **/
    public T selectOneByFiled(Sqls where, String orderByFiled, String... Fileds) {
        return (T) selectByFileds(null, null, where, orderByFiled, null, Fileds).get(0);

    }

    /**
     * @Author Don
     * @Description 通过条件查询一个列表
     * @Date 2020/7/9 18:55
     **/
    public List<T> selectListByFiled(Sqls where, String orderByField, String... fields) {
        return selectByFileds(null, null, where, orderByField, null, fields);
    }

    /**
     * @Author Don
     * @Description 实现条件查询的分页
     * @Date 2020/7/9 18:54
     **/
    public PageInfo<T> selectListByPageAndFiled(Integer pageNo, Integer pageSize, Sqls where, String orderFiled, String... fileds) {
        return new PageInfo<T>(selectByFileds(pageNo, pageSize, where, orderFiled, null, fileds));
    }

    /**
     * @Author Don
     * @Description 查询集合 分页查询
     * @Date 2020/7/9 17:13
     **/
    public PageInfo<T> selectListByPage(T t, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<T> select = mapper.select(t);
        PageInfo<T> pageInfo = new PageInfo<T>(select);
        return pageInfo;
    }

    /**
     * @Author Don
     * @Description 获取spring容器/获取spring的上下文
     * @Date 2020/7/9 18:50
     **/
    public ApplicationContext getApplicationContext() {
        return SpringContextUtils.getApplicationContext();
    }
}

package com.gsp.springcloud.utils;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Don
 * @Date: 2020/7/9 16:08
 * @Discription: 高性能转化工具类
 * @Version 1.0
 **/
public class Map2BeanUtils {

    private Map2BeanUtils() {
    }

    //高性能JAVA实例化工具集
    private static final Objenesis OBJENESIS = new ObjenesisStd(true);

    //使用StringBuilder提升效率
    private static final StringBuilder STRING_BUILDER = new StringBuilder();

    // 高性能反射工具类中，高性能反射字节集
    // ConcurrentHashMap:在线程中运转，这个Map会在当前线程中出现
    // 而且线程和线程具有隔离性，这里的Map就不会被其他的线程所干扰
    private static final ConcurrentHashMap<Class, MethodAccess> CONCURRENT_HASH_MAP = new ConcurrentHashMap<Class, MethodAccess>(16);

    /**
     * @Author Don
     * @Description Map转换JAVA Bean
     * @Date 2020/7/9 16:16
     * @Param
     * @Return
     * @Throws
     **/
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz) {
        //1.获取实例信息
        T instance = OBJENESIS.newInstance(clazz);
        //2.从Map中获取Key（instance）或者MethodAccess对象
        MethodAccess methodAccess = CONCURRENT_HASH_MAP.get(clazz);
        //3.判断
        if (null == methodAccess) {
            //通过类型获取methodAccess对象
            methodAccess = MethodAccess.get(clazz);
            //4.存入CONCURRENT_HASH_MAP中
            CONCURRENT_HASH_MAP.put(clazz, methodAccess);
        }
        //5.循环map对象
        for (Map.Entry entry : map.entrySet()) {
            String setMethodName = getSetMethodName((String) entry.getKey());
            int index = methodAccess.getIndex(setMethodName, entry.getValue().getClass());
            methodAccess.invoke(instance, index, entry.getValue());
        }
        return instance;
    }


    /**
     * @Author Don
     * @Description 通过字段拼接方法名
     * @Date 2020/7/9 16:23
     * @Param
     * @Return
     * @Throws
     **/
    private static String getSetMethodName(String fieldName) {
        STRING_BUILDER.setLength(0);
        return STRING_BUILDER.append("set").append(first2UpperCase(fieldName)).toString();
    }


    /**
     * @Author Don
     * @Description 设置首字母大写
     * @Date 2020/7/9 16:22
     * @Param
     * @Return
     * @Throws
     **/
    private static String first2UpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


}

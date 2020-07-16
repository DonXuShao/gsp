package com.gsp.springcloud.redis;

import com.gsp.springcloud.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.gsp.springcloud.staticproperties.RedisProperties.*;

/**
 * @Author Don
 * @Date: 2020/7/10 16:26
 * @Discription:Redis操作Service
 * @Version 1.0
 **/
public class RedisService<T> {

    private RedisSerializer keySerializer = null;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * @Author Don
     * @Description 初始化Redis的key序列化器
     * @Date 2020/7/10 16:28
     **/
    @PostConstruct
    public void initRedisSerializer() {
        if (this.keySerializer == null) {
            this.keySerializer = new JdkSerializationRedisSerializer(this.getClass().getClassLoader());
        }
    }

    /**
     * @Author Don
     * @Description 向redis中存入数据
     * nxxx:
     * 是固定值，有两个值
     * "nx":
     * 如果redis中的key不存在，则就可以存储，如果redis中已经有这个key了，则不存数据
     * "xx":
     * 如果redis中的key存在，则直接覆盖，如果key不存在则不存
     * <p>
     * eg:
     * 研发组有两个人，一个是张三，一个是李四
     * 张三负责商品管理的代码编写
     * 李四负责订单管理
     * 张三和李四因为数据量过大都会使用到redis
     * 张三---->redis.set("goods", 商品信息);
     * 李四---->redis.set("goods", 订单信息);
     * 张三---->redis.get("goods")--->订单信息---->转换异常
     * <p>
     * 张三负责商品管理的代码编写
     * 张三---->redis.set("goods", 商品信息);
     * 张三---->redis.set("goods", 商品信息);
     * <p>
     * expx:
     * 值也是固定的
     * 只有两个值:
     * ex:
     * 失效时间的单位是秒
     * px:
     * 失效时间的单位是毫秒
     * <p>
     * seconds:
     * 失效时间
     * @Date 2020/7/10 16:34
     **/
    public String set(String key, T value, String nxxx, String expx, Integer seconds) {
        if (null != seconds && 0 < seconds &&
                (EX.equals(expx) || PX.equals(expx)) &&
                (XX.equals(nxxx) || NX.equals(nxxx))
        ) {
            //在存入数据的时候必须要上失效时间
            return jedisCluster.set(key, JSONUtils.toJsonString(value), nxxx, expx, seconds);
        } else {
            //说明不需要设置失效时间
            //但是仍然需要进一步去判断用户所传递的到底是nx还是xx
            if (NX.equals(nxxx)) {
                return String.valueOf(jedisCluster.setnx(key, JSONUtils.toJsonString(value)));
            } else if (XX.equals(nxxx)) {
                return jedisCluster.set(key, JSONUtils.toJsonString(value));
            }
        }
        return NO;
    }

    /**
     * @Author Don
     * @Description 从Redis中获取单个数据
     * @Date 2020/7/10 16:36
     **/
    public T getOne(String key) {
        return (T) JSONUtils.toObject(jedisCluster.get(key), Object.class);
    }

    /**
     * @Author Don
     * @Description 从Redis中查询数据 数据格式为字符串
     * @Date 2020/7/10 16:37
     **/
    public String getString(String key) {
        return jedisCluster.get(key);
    }

    /**
     * @Author Don
     * @Description 从Redis中查询数据（集合数据）
     * @Date 2020/7/10 16:39
     **/
    public List<T> getList(String key) {
        return (List<T>) JSONUtils.toList(jedisCluster.get(key), Object.class);
    }

    /**
     * @Author Don
     * @Description 删除某一个
     * 思路:
     * 目前来说架构遇到的问题:
     * 封装redis的时候发现无法实现通用，因为JedisCluster只能接收String类型key值
     * 并不符合架构标准，最终可以把Object对象转换为字节数组来进行处理这个问题
     * @Date 2020/7/10 16:45
     **/
    public Long delOne(Object key) {
        return jedisCluster.del(rawKey(key));
    }

    /**
     * @Author Don
     * @Description 删除多个键
     * 这种循环的形式看似没有毛病，但是有问题
     * 假设有10个key需要删除
     * 其中九个都删了，但是只有一个没有删，如果这一个不是在最后
     * 那么结果返回一定大于0
     * 不能使用循环操作
     * @Date 2020/7/10 16:46
     **/
    public Long delMany(Collection<T> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return 0L;
        } else {
            byte[][] bytes = this.rawkeys(keys);
            return jedisCluster.del(bytes);
        }
    }

    /**
     * @Author Don
     * @Description 把Object对象转换为字节数组
     * @Date 2020/7/10 16:42
     **/
    private byte[] rawKey(Object key) {
        // 1.判断
        /**
         * 断言就是来判断用的:
         *  如果key有值则会去执行下面的代码
         *  如果key没有，则直接return了
         */
        /*if(key == null) {
            System.out.println("key不存在");
            return null;
        } else {
            if(keySerializer == null && key instanceof byte[]) {
                // 直接转换
                return (byte[]) key;
            } else {
                // 说明条件不满足，需要进行转换
                return keySerializer.serialize(key);
            }
        }*/
        Assert.notNull(key, "non null key required");
        /*if(keySerializer == null && key instanceof byte[]) {
            // 直接转换
            return (byte[]) key;
        } else {
            // 说明条件不满足，需要进行转换
            return keySerializer.serialize(key);
        }*/
        return this.keySerializer == null && key instanceof byte[] ?
                (byte[]) key : this.keySerializer.serialize(key);
    }

    private byte[][] rawkeys(Collection<T> keys) {
        byte[][] rks = new byte[keys.size()][];
        int i = 0;
        Object key = null;
        for (Iterator i9 = keys.iterator(); i9.hasNext(); rks[i++] = this.rawKey(key)) {
            key = i9.next();
        }
        return rks;
    }
}

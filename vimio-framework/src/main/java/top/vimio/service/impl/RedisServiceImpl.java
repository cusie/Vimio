package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.vimio.entity.Moment;
import top.vimio.utils.common.PageResult;
import top.vimio.model.blogvo.vo.BlogInfoVo;
import top.vimio.service.RedisService;
import top.vimio.utils.JacksonUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 提供Redis数据访问的服务类实现
 * @Author: Vimio
 * @Date: 2024/9/8 10:47
 */

 @Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate jsonRedisTemplate;
    /**
     * 根据哈希表名和页码获取博客信息分页结果，如果Redis中存在对应的数据则返回，否则返回null
     *
     * @param hash  哈希表名
     * @param pageNum 页码
     * @return 分页结果，如果未找到则返回null
     */
    @Override
    public PageResult<BlogInfoVo> getBlogInfoPageResultByHash(String hash, Integer pageNum) {
        // 检查 Redis 中是否存在指定哈希表和页码的键
        if (jsonRedisTemplate.opsForHash().hasKey(hash, pageNum)) {
            // 获取 Redis 中对应哈希表和页码的键的值
            Object redisResult = jsonRedisTemplate.opsForHash().get(hash, pageNum);
            // 将获取到的值转换为 PageResult<BlogInfoVo> 类型
            PageResult<BlogInfoVo> pageResult = JacksonUtils.convertValue(redisResult, PageResult.class);
            // 返回转换后的分页结果
            return pageResult;
        } else {
            // 如果 Redis 中不存在对应的数据，则返回 null
            return null;
        }
    }

    @Override
    public PageResult<Moment> getMomentInfoPageResultBySet(String hash, Integer pageNum) {
        // 检查 Redis 中是否存在指定哈希表和页码的键
        if (jsonRedisTemplate.opsForHash().hasKey(hash, pageNum)) {
            // 获取 Redis 中对应哈希表和页码的键的值
            Object redisResult = jsonRedisTemplate.opsForHash().get(hash, pageNum);
            // 将获取到的值转换为 PageResult<BlogInfoVo> 类型
            PageResult<Moment> pageResult = JacksonUtils.convertValue(redisResult, PageResult.class);
            // 返回转换后的分页结果
            return pageResult;
        } else {
            // 如果 Redis 中不存在对应的数据，则返回 null
            return null;
        }
    }

    /**
     * 将键值对保存到Redis哈希表中
     *
     * @param hash 哈希表名
     * @param key  键
     * @param value 值
     */
    @Override
    public void saveKVToHash(String hash, Object key, Object value) {
        jsonRedisTemplate.opsForHash().put(hash, key, value);
    }

    /**
     * 将一个Map对象保存到Redis哈希表中
     *
     * @param hash 哈希表名
     * @param map  要保存的Map对象
     */
    @Override
    public void saveMapToHash(String hash, Map map) {
        jsonRedisTemplate.opsForHash().putAll(hash, map);
    }

    /**
     * 从Redis哈希表中获取所有键值对，并以Map形式返回
     *
     * @param hash 哈希表名
     * @return 包含所有键值对的Map对象
     */
    @Override
    public Map getMapByHash(String hash) {
        return jsonRedisTemplate.opsForHash().entries(hash);
    }

    /**
     * 根据哈希表名和键获取对应的值
     *
     * @param hash 哈希表名
     * @param key  键
     * @return 键对应的值，如果未找到则返回null
     */
    @Override
    public Object getValueByHashKey(String hash, Object key) {
        return jsonRedisTemplate.opsForHash().get(hash, key);
    }
    /**
     * 根据哈希表名和键增加对应的值，如果增量小于0则抛出异常
     *
     * @param hash 哈希表名
     * @param key  键
     * @param increment 增量
     * @throws RuntimeException 如果增量小于0
     */
    @Override
    public void incrementByHashKey(String hash, Object key, int increment) {
        if (increment < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        jsonRedisTemplate.opsForHash().increment(hash, key, increment);
    }

    /**
     * 根据哈希表名和键删除对应的值
     *
     * @param hash 哈希表名
     * @param key  键
     */
    @Override
    public void deleteByHashKey(String hash, Object key) {
        jsonRedisTemplate.opsForHash().delete(hash, key);
    }

    /**
     * 根据键获取Redis中存储的列表，如果键不存在则返回null
     *
     * @param key 键
     * @return 列表对象，如果键不存在则返回null
     */
    @Override
    public <T> List<T> getListByValue(String key) {
        List<T> redisResult = (List<T>) jsonRedisTemplate.opsForValue().get(key);
        return redisResult;
    }

    /**
     * 将列表保存到Redis中，使用指定的键
     *
     * @param key 键
     * @param list 要保存的列表
     */
    @Override
    public <T> void saveListToValue(String key, List<T> list) {
        jsonRedisTemplate.opsForValue().set(key, list);
    }

    /**
     * 根据键获取Redis中存储的Map，如果键不存在则返回null
     *
     * @param key 键
     * @return Map对象，如果键不存在则返回null
     */
    @Override
    public <T> Map<String, T> getMapByValue(String key) {
        // 尝试从Redis中获取指定键的值，并将其转换为Map<String, T>类型
        Map<String, T> redisResult = (Map<String, T>) jsonRedisTemplate.opsForValue().get(key);
        // 返回获取到的Map对象，如果键不存在，则返回null
        return redisResult;
    }

    /**
     * 将Map保存到Redis中，使用指定的键
     *
     * @param key 键
     * @param map 要保存的Map
     */
    @Override
    public <T> void saveMapToValue(String key, Map<String, T> map) {
        // 将给定的Map对象存储到Redis中，使用指定的key
        jsonRedisTemplate.opsForValue().set(key, map);
    }

    /**
     * 根据键和类型获取Redis中存储的对象，如果键不存在则返回null
     *
     * @param key 键
     * @param t 类型
     * @return 对象，如果键不存在则返回null
     */
    @Override
    public <T> T getObjectByValue(String key, Class t) {
        Object redisResult = jsonRedisTemplate.opsForValue().get(key);
        T object = (T) JacksonUtils.convertValue(redisResult, t);
        return object;
    }

    /**
     * 根据键增加Redis中存储的值，如果增量小于0则抛出异常
     *
     * @param key 键
     * @param increment 增量
     * @throws RuntimeException 如果增量小于0
     */
    @Override
    public void incrementByKey(String key, int increment) {
        if (increment < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        jsonRedisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 将对象保存到Redis中，使用指定的键
     *
     * @param key 键
     * @param object 要保存的对象
     */
    @Override
    public void saveObjectToValue(String key, Object object) {
        jsonRedisTemplate.opsForValue().set(key, object);
    }

    /**
     * 将值保存到Redis集合中，如果值已存在则不保存
     *
     * @param key 键
     * @param value 值
     */
    @Override
    public void saveValueToSet(String key, Object value) {
        jsonRedisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取Redis集合中元素的数量
     *
     * @param key 键
     * @return 元素数量
     */
    @Override
    public int countBySet(String key) {
        return jsonRedisTemplate.opsForSet().size(key).intValue();
    }

    /**
     * 从Redis集合中删除指定的值
     *
     * @param key 键
     * @param value 值
     */
    @Override
    public void deleteValueBySet(String key, Object value) {
        jsonRedisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 判断Redis集合中是否存在指定的值
     *
     * @param key 键
     * @param value 值
     * @return 是否存在
     */
    @Override
    public boolean hasValueInSet(String key, Object value) {
        return jsonRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 根据键删除Redis中的缓存数据
     *
     * @param key 键
     */
    @Override
    public void deleteCacheByKey(String key) {
        jsonRedisTemplate.delete(key);
    }
    /**
     * 检查给定的键是否存在于Redis中
     *
     * @param key 要检查的键
     * @return 如果键存在，则返回true；否则返回false
     */
    @Override
    public boolean hasKey(String key) {
        return jsonRedisTemplate.hasKey(key);
    }
    /**
     * 设置键的过期时间
     *
     * @param key 键
     * @param time 过期时间（秒）
     */
    @Override
    public void expire(String key, long time) {
        jsonRedisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
}

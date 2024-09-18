package top.vimio.service;

import top.vimio.entity.Moment;
import top.vimio.utils.common.PageResult;
import top.vimio.model.blogvo.vo.BlogInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/8 10:42
 */
public interface RedisService {
    PageResult<BlogInfoVo> getBlogInfoPageResultByHash(String hash, Integer pageNum);

    PageResult<Moment> getMomentInfoPageResultBySet(String hash, Integer pageNum);

    void saveKVToHash(String hash, Object key, Object value);

    void saveMapToHash(String hash, Map map);

    Map getMapByHash(String hash);

    Object getValueByHashKey(String hash, Object key);

    void incrementByHashKey(String hash, Object key, int increment);

    void deleteByHashKey(String hash, Object key);

    <T> List<T> getListByValue(String key);

    <T> void saveListToValue(String key, List<T> list);

    <T> Map<String, T> getMapByValue(String key);

    <T> void saveMapToValue(String key, Map<String, T> map);

    <T> T getObjectByValue(String key, Class t);

    void incrementByKey(String key, int increment);

    void saveObjectToValue(String key, Object object);

    void saveValueToSet(String key, Object value);

    int countBySet(String key);

    void deleteValueBySet(String key, Object value);

    boolean hasValueInSet(String key, Object value);

    void deleteCacheByKey(String key);

    boolean hasKey(String key);

    void expire(String key, long time);
}

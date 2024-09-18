package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.constant.RedisKeyConstants;
import top.vimio.entity.About;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.AboutMapper;
import top.vimio.service.AboutService;
import top.vimio.service.RedisService;
import top.vimio.utils.markdown.MarkdownUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 关于我的信息简介业务层实现
 * @Author: Vimio
 * @Date: 2024/8/27 21:09
 */
@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    AboutMapper aboutMapper;
    @Autowired
    RedisService redisService;
    @Override
    public Map<String, String> getAboutInfo() {
        String redisKey = RedisKeyConstants.ABOUT_INFO_MAP;
        Map<String, String> aboutInfoMapFromRedis = redisService.getMapByValue(redisKey);
        if (aboutInfoMapFromRedis != null) {
            return aboutInfoMapFromRedis;
        }
        /*------------------*/
        List<About> abouts = aboutMapper.getList();
        Map<String, String> aboutInfoMap = new HashMap<>();
        for (About about : abouts) {
            if ("content".equals(about.getNameEn())) {
                about.setValue(MarkdownUtils.markdownToHtmlExtensions(about.getValue()));
                //如果对应的英文名标签等于content，将其内容换为HTML格式（从Markdown格式转换）
            }
            aboutInfoMap.put(about.getNameEn(), about.getValue());
            //转换后将值添加到aboutInfoMap中
        }
        redisService.saveMapToValue(redisKey, aboutInfoMap);
        return aboutInfoMap;
        }

        //
    @Override
    public boolean getAboutCommentEnabled() {
        String commentEnabledString = aboutMapper.getAboutCommentEnabled();
        return Boolean.parseBoolean(commentEnabledString);
    }

    @Override
    public Map<String, String> getAboutSetting() {
        List<About> abouts = aboutMapper.getList();
        Map<String, String> map = new HashMap<>(16);
        for (About about : abouts) {
            map.put(about.getNameEn(), about.getValue());
        }
        return map;
    }

    @Override
    public void updateAbout(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            updateOneAbout(key, map.get(key));
        }
        deleteAboutRedisCache();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateOneAbout(String nameEn, String value) {
        if (aboutMapper.updateAbout(nameEn, value) != 1) {
            throw new PersistenceException("修改失败");
        }
    }

    /**
     * 删除关于我页面缓存
     */
    private void deleteAboutRedisCache() {
        redisService.deleteCacheByKey(RedisKeyConstants.ABOUT_INFO_MAP);
    }
}

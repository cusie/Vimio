package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.constant.RedisKeyConstants;
import top.vimio.constant.SiteSettingConstants;
import top.vimio.entity.SiteSetting;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.SiteSettingMapper;
import top.vimio.model.blogvo.siteinfo.Badge;
import top.vimio.model.blogvo.siteinfo.Copyright;
import top.vimio.model.blogvo.siteinfo.Favorite;
import top.vimio.model.blogvo.siteinfo.Introduction;
import top.vimio.model.blogvo.vo.FriendInfoVo;
import top.vimio.service.RedisService;
import top.vimio.service.SiteSettingService;
import top.vimio.utils.JacksonUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 站点设置业务层实现
 * @Author: Vimio
 * @Date: 2024/8/28 14:14
 */
@Service
public class SiteSettingServiceImpl implements SiteSettingService {
    @Autowired
    SiteSettingMapper siteSettingMapper;
    @Autowired
    RedisService redisService;

    // 使用正则表达式编译一个字符串，用于匹配双引号中的内容
    private static final Pattern PATTERN = Pattern.compile("\"(.*?)\"");


    @Override
    public Map<String, Object> getSiteInfo() {
        /**
         * redis缓存
         * */
        String redisKey = RedisKeyConstants.SITE_INFO_MAP;
        Map<String, Object> siteInfoMapFromRedis = redisService.getMapByValue(redisKey);
        if (siteInfoMapFromRedis != null) {
            return siteInfoMapFromRedis;
        }
        /**
         * 数据库查询
         * */

        List<SiteSetting> siteSettings = siteSettingMapper.getList();
        Map<String, Object> siteInfo = new HashMap<>(2);
        List<Badge> badges = new ArrayList<>();
        Introduction introduction = new Introduction();
        List<Favorite> favorites = new ArrayList<>();
        List<String> rollTexts = new ArrayList<>();
        for (SiteSetting s : siteSettings) {
            switch (s.getType()) {
                case 1:
                    if (SiteSettingConstants.COPYRIGHT.equals(s.getNameEn())) {
                        Copyright copyright = JacksonUtils.readValue(s.getValue(), Copyright.class);
                        siteInfo.put(s.getNameEn(), copyright);
                    } else {
                        siteInfo.put(s.getNameEn(), s.getValue());
                    }
                    break;
                case 2:
                    switch (s.getNameEn()) {
                        case SiteSettingConstants.AVATAR:
                            introduction.setAvatar(s.getValue());
                            break;
                        case SiteSettingConstants.NAME:
                            introduction.setName(s.getValue());
                            break;
                        case SiteSettingConstants.GITHUB:
                            introduction.setGithub(s.getValue());
                            break;
                        case SiteSettingConstants.TELEGRAM:
                            introduction.setTelegram(s.getValue());
                            break;
                        case SiteSettingConstants.QQ:
                            introduction.setQq(s.getValue());
                            break;
                        case SiteSettingConstants.BILIBILI:
                            introduction.setBilibili(s.getValue());
                            break;
                        case SiteSettingConstants.NETEASE:
                            introduction.setNetease(s.getValue());
                            break;
                        case SiteSettingConstants.EMAIL:
                            introduction.setEmail(s.getValue());
                            break;
                        case SiteSettingConstants.FAVORITE:
                            Favorite favorite = JacksonUtils.readValue(s.getValue(), Favorite.class);
                            favorites.add(favorite);
                            break;
                        //将双引号中的内容提取出来添加到 rollTexts 列表中。
                        case SiteSettingConstants.ROLL_TEXT:
                            Matcher m = PATTERN.matcher(s.getValue());
                            while (m.find()) {
                                rollTexts.add(m.group(1));
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    Badge badge = JacksonUtils.readValue(s.getValue(), Badge.class);
                    badges.add(badge);
                    break;
                default:
                    break;
            }
        }
        introduction.setFavorites(favorites);
        introduction.setRollText(rollTexts);
        Map<String, Object> map = new HashMap<>(8);
        map.put("introduction", introduction);
        map.put("siteInfo", siteInfo);
        map.put("badges", badges);
        redisService.saveMapToValue(redisKey, map);
        return map;
    }

    @Override
    public Map<String, List<SiteSetting>> getList() {
        List<SiteSetting> siteSettings = siteSettingMapper.getList();
        List<SiteSetting> type1 = new ArrayList<>();
        List<SiteSetting> type2 = new ArrayList<>();
        List<SiteSetting> type3 = new ArrayList<>();
        for (SiteSetting s : siteSettings) {
            switch (s.getType()) {
                case 1:
                    type1.add(s);
                    break;
                case 2:
                    type2.add(s);
                    break;
                case 3:
                    type3.add(s);
                    break;
                default:
                    break;
            }
        }
        Map<String, List<SiteSetting>> map = new HashMap<>(8);
        map.put("type1", type1);
        map.put("type2", type2);
        map.put("type3", type3);
        return map;
    }

    @Override
    public String getWebTitleSuffix() {
        return siteSettingMapper.getWebTitleSuffix();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSiteSetting(List<LinkedHashMap> siteSettings, List<Integer> deleteIds) {
        for (Integer id : deleteIds) {
            //删除
            deleteOneSiteSettingById(id);
        }
        for (LinkedHashMap s : siteSettings) {
            SiteSetting siteSetting = JacksonUtils.convertValue(s, SiteSetting.class);
            if (siteSetting.getId() != null) {
                //修改
                updateOneSiteSetting(siteSetting);
            } else {
                //添加
                saveOneSiteSetting(siteSetting);
            }
        }
        deleteSiteInfoRedisCache();
    }

    public void saveOneSiteSetting(SiteSetting siteSetting) {
        if (siteSettingMapper.saveSiteSetting(siteSetting) != 1) {
            throw new PersistenceException("配置添加失败");
        }
    }

    public void updateOneSiteSetting(SiteSetting siteSetting) {
        if (siteSettingMapper.updateSiteSetting(siteSetting) != 1) {
            throw new PersistenceException("配置修改失败");
        }
    }

    public void deleteOneSiteSettingById(Integer id) {
        if (siteSettingMapper.deleteSiteSettingById(id) != 1) {
            throw new PersistenceException("配置删除失败");
        }
    }

    /**
     * 删除站点信息缓存
     */
    private void deleteSiteInfoRedisCache() {
        redisService.deleteCacheByKey(RedisKeyConstants.SITE_INFO_MAP);
    }
}

package top.vimio.service;

import top.vimio.entity.SiteSetting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/8/28 14:13
 */
public interface SiteSettingService {

    /**
     *
     * @Description: 前台显示业务
     * */
    Map<String, Object> getSiteInfo();

    /**
     *
     * @Description: 公共业务业务
     * */

    /**
     *
     * @Description: 后台管理业务
     * */
    Map<String, List<SiteSetting>> getList();

    String getWebTitleSuffix();

    void updateSiteSetting(List<LinkedHashMap> siteSettings, List<Integer> deleteIds);





}

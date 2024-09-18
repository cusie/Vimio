package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.vimio.entity.SiteSetting;

import java.util.List;
/**
 * @Description: 站点设置持久层接口
 * @Author: Vimio
 * @Date: 2024/8/28 14:08
 */
@Mapper
@Repository
public interface SiteSettingMapper {
    List<SiteSetting> getList();

    List<SiteSetting> getFriendInfo();

    int updateFriendInfoContent(String content);

    int updateFriendInfoCommentEnabled(Boolean commentEnabled);


    String getWebTitleSuffix();

    int deleteSiteSettingById(Integer id);

    int saveSiteSetting(SiteSetting siteSetting);

    int updateSiteSetting(SiteSetting siteSetting);

}

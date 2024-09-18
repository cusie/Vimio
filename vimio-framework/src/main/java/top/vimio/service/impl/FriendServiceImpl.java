package top.vimio.service.impl;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.constant.RedisKeyConstants;
import top.vimio.entity.Friend;
import top.vimio.entity.SiteSetting;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.FriendMapper;
import top.vimio.mapper.SiteSettingMapper;
import top.vimio.model.blogdto.FriendDto;
import top.vimio.model.blogvo.vo.FriendInfoVo;
import top.vimio.model.blogvo.vo.FriendVo;
import top.vimio.service.FriendService;
import top.vimio.service.RedisService;
import top.vimio.utils.markdown.MarkdownUtils;

import java.util.Date;
import java.util.List;
/**
 * @Description: 友链业务层实现
 * @Author: Vimio
 * @Date: 2024/8/30 15:40
 */
@Mapper
@Repository
public class FriendServiceImpl implements FriendService {
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    SiteSettingMapper siteSettingMapper;
    @Autowired
    RedisService redisService;


    @Override
    public List<Friend> getFriendList() {
        return friendMapper.getFriendList();
    }



    @Override
    public List<FriendVo> getFriendVOList() {
        return friendMapper.getFriendVOList();
    }


    @Override
    public FriendInfoVo getFriendInfo(boolean cache, boolean md) {
        /**
         * redis缓存
         * */
        String redisKey = RedisKeyConstants.FRIEND_INFO_MAP;
        if (cache) {
            FriendInfoVo friendInfoFromRedis = redisService.getObjectByValue(redisKey, FriendInfoVo.class);
            if (friendInfoFromRedis != null) {
                return friendInfoFromRedis;
            }
        }
        /**
         * 数据库查询
         * */
        List<SiteSetting> siteSettings = siteSettingMapper.getFriendInfo();
        //通过List存放从SiteSettingMapper获取type=4的数据库的数据
        FriendInfoVo friendInfoVo  = new FriendInfoVo();
        //创建一个新的FriendInfoVo实例
        for (SiteSetting siteSetting : siteSettings) {
            if ("friendContent".equals(siteSetting.getNameEn())) {
                //匹配到友链介绍信息
                if (md) {
                    friendInfoVo.setContent
                            (MarkdownUtils.markdownToHtmlExtensions(siteSetting.getValue()));
                    //如果友链介绍信息是markdown的书写格式，则转换为HTML的形式输出
                } else {
                    friendInfoVo.setContent(siteSetting.getValue());
                }
            } else if ("friendCommentEnabled".equals(siteSetting.getNameEn())) {
                //匹配字符串，作用于评论开关
                if ("1".equals(siteSetting.getValue())) {
                    friendInfoVo.setCommentEnabled(true);
                } else {
                    friendInfoVo.setCommentEnabled(false);
                }
            }
            if (cache && md) {
                redisService.saveObjectToValue(redisKey, friendInfoVo);
            }
        }
        return friendInfoVo;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFriendInfoContent(String content) {
        if (siteSettingMapper.updateFriendInfoContent(content) != 1) {
            throw new PersistenceException("修改失败");
        }
        deleteFriendInfoRedisCache();
    }


    @Override
    public void updateFriendInfoCommentEnabled(Boolean commentEnabled) {
        if (siteSettingMapper.updateFriendInfoCommentEnabled(commentEnabled) != 1) {
            throw new PersistenceException("修改失败");
        }
        deleteFriendInfoRedisCache();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFriendPublishedById(Long friendId, Boolean published) {
        if (friendMapper.updateFriendPublishedById(friendId, published) != 1) {
            throw new PersistenceException("操作失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFriend(Friend friend) {
        friend.setViews(0);
        friend.setCreateTime(new Date());
        if (friendMapper.saveFriend(friend) != 1) {
            throw new PersistenceException("添加失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFriend(FriendDto friend) {
        if (friendMapper.updateFriend(friend) != 1) {
            throw new PersistenceException("修改失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFriend(Long id) {
        if (friendMapper.deleteFriend(id) != 1) {
            throw new PersistenceException("删除失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateViewsByNickname(String nickname) {
        if (friendMapper.updateViewsByNickname(nickname) != 1) {
            throw new PersistenceException("操作失败");
        }
    }

    /**
     * 删除友链页面缓存
     */
    private void deleteFriendInfoRedisCache() {
        redisService.deleteCacheByKey(RedisKeyConstants.FRIEND_INFO_MAP);
    }

}
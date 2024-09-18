package top.vimio.service;

import top.vimio.entity.Friend;
import top.vimio.model.blogdto.FriendDto;
import top.vimio.model.blogvo.vo.FriendInfoVo;
import top.vimio.model.blogvo.vo.FriendVo;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/8/30 15:36
 */
public interface FriendService {

    /**
     *
     * @Description: 前台显示业务
     * */

    /**
     *
     * @Description: 公共业务业务
     * */

    List<FriendVo> getFriendVOList();

    FriendInfoVo getFriendInfo(boolean cache, boolean md);
    void updateViewsByNickname(String nickname);

    /**
     *
     * @Description: 后台管理业务
     * */
    List<Friend> getFriendList();
    void updateFriendInfoCommentEnabled(Boolean commentEnabled);
    void updateFriendPublishedById(Long friendId, Boolean published);
    void updateFriendInfoContent(String content);

    void saveFriend(Friend friend);

    void updateFriend(FriendDto friend);

    void deleteFriend(Long id);




}

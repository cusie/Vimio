package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Friend;
import top.vimio.model.blogdto.FriendDto;
import top.vimio.model.blogvo.vo.FriendVo;

import java.util.List;

/**
 * @Description: 友链持久层
 * @Author: Vimio
 * @Date: 2024/9/2 10:17
 */
@Mapper
@Repository
public interface FriendMapper {

    List<Friend> getFriendList();

    List<FriendVo> getFriendVOList();

    int updateFriendPublishedById(@Param("id")Long id, @Param("published")Boolean published);

    int saveFriend(Friend friend);

    int updateFriend(FriendDto friend);

    int deleteFriend(Long id);

    int updateViewsByNickname(String nickname);
}

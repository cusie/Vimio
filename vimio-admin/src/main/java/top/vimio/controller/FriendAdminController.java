package top.vimio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.OperationLogger;
import top.vimio.entity.Friend;
import top.vimio.utils.common.ResponseResult;
import top.vimio.model.blogdto.FriendDto;
import top.vimio.service.FriendService;

import java.util.Map;

/**
 * @Description: 友链页面后台管理
 * @Author: Vimio
 * @Date: 2024/9/11 9:21
 */

@RestController
public class FriendAdminController {
    @Autowired
    FriendService friendService;

    /**
     * 分页获取友链列表
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/friends")
    public ResponseResult friends(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        String orderBy = "create_time asc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<Friend> pageInfo = new PageInfo<>(friendService.getFriendList());
        return ResponseResult.ok("请求成功", pageInfo);
    }

    /**
     * 获取友链页面信息
     *
     * @return
     */
    @GetMapping("/friendInfo")
    public ResponseResult friendInfo() {
        return ResponseResult.ok("请求成功", friendService.getFriendInfo(false, false));
    }

    /**
     * 修改友链页面评论开放状态
     *
     * @param commentEnabled 是否开放评论
     * @return
     */
    @OperationLogger("修改友链页面评论开放状态")
    @PutMapping("/friendInfo/commentEnabled")
    public ResponseResult updateFriendInfoCommentEnabled(@RequestParam Boolean commentEnabled) {
        friendService.updateFriendInfoCommentEnabled(commentEnabled);
        return ResponseResult.ok("修改成功");
    }

    /**
     * 更新友链公开状态
     *
     * @param id        友链id
     * @param published 是否公开
     * @return
     */
    @OperationLogger("更新友链公开状态")
    @PutMapping("/friend/published")
    public ResponseResult updatePublished(@RequestParam Long id, @RequestParam Boolean published) {
        friendService.updateFriendPublishedById(id, published);
        return ResponseResult.ok("操作成功");
    }

    /**
     * 添加友链
     *
     * @param friend 友链DTO
     * @return
     */
    @OperationLogger("添加友链")
    @PostMapping("/friend")
    public ResponseResult saveFriend(@RequestBody Friend friend) {
        friendService.saveFriend(friend);
        return ResponseResult.ok("添加成功");
    }

    /**
     * 更新友链
     *
     * @param friend 友链DTO
     * @return
     */
    @OperationLogger("更新友链")
    @PutMapping("/friend")
    public ResponseResult updateFriend(@RequestBody FriendDto friend) {
        friendService.updateFriend(friend);
        return ResponseResult.ok("修改成功");
    }

    /**
     * 按id删除友链
     *
     * @param id
     * @return
     */
    @OperationLogger("删除友链")
    @DeleteMapping("/friend")
    public ResponseResult deleteFriend(@RequestParam Long id) {
        friendService.deleteFriend(id);
        return ResponseResult.ok("删除成功");
    }



    /**
     * 修改友链页面content
     *
     * @param map 包含content的JSON对象
     * @return
     */
    @OperationLogger("修改友链页面信息")
    @PutMapping("/friendInfo/content")
    public ResponseResult updateFriendInfoContent(@RequestBody Map map) {
        friendService.updateFriendInfoContent((String) map.get("content"));
        return ResponseResult.ok("修改成功");
    }
}

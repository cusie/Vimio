package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.annotation.VisitLogger;
import top.vimio.enums.VisitBehavior;
import top.vimio.utils.common.ResponseResult;
import top.vimio.model.blogvo.vo.FriendVo;
import top.vimio.service.FriendService;
import top.vimio.service.SiteSettingService;
import top.vimio.model.blogvo.vo.FriendInfoVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 友链
 * @Author: Vimio
 * @Date: 2024/8/28 14:26
 */
@RestController
public class FriendController {
    @Autowired
    FriendService friendService;
    @Autowired
    SiteSettingService siteSettingService;


/*    @GetMapping("/friends")
    public ResponseResult friends() {
        FriendInfoVo friendInfo = friendService.getFriendInfo(true, true);
        Map<String, Object> map = new HashMap<>(4);
        map.put("friendInfo", friendInfo);
        return ResponseResult.ok("获取成功", map);
     }*/
    @VisitLogger(VisitBehavior.FRIEND)
    @GetMapping("/friends")
    public ResponseResult friends() {
        List<FriendVo> friendList = friendService.getFriendVOList();
        FriendInfoVo friendInfo = friendService.getFriendInfo(true, true);
        Map<String, Object> map = new HashMap<>(4);
        map.put("friendList", friendList);
        map.put("friendInfo", friendInfo);
        return ResponseResult.ok("获取成功", map);
    }

    /**
     * 按昵称增加友链浏览次数
     *
     * @param nickname 友链昵称
     * @return
     */
    @VisitLogger(VisitBehavior.CLICK_FRIEND)
    @PostMapping("/friend")
    public ResponseResult addViews(@RequestParam String nickname) {
        friendService.updateViewsByNickname(nickname);
        return ResponseResult.ok("请求成功");
    }

}
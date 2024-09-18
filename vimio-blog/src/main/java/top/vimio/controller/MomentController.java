package top.vimio.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.AccessLimit;
import top.vimio.annotation.VisitLogger;
import top.vimio.constant.JwtConstants;
import top.vimio.entity.Moment;
import top.vimio.entity.User;
import top.vimio.enums.VisitBehavior;
import top.vimio.utils.common.PageResult;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.MomentServic;
import top.vimio.service.impl.UserServiceImpl;
import top.vimio.utils.JwtUtils;

/**
 * @Description: 动态
 * @Author: Vimio
 * @Date: 2024/8/30 16:08
 */
@RestController
public class MomentController {
    @Autowired
    MomentServic momentService;

    @Autowired
    UserServiceImpl userService;

    /**
     * 分页查询动态List
     *
     * @param pageNum 页码
     * @param jwt     博主访问Token
     * @return
     */
    @VisitLogger(VisitBehavior.MOMENT)
    @GetMapping("/moments")
    public ResponseResult moments(@RequestParam(defaultValue = "1") Integer pageNum,
                     /* 加密锁*/   @RequestHeader(value = "Authorization", defaultValue = "") String jwt)
    {
        /**
         * 动态隐藏，仅博主可见
         *
        * */
        boolean adminIdentity = false;
        if (JwtUtils.judgeTokenIsExist(jwt)) {
            try {
                String subject = JwtUtils.getTokenBody(jwt).getSubject();
                if (subject.startsWith(JwtConstants.ADMIN_PREFIX)) {
                    //博主身份Token
                    String username = subject.replace(JwtConstants.ADMIN_PREFIX, "");
                    User admin = (User) userService.loadUserByUsername(username);
                    if (admin != null) {
                        adminIdentity = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 显示动态，所有人可见
         *
         * */
        PageInfo<Moment> pageInfo = new PageInfo<>(momentService.getMomentVOList(pageNum,/**/ adminIdentity));
        //存储Moment类型的对象集合
        PageResult<Moment> pageResult = new PageResult<>(pageInfo.getPages(), pageInfo.getList());
        //pageInfo.getPages()获取的是总页数信息、pageInfo.getList()获取当前页的动态信息列表。
        return ResponseResult.ok("获取成功", pageResult);
    }


    /**
     * 给动态点赞
     * 简单限制一下点赞
     *
     * @param id 动态id
     * @return
     */
    @AccessLimit(seconds = 86400, maxCount = 1, msg = "不可以重复点赞哦")
    @VisitLogger(VisitBehavior.LIKE_MOMENT)
    @PostMapping("/moment/like/{id}")
    public ResponseResult like(@PathVariable Long id) {
        momentService.addLikeByMomentId(id);
        return ResponseResult.ok("点赞成功");
    }
}

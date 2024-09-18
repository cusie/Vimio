package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.annotation.VisitLogger;
import top.vimio.enums.VisitBehavior;
import top.vimio.utils.common.PageResult;
import top.vimio.utils.common.ResponseResult;
import top.vimio.model.blogvo.vo.BlogInfoVo;
import top.vimio.service.BlogService;
/**
 * @Description: 标签
 * @Author: Vimio
 * @Date: 2024/9/2 20:04
 */
@RestController
public class TagController {
    @Autowired
    BlogService blogService;

    /**
     * 根据标签name分页查询公开博客列表
     *
     * @param tagName 标签name
     * @param pageNum 页码
     * @return
     */
    @VisitLogger(VisitBehavior.TAG)
    @GetMapping("/tag")
    public ResponseResult tag(@RequestParam String tagName,
                              @RequestParam(defaultValue = "1") Integer pageNum) {
        PageResult<BlogInfoVo> pageResult = blogService.getBlogInfoListByTagNameAndIsPublished(tagName, pageNum);
        return ResponseResult.ok("请求成功", pageResult);
    }
}

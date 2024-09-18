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
 * @Description: 文章分类
 * @Author: Vimio
 * @Date: 2024/8/31 21:25
 */
@RestController
public class CategoryController {
    @Autowired
    BlogService blogService;

    /**
     * 根据分类name分页查询公开博客列表
     *
     * @param categoryName 分类name
     * @param pageNum      页码
     * @return
     */
    @VisitLogger(VisitBehavior.CATEGORY)
    @GetMapping("/category")
    public ResponseResult category(@RequestParam String categoryName,
                                   @RequestParam(defaultValue = "1") Integer pageNum) {
        PageResult<BlogInfoVo> pageResult = blogService.getBlogInfoListByCategoryNameAndIsPublished(categoryName, pageNum);
        return ResponseResult.ok("请求成功", pageResult);
    }

}

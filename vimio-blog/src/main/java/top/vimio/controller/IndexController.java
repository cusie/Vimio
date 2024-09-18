package top.vimio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vimio.entity.Category;
import top.vimio.utils.common.ResponseResult;
import top.vimio.entity.Tag;
import top.vimio.model.blogvo.siteinfo.NewBlog;
import top.vimio.model.blogvo.siteinfo.RandomBlog;
import top.vimio.service.BlogService;
import top.vimio.service.CategoryService;
import top.vimio.service.SiteSettingService;
import top.vimio.service.TagService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 站点相关
 * @Author: Vimio
 * @Date: 2024/9/2 21:41
 */
@RestController
public class IndexController {
    @Autowired
    SiteSettingService siteSettingService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;

    /**
     * 获取站点配置信息、最新推荐博客、分类列表、标签云、随机博客
     *
     * @return
     */
    @GetMapping("/site")
    public ResponseResult site() {
        Map<String, Object> map = siteSettingService.getSiteInfo();
        // 底部最新推荐博客
        List<NewBlog> newBlogList = blogService.getNewBlogListByIsPublished();
        List<Category> categoryList = categoryService.getCategoryNameList();
        List<Tag> tagList = tagService.getTagListNotId();
        // 右侧随机博客
        List<RandomBlog> randomBlogList = blogService.getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend();
        map.put("newBlogList", newBlogList);
        map.put("categoryList", categoryList);
        map.put("tagList", tagList);
        map.put("randomBlogList", randomBlogList);
        return ResponseResult.ok("请求成功", map);
	}
}

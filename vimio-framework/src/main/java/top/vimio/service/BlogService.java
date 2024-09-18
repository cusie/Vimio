package top.vimio.service;

import top.vimio.entity.Blog;
import top.vimio.utils.common.PageResult;
import top.vimio.model.blogdto.BlogDto;
import top.vimio.model.blogdto.BlogVisibilityDto;
import top.vimio.model.blogvo.siteinfo.NewBlog;
import top.vimio.model.blogvo.siteinfo.RandomBlog;
import top.vimio.model.blogvo.vo.BlogDetailVo;
import top.vimio.model.blogvo.vo.BlogInfoVo;
import top.vimio.model.blogvo.vo.SearchBlogVo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/8/31 10:13
 */
public interface BlogService {

    /**
     * @return
     * @Description: 前台显示业务
     */
    int getBlogViewsByBlogId(Long blogId);
    /**
     * 博文搜索的业务
     */
    List<SearchBlogVo> getSearchBlogListByQueryAndIsPublished(String query);


    /**
     * 博文的业务
     */
    //文章列表显示
    PageResult<BlogInfoVo> getBlogInfoListByIsPublished(Integer pageNum);
    //文章按id查询


    //文章阅读
    BlogDetailVo getBlogByIdAndIsPublished(Long id);


    /**
     * 博文分类的业务
     */
    PageResult<BlogInfoVo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName, Integer pageNum);

    /**
     * 博文标签的业务
     */
    PageResult<BlogInfoVo> getBlogInfoListByTagNameAndIsPublished(String tagName, Integer pageNum);

    /**
     * 站点信息显示的业务
     */
    List<NewBlog> getNewBlogListByIsPublished();

    List<RandomBlog> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend();

    /**
     * 归档的业务
     */
    Map<String, Object> getArchiveBlogAndCountByIsPublished();

    int countBlogByIsPublished();


    /**
     * 评论工具的业务
     */
    String getTitleByBlogId(Long id);

    Boolean getCommentEnabledByBlogId(Long blogId);

    Boolean getPublishedByBlogId(Long blogId);

    String getBlogPassword(Long blogId);

    /**
     *
     * @Description: 公共业务
     * */

    /**
     *
     * @Description: 后台管理业务
     * */
    List<Blog> getListByTitleAndCategoryId(String title, Integer categoryId);

    Blog getBlogById(Long id);

    int countBlogByCategoryId(Long categoryId);

    int countBlogByTagId(Long tagId);

    //评论业务
    List<Blog> getIdAndTitleList();

    //博客增(发送博客)删改
    void deleteBlogTagByBlogId(Long blogId);

    void deleteBlogById(Long id);

    public void updateBlogTopById(Long blogId, Boolean top);

    public void updateBlogRecommendById(Long blogId, Boolean recommend);

    public void updateBlogVisibilityById(Long blogId, BlogVisibilityDto blogVisibility);

    void saveBlog(BlogDto blog);

    void saveBlogTag(Long blogId, Long tagId);

    void updateBlog(BlogDto blog);
}

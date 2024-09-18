package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Blog;
import top.vimio.model.blogdto.BlogDto;
import top.vimio.model.blogdto.BlogVisibilityDto;
import top.vimio.model.blogvo.siteinfo.NewBlog;
import top.vimio.model.blogvo.siteinfo.RandomBlog;
import top.vimio.model.blogvo.vo.*;

import java.util.List;

/**
 * @Description: 博客文章的持久层接口
 * @Author: Vimio
 * @Date: 2024/8/31 9:56
 */
@Mapper
@Repository
public interface BlogMapper {
    int updateViews(Long blogId, Integer views);

    int getBlogViewsByBlogId(Long blogId);
    /**
     * 博客文章搜索接口
     * */
    List<SearchBlogVo> getSearchBlogListByQueryAndIsPublished(String query);
    /**
     * 博客文章接口
     * */
    List<BlogInfoVo> getBlogInfoListByIsPublished();

    BlogDetailVo getBlogByIdAndIsPublished(Long id);

    /**
     * 博客分类接口
     * */
    List<BlogInfoVo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName);

    /**
     * 博客标签接口
     * */
    List<BlogInfoVo> getBlogInfoListByTagNameAndIsPublished(String tagName);

    /**
     * 站点信息显示接口
     * */

    List<NewBlog> getNewBlogListByIsPublished();

    List<RandomBlog> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend(Integer limitNum);

    /**
    * 归档接口
     * */
    List<String> getGroupYearMonthByIsPublished();

    List<ArchiveBlogVo> getArchiveBlogListByYearMonthAndIsPublished(String yearMonth);

    int countBlogByIsPublished();


    /**
     * 评论工具接口
     * */
    String getTitleByBlogId(Long id);

    Boolean getCommentEnabledByBlogId(Long blogId);

    Boolean getPublishedByBlogId(Long blogId);

    String getBlogPassword(Long blogId);


    /**
     * 后端管理工具接口
     * */
    List<Blog> getListByTitleAndCategoryId(@Param("title")String title, @Param("categoryId")Integer categoryId);

    Blog getBlogById(Long id);

    int countBlogByCategoryId(Long categoryId);

    int countBlogByTagId(Long tagId);

    List<Blog> getIdAndTitleList();
    /**
     * 记录日志工具接口
     * */

    int countBlog();

    List<CategoryBlogCount> getCategoryBlogCountList();

    /**
     * 博客增删改工具接口
     * */
    int deleteBlogById(Long id);

    int deleteBlogTagByBlogId(@Param("blogId")Long blogId);

    int updateBlogRecommendById(@Param("blogId")Long blogId, @Param("recommend")Boolean recommend);

    int updateBlogVisibilityById(@Param("blogId")Long blogId, @Param("bv")BlogVisibilityDto bv);

    int updateBlogTopById(@Param("blogId")Long blogId, @Param("top")Boolean top);

    int saveBlog(BlogDto blog);

    int saveBlogTag(@Param("blogId")Long blogId, @Param("tagId")Long tagId);

    int updateBlog(BlogDto blog);
}

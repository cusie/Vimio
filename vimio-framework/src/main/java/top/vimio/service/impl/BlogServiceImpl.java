package top.vimio.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.constant.RedisKeyConstants;
import top.vimio.entity.Blog;
import top.vimio.exception.PersistenceException;
import top.vimio.utils.common.PageResult;
import top.vimio.exception.NotFoundException;
import top.vimio.mapper.BlogMapper;
import top.vimio.model.blogdto.BlogDto;
import top.vimio.model.blogdto.BlogVisibilityDto;
import top.vimio.model.blogvo.siteinfo.NewBlog;
import top.vimio.model.blogvo.siteinfo.RandomBlog;
import top.vimio.model.blogvo.vo.ArchiveBlogVo;
import top.vimio.model.blogvo.vo.BlogDetailVo;
import top.vimio.model.blogvo.vo.BlogInfoVo;
import top.vimio.model.blogvo.vo.SearchBlogVo;
import top.vimio.service.BlogService;
import top.vimio.service.RedisService;
import top.vimio.utils.markdown.MarkdownUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/8/31 10:13
 */

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    RedisService redisService;

    //随机博客显示5条
    private static final int randomBlogLimitNum = 5;
    //最新推荐博客显示3条
    private static final int newBlogPageSize = 3;
    //每页显示5条博客简介
    private static final int pageSize = 5;
    //博客简介列表排序方式
    private static final String orderBy = "is_top desc, create_time desc";
    //私密博客提示
    private static final String PRIVATE_BLOG_DESCRIPTION = "此文章受密码保护！";

    /**
     * 博文阅读次数的业务实现
     */

    /**
     * 博文搜索的业务实现
     */
    @Override
    public List<SearchBlogVo> getSearchBlogListByQueryAndIsPublished(String query) {
        List<SearchBlogVo> searchBlogs = blogMapper.getSearchBlogListByQueryAndIsPublished(query);
        // 数据库的处理是不区分大小写的，那么这里的匹配串处理也应该不区分大小写，否则会出现不准确的结果
        query = query.toUpperCase();
        for (SearchBlogVo searchBlog : searchBlogs) {
            String content = searchBlog.getContent().toUpperCase();
            int contentLength = content.length();
            int index = content.indexOf(query) - 10;
            index = Math.max(index, 0);
            int end = index + 21;//以关键字字符串为中心返回21个字
            end = Math.min(end, contentLength - 1);
            searchBlog.setContent(searchBlog.getContent().substring(index, end));
        }
        return searchBlogs;
    }

    /**
     * 博文的业务实现
     */
    @Override
    public PageResult<BlogInfoVo> getBlogInfoListByIsPublished(Integer pageNum) {
        /**
         * redis缓存
         * */

        /**
         * 数据库查询
         * */
        //redis没有缓存，从数据库查询，并添加缓存
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<BlogInfoVo> blogInfos = processBlogInfosPassword(blogMapper.getBlogInfoListByIsPublished());
        PageInfo<BlogInfoVo> pageInfo = new PageInfo<>(blogInfos);
        PageResult<BlogInfoVo> pageResult = new PageResult<>(pageInfo.getPages(), pageInfo.getList());
        return pageResult;
    }

    private List<BlogInfoVo> processBlogInfosPassword(List<BlogInfoVo> blogInfos) {
        for (BlogInfoVo blogInfo : blogInfos) {
            if (!"".equals(blogInfo.getPassword())) {
                blogInfo.setPrivacy(true);
                blogInfo.setPassword("");
                blogInfo.setDescription(PRIVATE_BLOG_DESCRIPTION);
            } else {
                blogInfo.setPrivacy(false);
                blogInfo.setDescription(MarkdownUtils.markdownToHtmlExtensions(blogInfo.getDescription()));
            }
            //blogInfo.setTags(tagService.getTagListByBlogId(blogInfo.getId()));
        }
        return blogInfos;
    }

    @Override
    public BlogDetailVo getBlogByIdAndIsPublished(Long id) {
       BlogDetailVo blog = blogMapper.getBlogByIdAndIsPublished(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        return blog;
    }


    /**
     * 博文分类的业务实现
     */
    @Override
    public PageResult<BlogInfoVo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<BlogInfoVo> blogInfos = processBlogInfosPassword(blogMapper.getBlogInfoListByCategoryNameAndIsPublished(categoryName));
        PageInfo<BlogInfoVo> pageInfo = new PageInfo<>(blogInfos);
        PageResult<BlogInfoVo> pageResult = new PageResult<>(pageInfo.getPages(), pageInfo.getList());
        //setBlogViewsFromRedisToPageResult(pageResult);
        return pageResult;
    }


    /**
     * 博文标签的业务实现
     */
    @Override
    public PageResult<BlogInfoVo> getBlogInfoListByTagNameAndIsPublished(String tagName, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<BlogInfoVo> blogInfos = processBlogInfosPassword(blogMapper.getBlogInfoListByTagNameAndIsPublished(tagName));
        PageInfo<BlogInfoVo> pageInfo = new PageInfo<>(blogInfos);
        PageResult<BlogInfoVo> pageResult = new PageResult<>(pageInfo.getPages(), pageInfo.getList());
        //setBlogViewsFromRedisToPageResult(pageResult);
        return pageResult;
    }

    /**
     * 站点信息显示的业务实现
     */
    @Override
    public List<NewBlog> getNewBlogListByIsPublished() {
        PageHelper.startPage(1, newBlogPageSize);
        List<NewBlog> newBlogList = blogMapper.getNewBlogListByIsPublished();
        for (NewBlog newBlog : newBlogList) {
            if (!"".equals(newBlog.getPassword())) {
                newBlog.setPrivacy(true);
                newBlog.setPassword("");
            } else {
                newBlog.setPrivacy(false);
            }
        }
        return newBlogList;
    }

    @Override
    public List<RandomBlog> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend() {
        List<RandomBlog> randomBlogs = blogMapper.getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend(randomBlogLimitNum);
        for (RandomBlog randomBlog : randomBlogs) {
            if (!"".equals(randomBlog.getPassword())) {
                randomBlog.setPrivacy(true);
                randomBlog.setPassword("");
            } else {
                randomBlog.setPrivacy(false);
            }
        }
        return randomBlogs;
    }


    /**
     * 归档的业务实现
     */
    /**
     * 获取已发布博客的归档信息和数量
     *
     * 该方法首先从数据库中查询已发布博客的年份和月份分组，然后根据这些分组查询每个月的博客列表。
     * 如果博客设置了密码，则将其标记为私有，并清除密码。最后，将博客列表按照年份和月份归档，并返回一个包含归档博客映射和博客总数的Map。
     *
     * @return 一个包含归档博客映射和博客总数的Map
     */
    @Override
    public Map<String, Object> getArchiveBlogAndCountByIsPublished() {

        /**
         * redis缓存
         * */

        /**
         * 数据库查询
         * */
        // 获取已发布博客的年份和月份分组
        List<String> groupYearMonth = blogMapper.getGroupYearMonthByIsPublished();
        // 创建一个LinkedHashMap来存储归档博客信息
        Map<String, List<ArchiveBlogVo>> archiveBlogMap = new LinkedHashMap<>();
        // 遍历每个年份和月份分组
        for (String s : groupYearMonth) {
            // 根据年份和月份查询博客列表
            List<ArchiveBlogVo> archiveBlogs = blogMapper.getArchiveBlogListByYearMonthAndIsPublished(s);
            // 遍历博客列表，处理密码和隐私设置
            for (ArchiveBlogVo archiveBlog : archiveBlogs) {
                // 如果密码不为空，则设置博客为私有，并清空密码
                if (!"".equals(archiveBlog.getPassword())) {
                    archiveBlog.setPrivacy(true);
                    archiveBlog.setPassword("");
                } else {
                    // 否则，设置博客为公开
                    archiveBlog.setPrivacy(false);
                }
            }
            // 将处理后的博客列表添加到归档博客映射中
            archiveBlogMap.put(s, archiveBlogs);
        }
        // 获取已发布博客的总数
        Integer count = countBlogByIsPublished();
        // 创建一个Map来存储归档博客映射和博客总数
        Map<String, Object> map = new HashMap<>(4);
        // 将归档博客映射添加到Map中
        map.put("blogMap", archiveBlogMap);
        // 将博客总数添加到Map中
        map.put("count", count);
        // 返回包含归档博客映射和博客总数的Map
        return map;
    }

    @Override
    public int countBlogByIsPublished() {
        return blogMapper.countBlogByIsPublished();
    }



    /**
     * 评论工具的业务
     */
    @Override
    public String getTitleByBlogId(Long id) {
        return blogMapper.getTitleByBlogId(id);
    }

    @Override
    public Boolean getCommentEnabledByBlogId(Long blogId) {
        return blogMapper.getCommentEnabledByBlogId(blogId);
    }

    @Override
    public Boolean getPublishedByBlogId(Long blogId) {
        return blogMapper.getPublishedByBlogId(blogId);
    }

    @Override
    public String getBlogPassword(Long blogId) {
        return blogMapper.getBlogPassword(blogId);
    }



    @Override
    public List<Blog> getListByTitleAndCategoryId(String title, Integer categoryId) {
        return blogMapper.getListByTitleAndCategoryId(title, categoryId);
    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogMapper.getBlogById(id);
        if (blog == null) {
            throw new NotFoundException("博客不存在");
        }
        /**
         * 将浏览量设置为Redis中的最新值
         * 这里如果出现异常，查看第 152 行注释说明
         * @see BlogServiceImpl#setBlogViewsFromRedisToPageResult
         */
        int view = Math.toIntExact(blog.getId());
        blog.setViews(view);
        return blog;
    }

    @Override
    public int getBlogViewsByBlogId(Long blogId)
    {
        return blogMapper.getBlogViewsByBlogId(blogId);
    }


    @Override
    public int countBlogByCategoryId(Long categoryId) {
        return blogMapper.countBlogByCategoryId(categoryId);
    }
    @Override
    public int countBlogByTagId(Long tagId) {
        return blogMapper.countBlogByTagId(tagId);
    }

    @Override
    public List<Blog> getIdAndTitleList() {
        return blogMapper.getIdAndTitleList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlogTagByBlogId(Long blogId) {
        if (blogMapper.deleteBlogTagByBlogId(blogId) == 0) {
            throw new PersistenceException("维护博客标签关联表失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlogById(Long id) {
        if (blogMapper.deleteBlogById(id) != 1) {
            throw new NotFoundException("该博客不存在");
        }
        deleteBlogRedisCache();
        redisService.deleteByHashKey(RedisKeyConstants.BLOG_VIEWS_MAP, id);
    }

    /**
     * 删除首页缓存、最新推荐缓存、归档页面缓存、博客浏览量缓存
     */
    private void deleteBlogRedisCache() {
        redisService.deleteCacheByKey(RedisKeyConstants.HOME_BLOG_INFO_LIST);
        redisService.deleteCacheByKey(RedisKeyConstants.NEW_BLOG_LIST);
        redisService.deleteCacheByKey(RedisKeyConstants.ARCHIVE_BLOG_MAP);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBlogTopById(Long blogId, Boolean top) {
        if (blogMapper.updateBlogTopById(blogId, top) != 1) {
            throw new PersistenceException("操作失败");
        }
        redisService.deleteCacheByKey(RedisKeyConstants.HOME_BLOG_INFO_LIST);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBlogRecommendById(Long blogId, Boolean recommend) {
        if (blogMapper.updateBlogRecommendById(blogId, recommend) != 1) {
            throw new PersistenceException("操作失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBlogVisibilityById(Long blogId, BlogVisibilityDto blogVisibility) {
        if (blogMapper.updateBlogVisibilityById(blogId, blogVisibility) != 1) {
            throw new PersistenceException("操作失败");
        }
        redisService.deleteCacheByKey(RedisKeyConstants.HOME_BLOG_INFO_LIST);
        redisService.deleteCacheByKey(RedisKeyConstants.NEW_BLOG_LIST);
        redisService.deleteCacheByKey(RedisKeyConstants.ARCHIVE_BLOG_MAP);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveBlog(BlogDto blog) {
        if (blogMapper.saveBlog(blog) != 1) {
            throw new PersistenceException("添加博客失败");
        }
        redisService.saveKVToHash(RedisKeyConstants.BLOG_VIEWS_MAP, blog.getId(), 0);
        deleteBlogRedisCache();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveBlogTag(Long blogId, Long tagId) {
        if (blogMapper.saveBlogTag(blogId, tagId) != 1) {
            throw new PersistenceException("维护博客标签关联表失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBlog(BlogDto blog) {
        if (blogMapper.updateBlog(blog) != 1) {
            throw new PersistenceException("更新博客失败");
        }
        deleteBlogRedisCache();
        redisService.saveKVToHash(RedisKeyConstants.BLOG_VIEWS_MAP, blog.getId(), blog.getViews());
    }


}

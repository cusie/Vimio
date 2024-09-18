package top.vimio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vimio.annotation.OperationLogger;
import top.vimio.entity.Blog;
import top.vimio.entity.Comment;
import top.vimio.utils.common.ResponseResult;
import top.vimio.service.BlogService;
import top.vimio.service.CommentService;
import top.vimio.utils.StringUtils;

import java.util.List;

/**
 * @Description: 博客评论后台管理
 * @Author: Vimio
 * @Date: 2024/9/12 11:24
 */
@RestController
public class CommentAdminController {
    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;

    /**
     * 按页面和博客id分页查询评论List
     *
     * @param page     要查询的页面(博客文章or关于我...)
     * @param blogId   如果是博客文章页面 需要提供博客id
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return
     */
    @GetMapping("/comments")
    public ResponseResult comments(@RequestParam(defaultValue = "") Integer page,
                           @RequestParam(defaultValue = "") Long blogId,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<Comment> comments = commentService.getListByPageAndParentCommentId(page, blogId, -1L);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        return ResponseResult.ok("请求成功", pageInfo);
    }

    /**
     * 获取所有博客id和title 供评论分类的选择
     *
     * @return
     */
    @GetMapping("/blogIdAndTitle")
    public ResponseResult blogIdAndTitle() {
        List<Blog> blogs = blogService.getIdAndTitleList();
        return ResponseResult.ok("请求成功", blogs);
    }

    /**
     * 更新评论公开状态
     *
     * @param id        评论id
     * @param published 是否公开
     * @return
     */
    @OperationLogger("更新评论公开状态")
    @PutMapping("/comment/published")
    public ResponseResult updatePublished(@RequestParam Long id, @RequestParam Boolean published) {
        commentService.updateCommentPublishedById(id, published);
        return ResponseResult.ok("操作成功");
    }

    /**
     * 更新评论接收邮件提醒状态
     *
     * @param id     评论id
     * @param notice 是否接收提醒
     * @return
     */
    @OperationLogger("更新评论邮件提醒状态")
    @PutMapping("/comment/notice")
    public ResponseResult updateNotice(@RequestParam Long id, @RequestParam Boolean notice) {
        commentService.updateCommentNoticeById(id, notice);
        return ResponseResult.ok("操作成功");
    }

    /**
     * 按id删除该评论及其所有子评论
     *
     * @param id 评论id
     * @return
     */
    @OperationLogger("删除评论")
    @DeleteMapping("/comment")
    public ResponseResult delete(@RequestParam Long id) {
        commentService.deleteCommentById(id);
        return ResponseResult.ok("删除成功");
    }

    /**
     * 修改评论
     *
     * @param comment 评论实体
     * @return
     */
    @OperationLogger("修改评论")
    @PutMapping("/comment")
    public ResponseResult updateComment(@RequestBody Comment comment) {
        if (StringUtils.isEmpty(comment.getNickname(), comment.getAvatar(), comment.getEmail(), comment.getIp(), comment.getContent())) {
            return ResponseResult.error("参数有误");
        }
        commentService.updateComment(comment);
        return ResponseResult.ok("评论修改成功");
    }
}

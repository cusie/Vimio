package top.vimio.service;

import top.vimio.entity.Comment;
import top.vimio.model.blogdto.CommentDto;
import top.vimio.model.blogvo.vo.PageCommentVo;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/4 11:29
 */
public interface CommentService {

    /**
     *
     * @Description: 前台显示业务
     * */

    /**
     *
     * @Description: 后台管理业务
     * */

    /**
     *
     * @Description: 公共业务业务
     * */

    List<Comment> getListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId);

    /**
    * 评论列表显示
    * */
    List<PageCommentVo> getPageCommentList(Integer page, Long blogId, Long parentCommentId);


    int countByPageAndIsPublished(Integer page, Long blogId, Boolean isPublished);

    Comment getCommentById(Long id);

    void saveComment(CommentDto commentDto);


    //子评论递归检验

    void updateCommentPublishedById(Long commentId, Boolean published);

    void updateCommentNoticeById(Long commentId, Boolean notice);

    void deleteCommentById(Long commentId);

    void deleteCommentsByBlogId(Long blogId);

    void updateComment(Comment comment);

}

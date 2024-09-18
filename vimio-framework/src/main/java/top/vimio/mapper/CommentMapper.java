package top.vimio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.vimio.entity.Comment;
import top.vimio.model.blogdto.CommentDto;
import top.vimio.model.blogvo.vo.PageCommentVo;

import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/3 21:35
 */
@Mapper
@Repository
public interface CommentMapper {

    int countByPageAndIsPublished(@Param("page") Integer page, @Param("blogId") Long blogId, @Param("isPublished") Boolean isPublished);
    List<PageCommentVo> getPageCommentListByPageAndParentCommentId(@Param("page") Integer page,@Param("blogId")  Long blogId, @Param("parentCommentId") Long parentCommentId);

    List<Comment> getListByPageAndParentCommentId(@Param("page")Integer page , @Param("blogId")Long blogId, @Param("parentCommentId")Long parentCommentId);

    Comment getCommentById(Long id);

    int saveComment(CommentDto commentDto);


    //子评论递归检验
    int updateCommentPublishedById(Long commentId, Boolean published);

    int updateCommentNoticeById(Long commentId, Boolean notice);

    int deleteCommentById(Long commentId);

    int deleteCommentsByBlogId(Long blogId);

    int updateComment(Comment comment);

    List<Comment> getListByParentCommentId(Long parentCommentId);

    int countComment();

}

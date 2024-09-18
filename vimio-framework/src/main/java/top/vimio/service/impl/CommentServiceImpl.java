package top.vimio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vimio.entity.Comment;
import top.vimio.exception.PersistenceException;
import top.vimio.mapper.CommentMapper;
import top.vimio.model.blogdto.CommentDto;
import top.vimio.model.blogvo.vo.PageCommentVo;
import top.vimio.service.CommentService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/4 11:42
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public int countByPageAndIsPublished(Integer page, Long blogId, Boolean isPublished) {
        return commentMapper.countByPageAndIsPublished(page, blogId, isPublished);
    }

    @Override
    public List<PageCommentVo> getPageCommentList(Integer page, Long blogId, Long parentCommentId) {
        List<PageCommentVo> comments = getPageCommentListByPageAndParentCommentId(page, blogId, parentCommentId);
        for (PageCommentVo c : comments) {
            List<PageCommentVo> tmpComments = new ArrayList<>();
            getReplyComments(tmpComments, c.getReplyComments());
            //对于两列评论来说，按时间顺序排列应该比树形更合理些
            //排序一下
            Comparator<PageCommentVo> comparator = Comparator.comparing(PageCommentVo::getCreateTime);
            tmpComments.sort(comparator);

            c.setReplyComments(tmpComments);
        }
        return comments;
    }

    private List<PageCommentVo> getPageCommentListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId) {
        List<PageCommentVo> comments = commentMapper.getPageCommentListByPageAndParentCommentId(page, blogId, parentCommentId);
        for (PageCommentVo c : comments) {
            List<PageCommentVo> replyComments = getPageCommentListByPageAndParentCommentId(page, blogId, c.getId());
            c.setReplyComments(replyComments);
        }
        return comments;
    }


    @Override
    public List<Comment> getListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId) {
        List<Comment> comments = commentMapper.getListByPageAndParentCommentId(page, blogId, parentCommentId);
        for (Comment c : comments) {
            //递归查询子评论及其子评论
            List<Comment> replyComments = getListByPageAndParentCommentId(page, blogId, c.getId());
            c.setReplyComments(replyComments);
        }
        return comments;
    }

    /**
     * 将所有子评论递归取出到一个List中
     *
     * @param comments
     */
    private void getReplyComments(List<PageCommentVo> tmpComments, List<PageCommentVo> comments) {
        for (PageCommentVo c : comments) {
            tmpComments.add(c);
            getReplyComments(tmpComments, c.getReplyComments());
        }
    }

    @Override
    public Comment getCommentById(Long id) {
        Comment comment = commentMapper.getCommentById(id);
        if (comment == null) {
            throw new PersistenceException("评论不存在");
        }
        return comment;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveComment(CommentDto commentDto) {
        if (commentMapper.saveComment(commentDto) != 1) {
            throw new PersistenceException("评论失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentPublishedById(Long commentId, Boolean published) {
        //如果是隐藏评论，则所有子评论都要修改成隐藏状态
        if (!published) {
            List<Comment> comments = getAllReplyComments(commentId);
            for (Comment c : comments) {
                hideComment(c);
            }
        }

        if (commentMapper.updateCommentPublishedById(commentId, published) != 1) {
            throw new PersistenceException("操作失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentNoticeById(Long commentId, Boolean notice) {
        if (commentMapper.updateCommentNoticeById(commentId, notice) != 1) {
            throw new PersistenceException("操作失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCommentById(Long commentId) {
        List<Comment> comments = getAllReplyComments(commentId);
        for (Comment c : comments) {
            delete(c);
        }
        if (commentMapper.deleteCommentById(commentId) != 1) {
            throw new PersistenceException("评论删除失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCommentsByBlogId(Long blogId) {
        commentMapper.deleteCommentsByBlogId(blogId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateComment(Comment comment) {
        if (commentMapper.updateComment(comment) != 1) {
            throw new PersistenceException("评论修改失败");
        }
    }
    /**
     * 按id递归查询子评论
     *
     * @param parentCommentId 需要查询子评论的父评论id
     * @return
     */
    private List<Comment> getAllReplyComments(Long parentCommentId) {
        List<Comment> comments = commentMapper.getListByParentCommentId(parentCommentId);
        for (Comment c : comments) {
            List<Comment> replyComments = getAllReplyComments(c.getId());
            c.setReplyComments(replyComments);
        }
        return comments;
    }

    /**
     * 递归删除子评论
     *
     * @param comment 需要删除子评论的父评论
     */
    private void delete(Comment comment) {
        for (Comment c : comment.getReplyComments()) {
            delete(c);
        }
        if (commentMapper.deleteCommentById(comment.getId()) != 1) {
            throw new PersistenceException("评论删除失败");
        }
    }

    /**
     * 递归隐藏子评论
     *
     * @param comment 需要隐藏子评论的父评论
     */
    private void hideComment(Comment comment) {
        for (Comment c : comment.getReplyComments()) {
            hideComment(c);
        }
        if (commentMapper.updateCommentPublishedById(comment.getId(), false) != 1) {
            throw new PersistenceException("操作失败");
        }
    }
}

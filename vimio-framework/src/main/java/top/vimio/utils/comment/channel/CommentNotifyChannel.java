package top.vimio.utils.comment.channel;

import top.vimio.model.blogdto.CommentDto;

/**
 * @Description: CommentNotifyChannel
 * @Author: Vimio
 * @Date: 2024/9/4 9:22
 */
public interface CommentNotifyChannel {
    /**
     * 通过指定方式通知自己
     *
     * @param comment 当前收到的评论
     */
    void notifyMyself(CommentDto comment);

}

package top.vimio.utils.comment.channel;

import top.vimio.constant.CommentConstants;
import top.vimio.utils.common.SpringContextUtils;

/**
 * @Description: 评论提醒方式
 * @Author: Vimio
 * @Date: 2024/9/4 9:34
 */
public class ChannelFactory {
    /**
     * 创建评论提醒方式
     *
     * @param channelName 方式名称
     * @return
     */
    public static CommentNotifyChannel getChannel(String channelName) {
        if (CommentConstants.TELEGRAM.equalsIgnoreCase(channelName)) {
            return SpringContextUtils.getBean("telegramChannel", CommentNotifyChannel.class);
        } else if (CommentConstants.MAIL.equalsIgnoreCase(channelName)) {
            return SpringContextUtils.getBean("mailChannel", CommentNotifyChannel.class);
        }
        throw new RuntimeException("Unsupported value in [application.properties]: [comment.notify.channel]");
    }
}

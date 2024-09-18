package top.vimio.utils.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import top.vimio.config.properties.BlogProperties;
import top.vimio.constant.PageConstants;
import top.vimio.constant.RedisKeyConstants;
import top.vimio.entity.Comment;
import top.vimio.entity.User;
import top.vimio.enums.CommentOpenStateEnum;
import top.vimio.enums.CommentPageEnum;
import top.vimio.model.blogdto.CommentDto;
import top.vimio.model.blogvo.vo.FriendInfoVo;
import top.vimio.service.*;
import top.vimio.utils.*;
import top.vimio.utils.comment.channel.ChannelFactory;
import top.vimio.utils.comment.channel.CommentNotifyChannel;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 评论工具类
 * @Author: Vimio
 * @Date: 2024/9/4 9:20
 */
@Component
@DependsOn("springContextUtils")
public class CommentUtils {
    @Autowired
    private BlogProperties blogProperties;
    @Autowired
    private MailUtils mailUtils;
    @Autowired
    private AboutService aboutService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    private static BlogService blogService;

    private CommentNotifyChannel notifyChannel;
    /**
     * 新评论是否默认公开
     */
    private Boolean commentDefaultOpen;

    @Autowired
    public void setBlogService(BlogService blogService) {
        CommentUtils.blogService = blogService;
    }

    @Value("${comment.notify.channel}")
    public void setNotifyChannel(String channelName) {
        this.notifyChannel = ChannelFactory.getChannel(channelName);
    }

    @Value("${comment.default-open}")
    public void setCommentDefaultOpen(Boolean commentDefaultOpen) {
        this.commentDefaultOpen = commentDefaultOpen;
    }

    /**
     * 判断是否发送提醒
     * 6种情况：
     * 1.我以父评论提交：不用提醒
     * 2.我回复我自己：不用提醒
     * 3.我回复访客的评论：只提醒该访客
     * 4.访客以父评论提交：只提醒我自己
     * 5.访客回复我的评论：只提醒我自己
     * 6.访客回复访客的评论(即使是他自己先前的评论)：提醒我自己和他回复的评论
     *
     * @param commentDto        当前收到的评论
     * @param isVisitorComment 是否访客评论
     * @param parentComment    父评论
     */
    public void judgeSendNotify(CommentDto commentDto, boolean isVisitorComment, Comment parentComment) {
        if (parentComment != null && !parentComment.getAdminComment() && parentComment.getNotice()) {
            //我回复访客的评论，且对方接收提醒，邮件提醒对方(3)
            //访客回复访客的评论(即使是他自己先前的评论)，且对方接收提醒，邮件提醒对方(6)
            sendMailToParentComment(parentComment, commentDto);
        }
        if (isVisitorComment) {
            //访客以父评论提交，只提醒我自己(4)
            //访客回复我的评论，提醒我自己(5)
            //访客回复访客的评论，不管对方是否接收提醒，都要提醒我有新评论(6)
            notifyMyself(commentDto);
        }
    }

    /**
     * 发送邮件提醒回复对象
     *
     * @param parentComment 父评论
     * @param commentDto       当前收到的评论
     */
    private void sendMailToParentComment(Comment parentComment, CommentDto commentDto) {
        CommentPageEnum commentPageEnum = getCommentPageEnum(commentDto);
        Map<String, Object> map = new HashMap<>(16);
        map.put("parentNickname", parentComment.getNickname());
        map.put("nickname", commentDto.getNickname());
        map.put("title", commentPageEnum.getTitle());
        map.put("time", commentDto.getCreateTime());
        map.put("parentContent", parentComment.getContent());
        map.put("content", commentDto.getContent());
        map.put("url", blogProperties.getView() + commentPageEnum.getPath());
        String toAccount = parentComment.getEmail();
        String subject = "您在 " + blogProperties.getName() + " 的评论有了新回复";
        mailUtils.sendHtmlTemplateMail(map, toAccount, subject, "guest.html");
    }

    /**
     * 通过指定方式通知自己
     *
     * @param commentDto 当前收到的评论
     */
    private void notifyMyself(CommentDto commentDto) {
        notifyChannel.notifyMyself(commentDto);
    }

    /**
     * 获取评论对应的页面
     *
     * @param commentDto 当前收到的评论
     * @return CommentPageEnum
     */
    public static CommentPageEnum getCommentPageEnum(CommentDto commentDto) {
        CommentPageEnum commentPageEnum = CommentPageEnum.UNKNOWN;
        switch (commentDto.getPage()) {
            case 0:
                //普通博客
                commentPageEnum = CommentPageEnum.BLOG;
                commentPageEnum.setTitle(blogService.getTitleByBlogId(commentDto.getBlogId()));
                commentPageEnum.setPath("/blog/" + commentDto.getBlogId());
                break;
            case 1:
                //关于我页面
                commentPageEnum = CommentPageEnum.ABOUT;
                break;
            case 2:
                //友链页面
                commentPageEnum = CommentPageEnum.FRIEND;
                break;
            default:
                break;
        }
        return commentPageEnum;
    }

    /**
     * 查询对应页面评论是否开启
     *
     * @param page   页面分类（0普通文章，1关于我，2友链）
     * @param blogId 如果page==0，需要博客id参数，校验文章是否公开状态
     * @return CommentOpenStateEnum
     */
    public CommentOpenStateEnum judgeCommentState(Integer page, Long blogId) {
        switch (page) {
            case PageConstants.BLOG:
                //普通博客
                Boolean commentEnabled = blogService.getCommentEnabledByBlogId(blogId);
                Boolean published = blogService.getPublishedByBlogId(blogId);
                if (commentEnabled == null || published == null) {
                    //未查询到此博客
                    return CommentOpenStateEnum.NOT_FOUND;
                } else if (!published) {
                    //博客未公开
                    return CommentOpenStateEnum.NOT_FOUND;
                } else if (!commentEnabled) {
                    //博客评论已关闭
                    return CommentOpenStateEnum.CLOSE;
                }
                //判断文章是否存在密码
                String password = blogService.getBlogPassword(blogId);
                if (!StringUtils.isEmpty(password)) {
                    return CommentOpenStateEnum.PASSWORD;
                }
                break;
            case PageConstants.ABOUT:
                //关于我页面
                if (!aboutService.getAboutCommentEnabled()) {
                    //页面评论已关闭
                    return CommentOpenStateEnum.CLOSE;
                }
                break;
            case PageConstants.FRIEND:
                //友链页面
                FriendInfoVo friendInfo = friendService.getFriendInfo(true, false);
                if (!friendInfo.getCommentEnabled()) {
                    //页面评论已关闭
                    return CommentOpenStateEnum.CLOSE;
                }
                break;
            default:
                break;
        }
        return CommentOpenStateEnum.OPEN;
    }

    /**
     * 对于昵称不是QQ号的评论，根据昵称Hash设置头像
     *
     * @param commentDto 当前收到的评论
     */
    private void setCommentRandomAvatar(CommentDto commentDto) {
        //设置随机头像
        //根据评论昵称取Hash，保证每一个昵称对应一个头像
        long nicknameHash = HashUtils.getMurmurHash32(commentDto.getNickname());
        //计算对应的头像
        long num = nicknameHash % 6 + 1;
        String avatar = "/img/comment-avatar/" + num + ".jpg";
        commentDto.setAvatar(avatar);
    }

    /**
     * 通用博主评论属性
     *
     * @param commentDto 评论DTO
     * @param admin   博主信息
     */
    private void setGeneralAdminComment(CommentDto commentDto, User admin) {
        commentDto.setAdminComment(true);
        commentDto.setCreateTime(new Date());
        commentDto.setPublished(true);
        commentDto.setAvatar(admin.getAvatar());
        commentDto.setWebsite("/");
        commentDto.setNickname(admin.getNickname());
        commentDto.setEmail(admin.getEmail());
        commentDto.setNotice(false);
    }

    /**
     * 为[Telegram快捷回复]方式设置评论属性
     *
     * @param commentDto 评论DTO
     */
    public void setAdminCommentByTelegramAction(CommentDto commentDto) {
        //查出博主信息，默认id为1的记录就是博主
        User admin = userService.findUserById(1L);

        setGeneralAdminComment(commentDto, admin);
        commentDto.setIp("via Telegram");
    }

    /**
     * 设置博主评论属性
     *
     * @param commentDto 当前收到的评论
     * @param request 用于获取ip
     * @param admin   博主信息
     */
    public void setAdminComment(CommentDto commentDto, HttpServletRequest request, User admin) {
        setGeneralAdminComment(commentDto, admin);
        commentDto.setIp(IpAddressUtils.getIpAddress(request));
    }

    /**
     * 设置访客评论属性
     *
     * @param commentDto 当前收到的评论
     * @param request 用于获取ip
     */
    public void setVisitorComment(CommentDto commentDto, HttpServletRequest request) {
        String nickname = commentDto.getNickname();
        try {
            if (QQInfoUtils.isQQNumber(nickname)) {
                commentDto.setQq(nickname);
                commentDto.setNickname(QQInfoUtils.getQQNickname(nickname));
                setCommentQQAvatar(commentDto, nickname);
            } else {
                commentDto.setNickname(commentDto.getNickname().trim());
                setCommentRandomAvatar(commentDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            commentDto.setNickname(commentDto.getNickname().trim());
            setCommentRandomAvatar(commentDto);
        }

        //check website
        if (!isValidUrl(commentDto.getWebsite())) {
            commentDto.setWebsite("");
        }
        commentDto.setAdminComment(false);
        commentDto.setCreateTime(new Date());
        commentDto.setPublished(commentDefaultOpen);
        commentDto.setEmail(commentDto.getEmail().trim());
        commentDto.setIp(IpAddressUtils.getIpAddress(request));
    }

    /**
     * 设置QQ头像，复用已上传过的QQ头像，不再重复上传
     *
     * @param commentDto 当前收到的评论
     * @param qq      QQ号
     * @throws Exception 上传QQ头像时可能抛出的异常
     */
    private void setCommentQQAvatar(CommentDto commentDto, String qq) throws Exception {
        String uploadAvatarUrl = (String) redisService.getValueByHashKey(RedisKeyConstants.QQ_AVATAR_URL_MAP, qq);
        if (StringUtils.isEmpty(uploadAvatarUrl)) {
            uploadAvatarUrl = QQInfoUtils.getQQAvatarUrl(qq);
            redisService.saveKVToHash(RedisKeyConstants.QQ_AVATAR_URL_MAP, qq, uploadAvatarUrl);
        }
        commentDto.setAvatar(uploadAvatarUrl);
    }

    /**
     * URL合法性校验
     *
     * @param url url
     * @return 是否合法
     */
    private static boolean isValidUrl(String url) {
        return url.matches("^https?://([^!@#$%^&*?.\\s-]([^!@#$%^&*?.\\s]{0,63}[^!@#$%^&*?.\\s])?\\.)+[a-z]{2,6}/?");
    }
}

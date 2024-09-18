package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 页面评论(父子评论)
 * @Author: Vimio
 * @Date: 2024/9/4 11:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCommentVo {

    private Long id;
    private String nickname;//昵称
    private String content;//评论内容
    private String avatar;//头像(图片路径)
    private Date createTime;//评论时间
    private String website;//个人网站
    private Boolean adminComment;//博主回复
    private String parentCommentId;//父评论id
    private String parentCommentNickname;//父评论昵称

    private List<PageCommentVo> replyComments = new ArrayList<>();//回复该评论的评论
}

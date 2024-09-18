package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/8/30 15:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendInfoVo {
    private String content;//内容描述
    private Boolean commentEnabled;//评论开关
}
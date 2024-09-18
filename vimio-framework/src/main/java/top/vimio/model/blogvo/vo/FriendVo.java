package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/2 10:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendVo {
    private String nickname;//昵称
    private String description;//描述
    private String website;//站点
    private String avatar;//头像
}

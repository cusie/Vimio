package top.vimio.model.blogvo.siteinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 自定义爱好
 * @Author: Vimio
 * @Date: 2024/9/3 9:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    private String title;       //爱好类型
    private String content;     //爱好内容
}

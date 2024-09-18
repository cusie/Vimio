package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/6 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSaveVo {
    private String webname;//网站昵称
    private String description;//描述
    private String website;//站点
    private String avatar;//头像
    private String type;//网站类型
}

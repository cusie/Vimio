package top.vimio.model.blogvo.siteinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 页脚徽标
 * @Author: Vimio
 * @Date: 2024/9/3 9:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    private String title;    //信息标题
    private String url;      //信息链接
    private String subject;  //作用
    private String value;    //名称
    private String color;    //颜色
}

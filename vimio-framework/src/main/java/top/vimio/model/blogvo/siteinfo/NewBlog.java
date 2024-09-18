package top.vimio.model.blogvo.siteinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 底部最新推荐博客
 * @Author: Vimio
 * @Date: 2024/9/3 9:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBlog {
    private Long id;
    private String title;
    private String password;
    private Boolean privacy;
}

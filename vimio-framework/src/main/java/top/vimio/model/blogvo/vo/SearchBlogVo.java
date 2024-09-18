package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 关键字搜索博客
 * @Author: Vimio
 * @Date: 2024/9/6 17:32
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBlogVo {
    private Long id;
    private String title;
    private String content;

}

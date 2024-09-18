package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 评论管理页面按博客title查询评论
 * @Author: Vimio
 * @Date: 2024/9/3 21:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogIdAndTitleVo {
    private Long id;
    private String title;

}

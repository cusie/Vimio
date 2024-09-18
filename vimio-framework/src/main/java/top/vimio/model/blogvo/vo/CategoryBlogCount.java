package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 分类和博客数量
 * @Author: Vimio
 * @Date: 2024/9/12 8:42
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBlogCount {
    private Long id;
    private String name;//分类名
    private Integer value;//分类下博客数量
}

package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/12 8:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagBlogCount {
    private Long id;
    private String name;//标签名
    private Integer value;//标签下博客数量

}

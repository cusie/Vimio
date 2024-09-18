package top.vimio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 博文分类
 * @Author: Vimio
 * @Date: 2024/8/31 20:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String name;//分类名称
    private List<Blog> blogs = new ArrayList<>();//该分类下的博客文章
}

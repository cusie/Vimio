package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.vimio.entity.Category;
import top.vimio.entity.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 博客简要信息
 * @Author: Vimio
 * @Date: 2024/8/31 20:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogInfoVo {
    private Long id;
    private String title;//文章标题
    private String description;//描述
    private Date createTime;//创建时间
    private Integer views;//浏览次数
    private Integer words;//文章字数
    private Integer readTime;//阅读时长(分钟)
    private Boolean top;//是否置顶
    private String password;//文章密码
    private Boolean privacy;//是否私密文章

    /**
    *  文章分类、 文章标签
     *  分别源于数据库的category、tag
    * */
    private Category category;//文章分类
    private List<Tag> tags = new ArrayList<>();//文章标签
}

package top.vimio.model.blogvo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 归档页面博客简要信息
 * @Author: Vimio
 * @Date: 2024/8/31 9:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveBlogVo {
    private Long id;//文章序号
    private String title;//文章标签
    private String day;//当月文章创建日期指定日
    private String password;//密码
    private Boolean privacy;//是否是公开/私密文章
}

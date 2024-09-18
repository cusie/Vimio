package top.vimio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * @Description: 博客动态
 * @Author: Vimio
 * @Date: 2024/8/30 15:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Moment {
    private Long id;
    private String content;//动态内容
    private Date createTime;//创建时间
    private Integer likes;//点赞数量
    private Boolean published;//是否公开
}

package top.vimio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 网站收集实体类
 * @Author: Vimio
 * @Date: 2024/9/6 19:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSave {
    private Long id;
    private String webname;//网站昵称
    private String description;//描述
    private String website;//站点
    private String avatar;//站点图标
    private Integer type;//'1.基础、2.科学上网、3常用、4新世界'
    private Integer views;//浏览次数
    private Date createTime;//创建时间
}

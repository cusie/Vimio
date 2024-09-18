package top.vimio.model.blogdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 网站链Dto
 * @Author: Vimio
 * @Date: 2024/9/13 21:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSaveDto {
    private Long id;
    private String webname;//网站昵称
    private String description;//描述
    private String website;//站点
    private String avatar;//站点图标
    private Integer type;//'1.基础、2.科学上网、3常用、4新世界'
}

package top.vimio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 站点设置
 * @Author: Vimio
 * @Date: 2024/8/28 14:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteSetting {
    private Long id;        //编号
    private String nameEn;	//标题英文
    private String nameZh;	//标题中文
    private String value;	//标题内容
    private Integer type;	//1.重要的基础信息、2.次要信息,图标链接、3.首页徽标信息、4.友链页面信息
}


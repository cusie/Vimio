package top.vimio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @Description: 关于我的简介和信息
 * @Author: Vimio
 * @Date: 2024/8/27 20:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class About {
    private Long id;        //序号
    private String nameEn;  //英文标题
    private String nameZh;  //中文标题
    private String value;   //标题内容
}

package top.vimio.model.blogvo.siteinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 左侧栏资料卡片信息
 * @Author: Vimio
 * @Date: 2024/9/3 9:03
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Introduction {
    private String avatar;    //头像
    private String name;      //昵称
    private String github;    //github
    private String telegram;  //telegram
    private String qq;        //qq
    private String bilibili;  //bilibili
    private String netease;   //netease
    private String email;     //email

    private List<String> rollText = new ArrayList<>();
    private List<Favorite> favorites = new ArrayList<>();
}

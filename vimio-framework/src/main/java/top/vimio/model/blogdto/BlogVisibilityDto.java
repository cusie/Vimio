package top.vimio.model.blogdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 博客可见性DTO
 * @Author: Vimio
 * @Date: 2024/9/12 12:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogVisibilityDto {
    private Boolean appreciation;//赞赏开关
    private Boolean recommend;//推荐开关
    private Boolean commentEnabled;//评论开关
    private Boolean top;//是否置顶
    private Boolean published;//公开或私密
    private String password;//密码保护
}

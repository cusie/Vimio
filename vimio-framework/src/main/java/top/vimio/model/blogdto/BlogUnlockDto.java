package top.vimio.model.blogdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 受保护文章密码DTO
 * @Author: Vimio
 * @Date: 2024/9/6 19:36
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUnlockDto {
    private Long blogId;
    private String password;
}

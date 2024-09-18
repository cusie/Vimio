package top.vimio.model.blogdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 登录账号密码
 * @Author: Vimio
 * @Date: 2024/9/5 15:55
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDto {
    private String username;
    private String password;
}

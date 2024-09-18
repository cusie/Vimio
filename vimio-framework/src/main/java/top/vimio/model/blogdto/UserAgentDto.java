package top.vimio.model.blogdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: UserAgent解析DTO
 * @Author: Vimio
 * @Date: 2024/9/14 16:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAgentDto {
    private String os;
    private String browser;
}

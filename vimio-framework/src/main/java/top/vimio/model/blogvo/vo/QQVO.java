package top.vimio.model.blogvo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/4 10:39
 */
@Data
public class QQVO {
    /**
     * qq号
     */
    private Long qq;

    /**
     * qq昵称
     */
    private String name;

    /**
     * qq邮箱
     */
    private String email;

    /**
     * qq头像
     */
    private String avatar;
}

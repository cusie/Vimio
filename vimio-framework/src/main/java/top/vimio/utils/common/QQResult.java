package top.vimio.utils.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/4 10:37
 */
@Data
public class QQResult {


    private String code;
    /**
     * qq昵称
     */
    private String name;
    /**
     * qq邮箱
     */
    private String mail;
    /**
     * qq头像
     */
    private String imgurl;

/*    private String success;

    private String msg;

    private Map<String, Object> data;

    private String time;

    private  String api_vers;*/
}

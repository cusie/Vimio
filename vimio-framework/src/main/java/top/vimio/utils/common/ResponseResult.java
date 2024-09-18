package top.vimio.utils.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @Description: 封装响应结果
 * @Author: Vimio
 * @Date: 2024/8/27 21:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult{
private Integer code;
private String msg;
private Object data;

private ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
        }

public static ResponseResult ok(String msg, Object data) {
        return new ResponseResult(200, msg, data);
        }

public static ResponseResult ok(String msg) {
        return new ResponseResult(200, msg);
        }

public static ResponseResult error(String msg) {
        return new ResponseResult(500, msg);
        }

public static ResponseResult error() {
        return new ResponseResult(500, "异常错误");
        }

public static ResponseResult create(Integer code, String msg, Object data) {
        return new ResponseResult(code, msg, data);
        }

public static ResponseResult create(Integer code, String msg) {
        return new ResponseResult(code, msg);
        }


}
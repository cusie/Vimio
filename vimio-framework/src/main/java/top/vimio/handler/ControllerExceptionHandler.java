package top.vimio.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.vimio.exception.NotFoundException;
import top.vimio.exception.PersistenceException;
import top.vimio.utils.common.ResponseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 对Controller层全局异常处理
 * @RestControllerAdvice 捕获异常后，返回json数据类型
 * @Author: Vimio
 * @Date: 2024/9/10 10:32
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 捕获自定义的404异常
     *
     * @param request 请求
     * @param e       自定义抛出的异常信息
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseResult notFoundExceptionHandler(HttpServletRequest request, NotFoundException e) {
        logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
        return ResponseResult.create(404, e.getMessage());
    }

    /**
     * 捕获自定义的持久化异常
     *
     * @param request 请求
     * @param e       自定义抛出的异常信息
     * @return
     */
    @ExceptionHandler(PersistenceException.class)
    public ResponseResult persistenceExceptionHandler(HttpServletRequest request, PersistenceException e) {
        logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
        return ResponseResult.create(500, e.getMessage());
    }

    /**
     * 捕获自定义的登录失败异常
     *
     * @param request 请求
     * @param e       自定义抛出的异常信息
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseResult usernameNotFoundExceptionHandler(HttpServletRequest request, UsernameNotFoundException e) {
        logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
        return ResponseResult.create(401, "用户名或密码错误！");
    }

    /**
     * 捕获其它异常
     *
     * @param request 请求
     * @param e       异常信息
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(HttpServletRequest request, Exception e) {
        logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
        return ResponseResult.create(500, "异常错误");
    }
}

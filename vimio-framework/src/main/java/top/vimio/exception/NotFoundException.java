package top.vimio.exception;

/**
 * @Description: 404异常
 * @Author: Vimio
 * @Date: 2024/9/1 21:45
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

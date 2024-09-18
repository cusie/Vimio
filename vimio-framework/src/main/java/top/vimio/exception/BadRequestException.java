package top.vimio.exception;

/**
 * @Description:
 * @Author: Vimio
 * @Date: 2024/9/4 10:49
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

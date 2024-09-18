package top.vimio.exception;

/**
 * @Description: 持久化异常
 * @Author: Vimio
 * @Date: 2024/9/3 21:09
 */
public class PersistenceException extends RuntimeException{

    public PersistenceException() {
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}

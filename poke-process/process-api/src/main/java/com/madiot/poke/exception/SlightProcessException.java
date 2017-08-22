/**
 * @Title: SlightProcessException.java
 * @Package com.madiot.poke.exception
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.exception;

/**
 * @ClassName: SlightProcessException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class SlightProcessException extends RuntimeException {

    public SlightProcessException() {
    }

    public SlightProcessException(String message) {
        super(message);
    }

    public SlightProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlightProcessException(Throwable cause) {
        super(cause);
    }

    public SlightProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

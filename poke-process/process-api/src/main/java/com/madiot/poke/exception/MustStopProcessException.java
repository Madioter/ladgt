/**
 * @Title: MustStopProcessException.java
 * @Package com.madiot.poke.exception
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.exception;

/**
 * @ClassName: MustStopProcessException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class MustStopProcessException extends RuntimeException {

    public MustStopProcessException() {
    }

    public MustStopProcessException(String message) {
        super(message);
    }

    public MustStopProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MustStopProcessException(Throwable cause) {
        super(cause);
    }

    public MustStopProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

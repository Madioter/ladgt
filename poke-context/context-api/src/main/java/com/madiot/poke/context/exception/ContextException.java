/**
 * @Title: CallHelperException.java
 * @Package com.madiot.poke.context.exception
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.context.exception;

/**
 * @ClassName: CallHelperException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class ContextException extends RuntimeException {

    public ContextException() {
    }

    public ContextException(String message) {
        super(message);
    }

    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextException(Throwable cause) {
        super(cause);
    }

    public ContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

/**
 * @Title: ComparatorException.java
 * @Package com.madiot.poke.rule.api
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.errors;

/**
 * @ClassName: ComparatorException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class ComparatorException extends PokeRuleException {

    public ComparatorException() {
    }

    public ComparatorException(String message) {
        super(message);
    }

    public ComparatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComparatorException(Throwable cause) {
        super(cause);
    }

    public ComparatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

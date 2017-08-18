/**
 * @Title: PokeContainsException.java
 * @Package com.madiot.poke.rule.errors
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 * @version
 */
package com.madiot.poke.errors;

/**
 * @ClassName: PokeContainsException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/18
 */
public class PokeContainsException extends PokeRuleException {

    public PokeContainsException() {
    }

    public PokeContainsException(String message) {
        super(message);
    }

    public PokeContainsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PokeContainsException(Throwable cause) {
        super(cause);
    }

    public PokeContainsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

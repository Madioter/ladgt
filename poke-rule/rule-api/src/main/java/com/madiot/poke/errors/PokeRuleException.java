/**
 * @Title: PokeRuleException.java
 * @Package com.madiot.poke.rule.api
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.poke.errors;

/**
 * @ClassName: PokeRuleException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class PokeRuleException extends RuntimeException {

    public PokeRuleException() {
    }

    public PokeRuleException(String message) {
        super(message);
    }

    public PokeRuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public PokeRuleException(Throwable cause) {
        super(cause);
    }

    public PokeRuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

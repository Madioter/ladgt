/**
 * @Title: RedisException.java
 * @Package com.madiot.common.redis.exception
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.common.redis.exception;

/**
 * @ClassName: RedisException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class RedisException extends RuntimeException {

    /**
     * 构造方法
     * @param message 异常信息
     */
    public RedisException(String message) {
        super(message);
    }

    /**
     * 构造方法
     * @param message 异常信息
     * @param e 异常
     */
    public RedisException(String message, Exception e) {
        super(message, e);
    }

    /**
     * 构造方法
     * @param e 异常
     */
    public RedisException(Exception e) {
        super(e);
    }
}

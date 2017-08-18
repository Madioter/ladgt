/**
 * @Title: InvalidException.java
 * @Package com.igdata.gbparser.common.validate
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/4/26
 * @version
 */
package com.madioter.poker.netty.message.common.validate;

import com.madioter.poker.common.exception.ParserException;

/**
 * @ClassName: InvalidException
 * @Description: 无效值异常
 * @author Yi.Wang2
 * @date 2017/4/26
 */
public class InvalidException extends ParserException {

    /**
     * 国标数据无效值异常
     * @param message 异常信息
     */
    public InvalidException(String message) {
        super(message);
    }

    /**
     * 国标数据无效值异常
     * @param message 异常信息
     * @param cause 异常原因
     */
    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}

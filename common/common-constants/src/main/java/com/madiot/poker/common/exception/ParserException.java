package com.madiot.poker.common.exception;

/**
 * Created by DELL on 2017/5/10.
 */
public class ParserException extends RuntimeException {

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}

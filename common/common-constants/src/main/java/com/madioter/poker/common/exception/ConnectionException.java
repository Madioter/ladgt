package com.madioter.poker.common.exception;

/**
 * Created by DELL on 2017/5/8.
 */
public class ConnectionException extends Exception {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}

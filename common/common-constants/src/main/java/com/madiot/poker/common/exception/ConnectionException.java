package com.madiot.poker.common.exception;

import org.omg.SendingContext.RunTime;

/**
 * Created by DELL on 2017/5/8.
 */
public class ConnectionException extends RuntimeException {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}

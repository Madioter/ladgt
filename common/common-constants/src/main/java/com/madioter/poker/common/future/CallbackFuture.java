package com.madioter.poker.common.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by DELL on 2017/5/7.
 */
public class CallbackFuture<T> implements Future<T> {
    private volatile boolean completed;
    private volatile boolean cancelled;
    private volatile T result;
    private volatile Exception ex;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isDone() {
        return this.completed;
    }

    private T getResult() throws ExecutionException {
        if (this.ex != null) {
            throw new ExecutionException(this.ex);
        } else {
            return this.result;
        }
    }

    public synchronized T get() throws InterruptedException, ExecutionException {
        while (!this.completed) {
            this.wait();
        }

        return this.getResult();
    }

    public synchronized T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (unit == null) {
            unit = TimeUnit.MILLISECONDS;
        }
        long msecs = unit.toMillis(timeout);
        long startTime = msecs <= 0L ? 0L : System.currentTimeMillis();
        long waitTime = msecs;
        if (this.completed) {
            return this.getResult();
        } else if (msecs <= 0L) {
            throw new TimeoutException();
        } else {
            do {
                this.wait(waitTime);
                if (this.completed) {
                    return this.getResult();
                }

                waitTime = msecs - (System.currentTimeMillis() - startTime);
            } while (waitTime > 0L);

            throw new TimeoutException();
        }
    }

    public boolean completed(T result) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }

            this.completed = true;
            this.result = result;
            this.notifyAll();
        }

        return true;
    }

    public boolean failed(Exception exception) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }

            this.completed = true;
            this.ex = exception;
            this.notifyAll();
        }

        return true;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }

            this.completed = true;
            this.cancelled = true;
            this.notifyAll();
        }

        return true;
    }

    public boolean cancel() {
        return this.cancel(true);
    }

}

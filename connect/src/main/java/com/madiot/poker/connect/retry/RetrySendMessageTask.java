package com.madiot.poker.connect.retry;

import com.madiot.common.utils.bytes.ByteUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 重试请求发送类 Created by Yi.Wang2 on 2016/10/25.
 */
public class RetrySendMessageTask {

    private static RetrySendMessageTask ourInstance;

    public static RetrySendMessageTask getInstance() {
        if (ourInstance == null) {
            synchronized (RetrySendMessageTask.class) {
                if (ourInstance == null) {
                    ourInstance = new RetrySendMessageTask();
                }
            }
        }
        return ourInstance;
    }

    private RetrySendMessageTask() {

    }

    /**
     * 最大重试次数
     */
    private static final int MAX_RETRY_TIMES = 3;

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RetrySendMessageTask.class);

    /**
     * 重试任务线程
     */
    private RetryThread retryThread;

    /**
     * 等待消息处理队列
     */
    private static BlockingQueue<RetryVo> retryQueue = new LinkedBlockingQueue<RetryVo>();

    /**
     * 增加一条重试指令
     *
     * @param message 消息
     * @param channel 通道
     */
    public void addRetryMessage(byte[] message, Channel channel) {
        initRetryThread();
        RetryVo retryVo = new RetryVo(message, channel);
        retryQueue.add(retryVo);
    }

    /**
     * 重试线程初始化，确保重试线程有效
     */
    private void initRetryThread() {
        if (retryThread == null) {
            retryThread = new RetryThread();
        }
        if (!retryThread.isAlive()) {
            retryThread.start();
        }
    }

    /**
     * 重试对象
     */
    public class RetryVo {

        /**
         * 重试次数
         */
        private int retryTimes;

        /**
         * 重试消息
         */
        private byte[] message;

        /**
         * 重试通道
         */
        private Channel channel;

        /**
         * 构造方法
         *
         * @param message 重试消息
         * @param channel 重试通道
         */
        RetryVo(byte[] message, Channel channel) {
            this.retryTimes = 0;
            this.message = message;
            this.channel = channel;
        }

        /**
         * 再次重试
         *
         * @return boolean 是否允许再次重试
         */
        public boolean tryAgain() {
            if (retryTimes >= MAX_RETRY_TIMES) {
                LOGGER.error("请求重试次数超过{}次，停止重试. message:{}",
                        new Object[]{Integer.toString(MAX_RETRY_TIMES), ByteUtils.bytesToHexString(this.message)});
            } else {
                retryTimes++;
                return true;
            }
            return false;
        }

        /**
         * Get message byte [ ].
         *
         * @return the byte [ ]
         */
        public byte[] getMessage() {
            return message;
        }

        /**
         * Gets channel.
         *
         * @return the channel
         */

        public Channel getChannel() {
            return channel;
        }
    }

    /**
     * 重试线程
     */
    public class RetryThread extends Thread {

        /**
         * 再次重试
         *
         * @param retryVo 重试对象
         */
        private void addRetryMessage(RetryVo retryVo) {
            initRetryThread();
            // 重试次数+1
            if (retryVo.tryAgain()) {
                retryQueue.add(retryVo);
            }
        }

        @Override
        public void run() {
            // 循环处理重试请求
            while (true) {
                final RetryVo retryVo = retryQueue.poll();
                // 如果没有重试对象，等到1秒后再检查
                if (retryVo == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    continue;
                }
                LOGGER.info("execute retry task");
                // 执行重试
                if (retryVo.getChannel() != null && retryVo.getChannel().isWritable()) {
                    ChannelFuture future = retryVo.getChannel().writeAndFlush(retryVo.getMessage());
                    future.addListener(new GenericFutureListener() {
                        @Override
                        public void operationComplete(Future future) throws Exception {
                            // 重试失败后再次添加到重试队列中
                            if (future.cause() != null) {
                                LOGGER.info("send message to retry");
                                addRetryMessage(retryVo);
                            }
                        }
                    });
                } else {
                    addRetryMessage(retryVo);
                }
            }
        }
    }
}

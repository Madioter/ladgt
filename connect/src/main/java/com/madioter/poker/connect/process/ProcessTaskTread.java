package com.madioter.poker.connect.process;

import com.madiot.poke.dubbo.api.server.IProcessService;
import com.madiot.common.spring.SpringContextUtils;
import com.madiot.common.utils.bytes.ByteUtils;
import com.madioter.poker.connect.retry.RetrySendMessageTask;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程异步操作线程
 * Created by Yi.Wang2 on 2016/10/21.
 */
public class ProcessTaskTread extends Thread {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessTaskTread.class);

    private static final IProcessService processService = SpringContextUtils.getBeanByClass(IProcessService.class);

    /**
     * 任务
     */
    private byte[] message;

    /**
     * tcp通道
     */
    private Channel channel;

    /**
     * 构造方法
     *
     * @param message message
     * @param channel channel
     */
    public ProcessTaskTread(byte[] message, Channel channel) {
        this.message = message;
        this.channel = channel;
    }

    @Override
    public void run() {
        final byte[] response = processService.receiptMessage(message);

        if (!ArrayUtils.isEmpty(response)) {
            LOGGER.debug("send response to tbox: {}", ByteUtils.bytesToHexString(response));
            if (channel.isWritable()) {
                ChannelFuture future = channel.writeAndFlush(response);
                future.addListener(new GenericFutureListener() {

                    @Override
                    public void operationComplete(Future future) throws Exception {
                        if (!future.isSuccess()) {
                            LOGGER.info("send message failed", future.cause());
                            RetrySendMessageTask.getInstance().addRetryMessage(response, channel);
                        }
                    }
                });
            } else {
                RetrySendMessageTask.getInstance().addRetryMessage(response, channel);
            }
        }
    }
}

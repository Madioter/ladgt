/**
 * @Title: MessageSendService.java
 * @Package com.madioter.poker.connect.service
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.poker.connect.service;

import com.madiot.poke.dubbo.api.connect.IMessageSendService;
import com.madiot.common.utils.bytes.ByteUtils;
import com.madiot.poker.common.exception.ConnectionException;
import com.madiot.poker.connect.connection.ConnectionManager;
import com.madiot.poker.connect.dto.ChannelInfo;
import com.madiot.poker.connect.retry.RetrySendMessageTask;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: MessageSendService
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class MessageSendService implements IMessageSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSendService.class);

    @Override
    public void sendMessage(final byte[] data, String remoteIp, Integer playerId) throws ConnectionException {
        final ChannelInfo channelInfo = ConnectionManager.getInstance().getByRemoteIp(remoteIp);

        if (channelInfo == null || !channelInfo.getPlayerId().equals(playerId) || channelInfo.getChannel() == null
                || !channelInfo.getChannel().isActive()) {
            throw new ConnectionException("连接异常，消息无法发送");
        }

        if (data != null) {
            LOGGER.debug("send response to tbox: {}", ByteUtils.bytesToHexString(data));
            if (channelInfo.getChannel().isWritable()) {
                ChannelFuture future = channelInfo.getChannel().writeAndFlush(data);
                future.addListener(new GenericFutureListener() {

                    @Override
                    public void operationComplete(Future future) throws Exception {
                        if (!future.isSuccess()) {
                            LOGGER.info("send message failed", future.cause());
                            RetrySendMessageTask.getInstance().addRetryMessage(data, channelInfo.getChannel());
                        }
                    }
                });
            } else {
                RetrySendMessageTask.getInstance().addRetryMessage(data, channelInfo.getChannel());
            }
        }
    }
}

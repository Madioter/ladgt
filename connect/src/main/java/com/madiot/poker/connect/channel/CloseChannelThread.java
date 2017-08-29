package com.madiot.poker.connect.channel;

import com.madiot.poke.dubbo.api.server.IPlayerStatusChange;
import com.madiot.common.spring.SpringContextUtils;
import com.madiot.poker.connect.connection.ConnectionManager;
import com.madiot.poker.connect.dto.ChannelInfo;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 关闭通道线程
 * Created by Yi.Wang2 on 2016/10/21.
 */
public class CloseChannelThread extends Thread {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CloseChannelThread.class);

    /**
     * 远程IP
     */
    private Channel channel;

    public CloseChannelThread(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
        String remoteIp = insocket.getAddress().getHostAddress() + ":" + insocket.getPort();

        ChannelInfo channelInfo = ConnectionManager.getInstance().release(remoteIp);
        if (channelInfo != null) {
            try {
                Integer playerId = channelInfo.getPlayerId();
                SpringContextUtils.getBeanByClass(IPlayerStatusChange.class).offLine(playerId, remoteIp);
            } catch (NullPointerException e) {
                LOGGER.error("服务提供者不存在，需要先添加服务提供者", e);
            }

        }

        String ip = insocket.getAddress().getHostAddress();
        if (ip.startsWith("10.") || ip.startsWith("100.")) {

        } else {
            LOGGER.info("关闭远程TCP连接：remoteIp: {}", remoteIp);
        }
        this.channel.close();
    }
}

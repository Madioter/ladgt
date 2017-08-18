package com.madioter.poker.connect.connection;

import com.madioter.poker.connect.dto.ChannelInfo;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 长连接会话管理
 *
 * @author Jie.Chen
 */
public class ConnectionManager {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    /**
     * 单例对象
     */
    private static ConnectionManager INSTANCE;

    /**
     * 计数
     */
    private static AtomicLong ALL = new AtomicLong(0);

    /**
     * 增加计数
     */
    private static AtomicLong ADD = new AtomicLong(0);

    /**
     * 删除计数
     */
    private static AtomicLong REMOVE = new AtomicLong(0);

    /**
     * 所有长连接
     */
    private ConcurrentSkipListMap<String, ChannelInfo> connectionMap = new ConcurrentSkipListMap<String, ChannelInfo>();

    private ConnectionManager() {

    }

    /**
     * Returns the singleton INSTANCE of SessionManager.
     *
     * @return the INSTANCE
     */
    public static ConnectionManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ConnectionManager.class) {
                INSTANCE = new ConnectionManager();
            }
        }
        return INSTANCE;
    }

    /**
     * 注册长连接
     *
     * @param remoteIp
     *          the remote ip
     * @param channel
     *          the channel
     */
    public ChannelInfo register(String remoteIp, Channel channel) {
        if (remoteIp == null) {
            return null;
        }
        ALL.incrementAndGet();
        ADD.incrementAndGet();
        // 如果Map中存在channelInfo时，不再put到对象中，并且返回Map中存在的channelInfo
        ChannelInfo newChannelInfo = new ChannelInfo(channel);
        ChannelInfo channelInfo = connectionMap.putIfAbsent(remoteIp.trim(), newChannelInfo);
        if (channelInfo == null) {
            channelInfo = newChannelInfo;
        }
        LOGGER.debug("ip:" + remoteIp + "的客户端与服务器建立长连接,当前长连接数量有" + connectionMap.size() + "个");
        return channelInfo;
    }

    /**
     * 释放长连接
     *
     * @param remoteIp
     *          the remote ip
     */
    public ChannelInfo release(String remoteIp) {
        if (remoteIp == null) {
            return null;
        }
        ALL.decrementAndGet();
        REMOVE.incrementAndGet();
        if (connectionMap.get(remoteIp.trim()) != null) {
            LOGGER.debug("ip:" + remoteIp + "的长连接被释放,当前长连接数量有" + connectionMap.size() + "个");
            return connectionMap.remove(remoteIp.trim());
        } else {
            return null;
        }
    }

    public Map<String, ChannelInfo> getConnectionMap() {
        return connectionMap;
    }

    /**
     * 通过 remoteIp 获取通道
     *
     * @param remoteIp
     *          remoteIp
     * @return Channel by remote ip
     */
    public ChannelInfo getByRemoteIp(String remoteIp) {
        if (remoteIp == null) {
            return null;
        } else {
            return getConnectionMap().get(remoteIp.trim());
        }
    }
}

package com.madioter.poker.connect.channel;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 打开通道线程类
 * Created by Yi.Wang2 on 2016/10/21.
 */
public class OpenChannelThread extends Thread {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenChannelThread.class);

    /**
     * 远程IP
     */
    private String remoteIp;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * tcp通道
     */
    private Channel channel;

    public OpenChannelThread(String remoteIp, String serverIp, Channel channel) {
        this.remoteIp = remoteIp;
        this.serverIp = serverIp;
        this.channel = channel;
    }

    @Override
    public void run() {

        InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
        String ip=insocket.getAddress().getHostAddress();
        //过滤掉阿里云HA心跳连接的打印信息
        if(ip.startsWith("10.")||ip.startsWith("100.")){

        }else{
            LOGGER.info("开启远程TCP连接：remoteIp: {}|serverIp: {}", remoteIp, serverIp);
        }
        // 预留做连接成功后操作
        // 上线时不做通知，不注册通道，只有身份认证时才做上线通知，并认为正式上线
    }
}

package com.madioter.poker.connect.netty;

import com.madiot.common.spring.SpringContextUtils;
import com.madiot.common.utils.bytes.ByteUtils;
import com.madioter.poker.connect.channel.CloseChannelThread;
import com.madioter.poker.connect.channel.OpenChannelThread;
import com.madioter.poker.connect.process.ProcessTaskTread;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.RejectedExecutionException;

/**
 * 通道处理类 Created by Jie.Chen on 2016/7/5.
 */
public class ServiceDispatcher extends ChannelInboundHandlerAdapter {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDispatcher.class);

    /**
     * java线程池
     * 线程池维护线程的最少数量  5
     * 线程池维护线程所允许的空闲时间 30000 MILLISECONDS
     * 线程池维护线程的最大数量  50
     * 线程池所使用的缓冲队列  5000
     */
    private ThreadPoolTaskExecutor taskExecutor = SpringContextUtils.getBeanByClass(ThreadPoolTaskExecutor.class);

    /**
     * 远程设备客户机IP
     */
    private String remoteIp;

    /**
     * 本地NETTY服务器IP
     */
    private String serverIp;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 获取远程设备IP
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        remoteIp = insocket.getAddress().getHostAddress() + ":" + insocket.getPort();
        // 本地IP
        InetAddress inet = InetAddress.getLocalHost();
        serverIp = inet.getHostAddress();

        // 连接初始化操作
        openChannel(remoteIp, serverIp, ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        closeChannel(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.debug("收到客户端{}发送至服务器端{}的心跳信息！{}", new String[]{remoteIp, serverIp, ByteUtils.bytesToHexString((byte[]) msg)});

        try {
            taskExecutor.execute(new ProcessTaskTread((byte[]) msg, ctx.channel()));
        } catch (RejectedExecutionException e) {
            LOGGER.debug("当前连接数过多，断开后重试");
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = insocket.getAddress().getHostAddress();
        if (cause.getMessage().startsWith("Connection reset")//判断是否是RST包导致的异常，如果是则不做处理
                //因为4层TCP来源IP为客户端的IP，所以若是10、100开头的IP那么肯定是SLB的健康检查
                && (ip.startsWith("10.") || ip.startsWith("100."))) {
        } else {
            LOGGER.error("Unexpected exception from downstream.", cause);
            ctx.close();
        }

    }

    /**
     * 连接初始化
     *
     * @param remoteIp 客户端IP
     * @param serverIp 服务端IP
     * @param channel  通道
     */
    public void openChannel(String remoteIp, String serverIp, Channel channel) {
        taskExecutor.execute(new OpenChannelThread(remoteIp, serverIp, channel));
    }

    /**
     * 连接关闭
     *
     * @param channel 连接通道
     */
    public void closeChannel(Channel channel) {
        taskExecutor.execute(new CloseChannelThread(channel));
    }

}

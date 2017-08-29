package com.madiot.poker.connect.netty;

import com.madiot.poker.connect.decoder.PokerDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenjie
 */
public class SocketService extends Thread {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketService.class);

    private static final int READ_TIMEOUT = 240;

    /**
     * 端口
     */
    private int port;

    /**
     * 构造方法
     *
     * @param port
     *          服务启动端口
     */
    public SocketService(int port) {
        this.port = port;
    }

    /**
     * 创建监听服务
     *
     * @throws Exception
     */
    public void run() {
        // 默认为cpu*2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("frameDecoder", new PokerDecoder());
                    pipeline.addLast("decoder", new ByteArrayDecoder());
                    pipeline.addLast("encoder", new ByteArrayEncoder());
                    pipeline.addLast("idleState", new IdleStateHandler(READ_TIMEOUT, 0, 0));
                    pipeline.addLast("dispatcher", new ServiceDispatcher());
                }
            });
            ChannelFuture f = b.bind(port).sync();
            LOGGER.debug("Netty cluster starting...port:" + port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

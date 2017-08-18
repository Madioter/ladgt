/**
 * @Title: ProcessFactory.java
 * @Package com.igdata.socket.connect.process
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/2/21
 * @version
 */
package com.madioter.poker.connect.process;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ProcessFactory
 * @Description: 处理流程分类
 * @author Yi.Wang2
 * @date 2017/2/21
 */
public class ProcessFactory {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessFactory.class);

    /**
     * 指令分发
     * @param command 指令数据
     * @param ctx 通道信息
     * @return 指令执行类
     */
    public static IProcess dispatcher(byte[] command, ChannelHandlerContext ctx) {
        return null;
    }
}

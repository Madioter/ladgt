/**
 * @Title: NettyService.java
 * @Package com.igdata.socket.connect.netty
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/3/8
 * @version
 */
package com.madioter.poker.connect.netty;

import com.madioter.common.utils.config.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @ClassName: NettyService
 * @Description: netty服务交给spring容器启动
 * @author Yi.Wang2
 * @date 2017/3/8
 */
@Service
public class NettyService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyService.class);

    @PostConstruct
    public void init() {
        LOGGER.debug("Init server thread");
        int port = ConfigUtil.getInt("gb.server.port", 4431);
        SocketService service = new SocketService(port);
        service.start();
    }
}

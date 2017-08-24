package com.madiot.poke.codec.common;

import com.madiot.common.utils.config.ConfigUtil;
import com.madiot.common.utils.http.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 消息的messageId创建类
 * 参考地址：http://www.dengchuanhua.com/132.html
 * Created by Yi.Wang2 on 2016/8/26.
 */
public class IndexCreator {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexCreator.class);

    /**
     * 工作对象
     */
    private static IndexCreator worker;

    /**
     * 参考配置格式： system.ip.mapper.127.0.0.1=1
     */
    static {
        List<String> ips = IPUtil.getAllLocalHostIp();
        Integer id = null;
        for (String ip : ips) {
            id = ConfigUtil.getInt("system.ip.mapper." + ip, null);
            if (id != null) {
                break;
            }
        }
        if (id != null) {
            worker = new IndexCreator(id);
        } else {
            worker = new IndexCreator(0);
            //LOGGER.error("当前服务器的IP未配置对应的项目编号，配置格式参考：system.ip.mapper.127.0.0.1=1，编号请勿重复");
            //System.exit(-1);
        }
    }

    public static synchronized long getIndex() {
        return worker.nextId();
    }

    /**
     * ***********************参考Twitter的分布式自增ID算法Snowflake实现***************************
     */

    private final long workerId;
    private final static long TWEPOCH = 1361753741828L;
    private long sequence = 0L;
    private final static long WORKER_ID_BITS = 4L;
    public final static long MAX_WORKER_ID = -1L ^ -1L << WORKER_ID_BITS;
    private final static long SEQUENCE_BITS = 10L;

    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    public final static long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;

    private long lastTimestamp = -1L;

    public IndexCreator(final long workerId) {
        super();
        if (workerId > this.MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    this.MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.SEQUENCE_MASK;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            LOGGER.error(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    this.lastTimestamp - timestamp));
        }

        this.lastTimestamp = timestamp;
        return ((timestamp - TWEPOCH << TIMESTAMP_LEFT_SHIFT))
                | (this.workerId << this.WORKER_ID_SHIFT) | (this.sequence);
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}

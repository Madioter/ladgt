/**
 * @Title: GBChannelInfo.java
 * @Package com.igdata.gb.connect.dto
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/3/20
 * @version
 */
package com.madioter.poker.connect.dto;

import com.madioter.common.utils.config.ConfigUtil;
import io.netty.channel.Channel;

/**
 * @ClassName: ChannelInfo
 * @Description: 连接信息
 * @author Yi.Wang2
 * @date 2017/3/20
 */
public class ChannelInfo {

    /**
     * 心跳超时时间，单位：秒
     */
    private static final int TIME_OUT_SECOND = ConfigUtil.getInt("tbox.connect.timeout.second", 240);

    /**
     * 长连接通道
     */
    private Channel channel;

    /**
     * 上次心跳时间
     */
    private long lastHeartBeatTime;


    /**
     * 车辆唯一标识

     */
    private String vin;

    /**
     * 无参构造方法：设置当前时间为心跳时间
     */
    public ChannelInfo() {
        this.lastHeartBeatTime = System.currentTimeMillis();
    }

    /**
     * 构造函数
     * @param channel 长连接通道
     */
    public ChannelInfo(Channel channel) {
        this.channel = channel;
        this.lastHeartBeatTime = System.currentTimeMillis();
    }

    public Channel getChannel() {
        return channel;
    }

    /**
     * 重置心跳时间
     */
    public void resetLastHeartBeatTime() {
        this.lastHeartBeatTime = System.currentTimeMillis();
    }

    /**
     * 判断是否连接超时
     */
    public boolean isTimeout() {
        if (System.currentTimeMillis() - lastHeartBeatTime > TIME_OUT_SECOND * 1000) {
            return true;
        }
        return false;
    }


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}

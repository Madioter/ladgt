/**
 * @Title: ConnectInfoDO.java
 * @Package com.madiot.common.redis.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.common.redis.model;

/**
 * @ClassName: ConnectInfoDO
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class ConnectInfoDO {

    private Integer playerId;

    private Integer roundIndex;

    private String serverIp;

    public ConnectInfoDO(Integer playerId, String serverIp) {
        this.playerId = playerId;
        this.serverIp = serverIp;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getRoundIndex() {
        return roundIndex;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setRoundIndex(Integer roundIndex) {
        this.roundIndex = roundIndex;
    }
}

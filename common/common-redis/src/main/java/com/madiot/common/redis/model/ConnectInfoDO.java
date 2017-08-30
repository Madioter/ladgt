/**
 * @Title: ConnectInfoDO.java
 * @Package com.madiot.common.redis.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.common.redis.model;

import com.madiot.poker.common.domain.IPlayer;

/**
 * @ClassName: ConnectInfoDO
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class ConnectInfoDO {

    private IPlayer player;

    private Integer roundIndex;

    private String serverIp;

    public ConnectInfoDO(IPlayer player, String serverIp) {
        this.player = player;
        this.serverIp = serverIp;
    }

    public Integer getPlayerId() {
        return player.getId();
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

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}

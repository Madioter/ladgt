/**
 * @Title: RoundInfoDO.java
 * @Package com.madiot.common.redis.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 * @version
 */
package com.madiot.common.redis.model;

import java.util.List;

/**
 * @ClassName: RoundInfoDO
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/24
 */
public class RoundInfoDO {

    private List<Integer> players;

    private Integer roundIndex;

    public RoundInfoDO(Integer roundIndex, List<Integer> players) {
        this.roundIndex = roundIndex;
        this.players = players;
    }

    public List<Integer> getPlayers() {
        return players;
    }

    public Integer getRoundIndex() {
        return roundIndex;
    }
}

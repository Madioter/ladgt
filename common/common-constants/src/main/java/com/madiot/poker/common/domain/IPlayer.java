/**
 * @Title: IPlayer.java
 * @Package com.madiot.poke.context.api
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poker.common.domain;

/**
 * @ClassName: IPlayer
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public interface IPlayer {

    String getName();

    Integer getId();

    int getLastScore();

    void calcScore(int score);

    String getServerIp();
}

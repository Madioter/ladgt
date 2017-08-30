package com.madiot.poke.context.model;

import com.madiot.common.redis.model.ConnectInfoDO;
import com.madiot.poker.common.domain.IPlayer;

/**
 * Created by julian on 2017/8/30.
 */
public class LadgtPlayer implements IPlayer {

    private ConnectInfoDO connectInfoDO;

    public LadgtPlayer(ConnectInfoDO connectInfoDO) {
        this.connectInfoDO = connectInfoDO;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public int getLastScore() {
        return 0;
    }

    @Override
    public void calcScore(int score) {

    }

    @Override
    public String getServerIp() {
        return null;
    }
}

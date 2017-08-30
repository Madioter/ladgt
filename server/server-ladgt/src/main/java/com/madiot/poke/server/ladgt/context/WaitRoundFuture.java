package com.madiot.poke.server.ladgt.context;

import com.madiot.poker.common.future.CallbackFuture;


/**
 * Created by julian on 2017/8/30.
 */
public class WaitRoundFuture extends CallbackFuture<Integer> {

    private Integer playerId;

    public WaitRoundFuture(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getPlayerId() {
        return playerId;
    }
}

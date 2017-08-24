/**
 * @Title: CurrentState.java
 * @Package com.madiot.poke.codec.ladgt.notices.upstream
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/22
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.upstream;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.poke.codec.ladgt.model.LadgtPlayer;
import com.madiot.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: CurrentState
 * @Description: 获取当前状态
 * @author Yi.Wang2
 * @date 2017/8/22
 */
public class CurrentState implements INoticeData {

    private INoticeResult result;

    private Integer playerId;

    private ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);

    private int deskIndex;

    private ListType<LadgtPlayer> players = new ListType<>(LadgtPlayer.class);

    public CurrentState(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {

    }

    @Override
    public void encode(ByteBuffer buffer) {

    }
}

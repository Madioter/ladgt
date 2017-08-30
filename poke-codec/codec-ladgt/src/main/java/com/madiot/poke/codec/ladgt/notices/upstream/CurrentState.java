/**
 * @Title: CurrentState.java
 * @Package com.madiot.poke.codec.ladgt.notices.upstream
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/22
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.upstream;

import com.madiot.common.utils.bytes.ByteUtils;
import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.poke.codec.ladgt.model.LadgtPlayer;
import com.madiot.common.utils.bytes.ByteBuffer;

/**
 * @author Yi.Wang2
 * @ClassName: CurrentState
 * @Description: 获取当前状态
 * @date 2017/8/22
 */
public class CurrentState implements INoticeData {

    private INoticeResult result;

    private ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);

    private int roundIndex;

    private ListType<LadgtPlayer> players = new ListType<>(LadgtPlayer.class);

    public CurrentState(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.SUCCESS) {
            this.cards.decode(buffer);
            this.roundIndex = ByteUtils.bytesToInt(buffer.read(4));
            this.players.decode(buffer);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.SUCCESS) {
            this.cards.encode(buffer);
            buffer.write(ByteUtils.intToBytes(this.roundIndex, 4));
            this.players.encode(buffer);
        }

    }

    public ListType<LadgtCard> getCards() {
        return cards;
    }

    public void setCards(ListType<LadgtCard> cards) {
        this.cards = cards;
    }

    public int getRoundIndex() {
        return roundIndex;
    }

    public void setRoundIndex(int roundIndex) {
        this.roundIndex = roundIndex;
    }

    public ListType<LadgtPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ListType<LadgtPlayer> players) {
        this.players = players;
    }
}

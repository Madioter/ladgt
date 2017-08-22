/**
 * @Title: NoticeDiscard.java
 * @Package com.madiot.poke.codec.ladgt.notices
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.notices;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madioter.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: NoticeDiscard
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class NoticeDiscard implements INoticeData {

    private INoticeResult result;

    private int playerId;

    private ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);

    public NoticeDiscard(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (this.result == LadgtNoticeResultEnum.COMMAND) {
            this.playerId = buffer.readInt();
            this.cards.decode(buffer);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (this.result == LadgtNoticeResultEnum.COMMAND) {
            buffer.writeInt(this.playerId);
            this.cards.encode(buffer);
        }
    }

    public ListType<LadgtCard> getCards() {
        return this.cards;
    }

    public void setCards(ListType<LadgtCard> cards) {
        this.cards = cards;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}

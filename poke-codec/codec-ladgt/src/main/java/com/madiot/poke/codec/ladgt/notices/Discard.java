/**
 * @Title: Discard.java
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
 * @ClassName: Discard
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class Discard implements INoticeData {

    private INoticeResult result;

    private ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);

    public Discard(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            cards.decode(buffer);
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (result == LadgtNoticeResultEnum.COMMAND) {
            cards.encode(buffer);
        }
    }

    public ListType<LadgtCard> getCards() {
        return cards;
    }

    public void setCards(ListType<LadgtCard> cards) {
        this.cards = cards;
    }
}

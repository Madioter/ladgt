/**
 * @Title: Discard.java
 * @Package com.madiot.poke.codec.ladgt.notices
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.poke.codec.ladgt.notices.downstream;

import com.madiot.poke.codec.api.INoticeData;
import com.madiot.poke.codec.api.INoticeResult;
import com.madiot.poke.codec.common.ListType;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: Discard
 * @Description: 由服务器通知出牌（带有上一手牌信息，如果为空，表示可以任意出牌）
 * 客户端响应出牌（信息中含有出牌内容，如果为空，表示pass）
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
        cards.decode(buffer);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        cards.encode(buffer);
    }

    public ListType<LadgtCard> getCards() {
        return cards;
    }

    public void setCards(ListType<LadgtCard> cards) {
        this.cards = cards;
    }
}

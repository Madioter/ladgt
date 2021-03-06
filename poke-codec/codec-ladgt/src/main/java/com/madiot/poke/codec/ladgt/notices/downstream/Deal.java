/**
 * @Title: Deal.java
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
import com.madiot.poke.codec.ladgt.LadgtNoticeResultEnum;
import com.madiot.poke.codec.ladgt.model.LadgtCard;
import com.madiot.poke.codec.ladgt.model.LadgtRoleEnum;
import com.madiot.common.utils.bytes.ByteBuffer;

/**
 * @ClassName: Deal
 * @Description: 服务器通知发牌内容，客户端接受后可以响应成功或不响应
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class Deal implements INoticeData {

    private INoticeResult result;

    private LadgtRoleEnum role;

    private LadgtCard landLordCard = new LadgtCard();

    private ListType<LadgtCard> cards = new ListType<>(LadgtCard.class);

    private int landlordPlayerId;

    public Deal(INoticeResult result) {
        this.result = result;
    }

    @Override
    public void decode(ByteBuffer buffer) {
        if (this.result == LadgtNoticeResultEnum.COMMAND) {
            this.role = LadgtRoleEnum.get(buffer.readInt());
            this.landLordCard.decode(buffer);
            this.cards.decode(buffer);
            this.landlordPlayerId = buffer.readInt();
        }
    }

    @Override
    public void encode(ByteBuffer buffer) {
        if (this.result == LadgtNoticeResultEnum.COMMAND) {
            buffer.writeInt(this.role.getCode());
            this.landLordCard.encode(buffer);
            this.cards.encode(buffer);
            buffer.writeInt(this.landlordPlayerId);
        }
    }

    public LadgtRoleEnum getRole() {
        return role;
    }

    public void setRole(LadgtRoleEnum role) {
        this.role = role;
    }

    public ListType<LadgtCard> getCards() {
        return cards;
    }

    public void setCards(ListType<LadgtCard> cards) {
        this.cards = cards;
    }

    public LadgtCard getLandLordCard() {
        return landLordCard;
    }

    public void setLandLordCard(LadgtCard landLordCard) {
        this.landLordCard = landLordCard;
    }

    public int getLandlordPlayerId() {
        return landlordPlayerId;
    }

    public void setLandlordPlayerId(int landlordPlayerId) {
        this.landlordPlayerId = landlordPlayerId;
    }
}

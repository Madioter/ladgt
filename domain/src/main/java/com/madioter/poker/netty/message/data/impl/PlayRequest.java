package com.madioter.poker.netty.message.data.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.common.PokerTypeEnum;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.netty.message.common.enums.PokerCard;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.impl.ByteType;
import com.madioter.poker.netty.message.type.impl.DataListType;
import com.madioter.poker.netty.message.type.impl.EnumType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 2017/5/16.
 */
public class PlayRequest implements IMessageData {

    private DataListType<PokerCard> pokerCards = DataListType.newInstance(PokerCard.class);

    @Override
    public byte[] getBytes() {
        ByteBuffer buffer = new ByteBuffer();
        encode(buffer);
        return buffer.getBytes();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        pokerCards.encode(buffer);
    }

    @Override
    public void decode(ByteBuffer code) {
        pokerCards.decode(code);
    }

    public DataListType<PokerCard> getPokerCards() {
        return pokerCards;
    }

    public void setPokerCards(DataListType<PokerCard> pokerCards) {
        this.pokerCards = pokerCards;
    }
}

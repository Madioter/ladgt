package com.madioter.poker.netty.message.common.enums;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.common.PokerTypeEnum;
import com.madioter.poker.common.PokerValueEnum;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.IDataListItem;
import com.madioter.poker.netty.message.type.impl.EnumType;

/**
 * Created by julian on 2017/5/16.
 */
public class PokerCard implements IMessageData,IDataListItem {

    /**
     * 花色
     */
    private EnumType<PokerTypeEnum> pokerType = EnumType.newInstance(PokerTypeEnum.class);

    /**
     * 大小
     */
    private EnumType<PokerValueEnum> pokerValue = EnumType.newInstance(PokerValueEnum.class);

    @Override
    public byte[] getBytes() {
        ByteBuffer buffer = new ByteBuffer();
        encode(buffer);
        return buffer.getBytes();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        pokerType.encode(buffer);
        pokerValue.encode(buffer);
    }

    @Override
    public void decode(ByteBuffer code) {
        pokerType.decode(code);
        pokerValue.decode(code);
    }

    public EnumType<PokerTypeEnum> getPokerType() {
        return pokerType;
    }

    public void setPokerType(EnumType<PokerTypeEnum> pokerType) {
        this.pokerType = pokerType;
    }

    public EnumType<PokerValueEnum> getPokerValue() {
        return pokerValue;
    }

    public void setPokerValue(EnumType<PokerValueEnum> pokerValue) {
        this.pokerValue = pokerValue;
    }
}

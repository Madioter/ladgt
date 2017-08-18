package com.madioter.poker.netty.message.data.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.impl.WordType;

/**
 * Created by julian on 2017/5/16.
 */
public class JoinRequest implements IMessageData{

    /**
     * 桌号 (不带桌号为默认分配),(默认值：0xFF,0xFF）
     */
    private WordType deskId = new WordType();

    public WordType getDeskId() {
        return deskId;
    }

    public void setDeskId(WordType deskId) {
        this.deskId = deskId;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer buffer = new ByteBuffer();
        encode(buffer);
        return buffer.getBytes();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        deskId.encode(buffer);
    }

    @Override
    public void decode(ByteBuffer code) {
        deskId.decode(code);
    }
}

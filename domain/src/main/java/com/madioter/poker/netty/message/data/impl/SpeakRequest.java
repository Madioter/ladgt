package com.madioter.poker.netty.message.data.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.impl.ByteType;
import com.madioter.poker.netty.message.type.impl.DataListType;

/**
 * Created by julian on 2017/5/16.
 */
public class SpeakRequest implements IMessageData {

    /**
     * 说话内容
     */
    private DataListType<ByteType> sentences = DataListType.newInstance(ByteType.class);

    @Override
    public byte[] getBytes() {
        ByteBuffer buffer = new ByteBuffer();
        encode(buffer);
        return buffer.getBytes();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        sentences.encode(buffer);
    }

    @Override
    public void decode(ByteBuffer code) {
        sentences.decode(code);
    }
}

package com.madioter.poker.netty.message.data.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.netty.message.common.enums.CallTypeEnum;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.impl.EnumType;

/**
 * Created by julian on 2017/5/16.
 */
public class CallLandloadResponse implements IMessageData {

    /**
     * 是否叫地主
     */
    private EnumType<CallTypeEnum> callType = EnumType.newInstance(CallTypeEnum.class);

    public EnumType<CallTypeEnum> getCallType() {
        return callType;
    }

    public void setCallType(EnumType<CallTypeEnum> callType) {
        this.callType = callType;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer buffer = new ByteBuffer();
        encode(buffer);
        return buffer.getBytes();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        callType.encode(buffer);
    }

    @Override
    public void decode(ByteBuffer code) {
        callType.decode(code);
    }
}

/**
 * @Title: ApplicationDataWapper.java
 * @Package com.igdata.gbparser.vo.data
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/3/23
 * @version
 */
package com.madioter.poker.netty.message.data.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.poker.netty.message.common.enums.MessageTypeEnum;
import com.madioter.poker.netty.message.data.IMessageData;
import com.madioter.poker.netty.message.type.impl.EnumType;

/**
 * @author Yi.Wang2
 * @ClassName: ApplicationDataWrapper
 * @Description: application data 接口类
 * @date 2017/3/23
 */
public class MessageDataWrapper implements IMessageData {

    private byte[] source;

    private Integer length;

    private EnumType<MessageTypeEnum> messageType;

    public MessageDataWrapper(Integer length, EnumType<MessageTypeEnum> messageType) {
        this.length = length;
        this.messageType = messageType;
    }

    @Override
    public byte[] getBytes() {
        return source;
    }

    @Override
    public void decode(ByteBuffer code) {
        this.source = code.read(this.length);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.write(this.source);
    }
}

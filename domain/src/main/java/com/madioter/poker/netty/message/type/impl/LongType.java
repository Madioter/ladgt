/**
 * @Title: MessageType.java
 * @Package com.madioter.poker.netty.message.type.impl
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/5/11
 * @version
 */
package com.madioter.poker.netty.message.type.impl;

import com.madioter.common.utils.bytes.ByteUtils;
import com.madioter.poker.netty.message.common.constants.MessageConstants;
import com.madioter.poker.netty.message.type.ByteArrayType;

/**
 * @ClassName: LongType
 * @Description: 长整型的数据
 * @author Yi.Wang2
 * @date 2017/5/11
 */
public class LongType extends ByteArrayType {

    private long data;

    public LongType() {
        super(MessageConstants.LONG_SIZE);
    }

    @Override
    public byte[] getBytes() {
        return ByteUtils.longToBytes(data, MessageConstants.LONG_SIZE);
    }

    @Override
    public void setData(byte[] data) {
        this.data = ByteUtils.bytesToLong(data);
    }
}

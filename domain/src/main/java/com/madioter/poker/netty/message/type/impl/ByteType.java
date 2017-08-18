/**
 * @Title: ByteType.java
 * @Package common.type
 * @Description: 单字节数据
 * @author Yi.Wang2
 * @date 2017/2/10
 * @version
 */
package com.madioter.poker.netty.message.type.impl;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.common.utils.bytes.ByteUtils;
import com.madioter.poker.netty.message.common.constants.MessageConstants;
import com.madioter.poker.netty.message.type.ByteArrayType;
import com.madioter.poker.netty.message.type.IIntAble;
import com.madioter.poker.netty.message.type.ISizeAble;

/**
 * @ClassName: ByteType
 * @Description: 单字节数据
 * @author Yi.Wang2
 * @date 2017/2/10
 */
public class ByteType extends ByteArrayType implements ISizeAble,IIntAble {

    /**
     * 默认值为失效
     */
    private byte data = (byte) 0xFF;

    /**
     * 默认无参构造方法
     */
    public ByteType() {
        super(MessageConstants.BYTE_SIZE);
    }

    /**
     * 带默认值的构造方法
     * @param defaultValue 默认值（例如，默认值为0）
     */
    public ByteType(byte defaultValue) {
        super(MessageConstants.BYTE_SIZE);
        this.data = defaultValue;
    }

    /**
     * 带byte数据的构造方法
     * @param code byte数据
     */
    public ByteType(ByteBuffer code) {
        super(code, MessageConstants.BYTE_SIZE);
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{data};
    }

    @Override
    public void setData(byte[] data) {
        if (data == null || data.length == 0) {
            this.data = 0x00;
        } else {
            this.data = data[0];
        }
    }

    @Override
    public int getSize() {
        return MessageConstants.BYTE_SIZE;
    }

    /**
     * 获取int数据值（无符号，0-255）
     * @return Integer结果
     */
    public Integer getInt() {
        // 去除ByteUtils的符号位导致的影响
        return ByteUtils.bytesToInt(new byte[]{data});
    }

    /**
     * 设置Integer数据值，仅解析单个byte位
     * @param value byte数据值
     */
    public void setInt(int value) {
        setData(ByteUtils.intToBytes(value, MessageConstants.BYTE_SIZE));
    }
}

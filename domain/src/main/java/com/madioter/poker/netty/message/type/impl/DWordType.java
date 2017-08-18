/**
 * @Title: DWordData.java
 * @Package common.type
 * @Description: DWord 数据类型
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
 * @author Yi.Wang2
 * @ClassName: DWordData
 * @Description: DWord 数据类型 ，4字节
 * @date 2017/2/10
 */
public class DWordType extends ByteArrayType implements ISizeAble, IIntAble {

    /**
     * 存放4个字节数据
     */
    private byte[] data;

    /**
     * 无参构造方法
     */
    public DWordType() {
        super(MessageConstants.DWORD_SIZE);
    }

    /**
     * 带默认值的参数构造方法
     *
     * @param defaultValue 默认值
     */
    public DWordType(byte[] defaultValue) {
        super(MessageConstants.DWORD_SIZE);
        this.data = defaultValue;
    }

    /**
     * 带解码功能的构造方法
     *
     * @param code byte数据
     */
    public DWordType(ByteBuffer code) {
        super(code, MessageConstants.DWORD_SIZE);
    }

    @Override
    public byte[] getBytes() {
        return ByteUtils.strictSize(data, getSize());
    }

    @Override
    public void setData(byte[] data) {
        this.data = ByteUtils.strictSize(data, getSize());
    }

    @Override
    public int getSize() {
        return MessageConstants.DWORD_SIZE;
    }

    /**
     * 获取Int数值，无符号，4位数据
     *
     * @return 数值
     */
    public Integer getInt() {
        return ByteUtils.bytesToInt(getBytes());
    }

    /**
     * 获取Long类型数值，无符号，4位数据，Integer类型的最高位会默认为符号为，这里增加Long类型数据获取，对于过大的数据，使用此方法获取
     *
     * @return Long类型数据
     */
    public Long getLong() {
        return ByteUtils.bytesToLong(getBytes());
    }

    /**
     * 设置数据
     *
     * @param value 数值
     */
    public void setInt(int value) {
        setData(ByteUtils.intToBytes(value, MessageConstants.DWORD_SIZE));
    }
}

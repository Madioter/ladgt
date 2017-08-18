/**
 * @Title: WordData.java
 * @Package common.type
 * @Description: TODO
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
 * @ClassName: WordData
 * @Description: 两字节数据结构
 * @author Yi.Wang2
 * @date 2017/2/10
 */
public class WordType extends ByteArrayType implements ISizeAble,IIntAble {

    /**
     * 数据，默认为无效值：0xFF, 0xFF
     */
    private byte[] data = new byte[]{(byte) 0xFF, (byte) 0xFF};

    /**
     * 无参构造方法
     */
    public WordType() {
        super(MessageConstants.WORD_SIZE);
    }

    /**
     * 带默认值的构造方法
     * @param defaultValue 默认值
     */
    public WordType(byte[] defaultValue) {
        super(MessageConstants.WORD_SIZE);
        this.data = defaultValue;
    }

    /**
     * 带数据的构造方法
     * @param code byte数据
     */
    public WordType(ByteBuffer code) {
        super(code, MessageConstants.WORD_SIZE);
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
        return MessageConstants.WORD_SIZE;
    }

    /**
     * 获取int类型的数据值
     * @return Integer数据
     */
    public Integer getInt() {
        return ByteUtils.bytesToInt(getBytes());
    }

    /**
     * 设置INT数值
     * @param value int数据
     */
    public void setInt(int value) {
        setData(ByteUtils.intToBytes(value, MessageConstants.WORD_SIZE));
    }
}

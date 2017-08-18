/**
 * @Title: ByteArrayType.java
 * @Package common.type
 * @Description: 数据结构基类
 * @author Yi.Wang2
 * @date 2017/2/10
 * @version
 */
package com.madioter.poker.netty.message.type;

import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.common.utils.bytes.ByteUtils;
import com.madioter.poker.netty.message.common.validate.IValid;
import com.madioter.poker.netty.message.common.validate.InvalidException;

/**
 * @ClassName: ByteArrayType
 * @Description: 数据结构基类
 * @author Yi.Wang2
 * @date 2017/2/10
 */
public abstract class ByteArrayType implements IDataListItem, IBytesAble, ISizeAble {

    /**
     * 长度
     */
    private int size;

    /**
     * 有效值范围
     */
    protected IValid valid;

    /**
     * 构造方法
     * @param size 数据长度，占用几个byte
     */
    public ByteArrayType(int size) {
        this.size = size;
    }

    /**
     * 构造方法
     * @param code byte数据
     * @param size 数据长度
     */
    public ByteArrayType(ByteBuffer code, int size) {
        this.size = size;
        decode(code);
    }

    @Override
    public void decode(ByteBuffer code) {
        setData(code.read(getSize()));
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.write(ByteUtils.strictSize(getBytes(), getSize()));
    }

    /**
     * 获取byte数据
     * @return byte数组
     */
    public abstract byte[] getBytes();

    /**
     * 设置byte数据
     * @param data byte数组
     */
    public abstract void setData(byte[] data);

    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * 判断数据是否有效
     * @return true：有效，false：无效
     */
    public boolean isValid() throws InvalidException {
        return this.valid == null || this.valid.isValid(this);
    }

    /**
     * 设置有效值验证
     * @param valid 验证
     */
    public void setValid(IValid valid) {
        this.valid = valid;
    }
}

/**
 * @Title: EnumType.java
 * @Package common.type
 * @Description: 枚举类型数据
 * @author Yi.Wang2
 * @date 2017/2/10
 * @version
 */
package com.madioter.poker.netty.message.type.impl;


import com.madioter.common.utils.bytes.ByteBuffer;
import com.madioter.common.utils.bytes.ByteUtils;
import com.madioter.poker.netty.message.common.enums.EnumUtils;
import com.madioter.poker.netty.message.common.enums.IEnum;
import com.madioter.poker.netty.message.type.ISetValueAble;

/**
 * @author Yi.Wang2
 * @ClassName: EnumType
 * @Description: 枚举类型数据，允许指定对象对应的枚举类型，并允许枚举类型之外的值作为实际值
 * @date 2017/2/10
 */
public class EnumType<E extends IEnum> extends ByteType implements ISetValueAble<Integer> {

    /**
     * 对象的构造方法，用于快速构造对象
     *
     * @param clz 枚举类型
     * @param <T> 本工程中的所有枚举都需要继承IEnum，这里才可以正常使用
     * @return 枚举类型对象
     */
    public static <T extends IEnum> EnumType<T> newInstance(Class<T> clz) {
        return new EnumType<T>(clz);
    }

    /**
     * 枚举类型
     */
    private Class<E> enumType;

    /**
     * 构造函数
     *
     * @param enumType 枚举类型
     */
    public EnumType(Class<E> enumType) {
        this.enumType = enumType;
    }

    /**
     * 带解析的构造函数
     *
     * @param enumType 枚举类型
     * @param buffer   byte数据
     */
    public EnumType(Class<E> enumType, ByteBuffer buffer) {
        super(buffer);
        this.enumType = enumType;
    }

    @Override
    public void setValue(Integer value) {
        setData(ByteUtils.intToBytes(value, 1));
    }

    /**
     * 设置枚举类型
     *
     * @param e 枚举对象
     */
    public void setValue(E e) {
        setValue(e.getCode());
    }

    /**
     * 获取枚举对象
     *
     * @return 枚举对象
     */
    public E getEnum() {
        try {
            return EnumUtils.get(enumType, getInt());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        E enumValue;
        try {
            enumValue = EnumUtils.get(enumType, getInt());
        } catch (Exception e) {
            enumValue = null;
        }
        if (enumValue != null) {
            return enumValue.getName();
        }
        return String.valueOf(getInt());
    }
}

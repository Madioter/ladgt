/**
 * @Title: StringType.java
 * @Package common.type
 * @Description: 字符对象
 * @author Yi.Wang2
 * @date 2017/2/10
 * @version
 */
package com.madioter.poker.netty.message.type.impl;

import com.madioter.poker.common.exception.ParserException;
import com.madioter.poker.netty.message.common.constants.MessageConstants;
import com.madioter.poker.netty.message.type.ByteArrayType;
import com.madioter.poker.netty.message.type.ISetValueAble;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @ClassName: StringType
 * @Description: 字符对象
 * @author Yi.Wang2
 * @date 2017/2/10
 */
public class StringType extends ByteArrayType implements ISetValueAble<String> {

    /**
     * 默认的字符数据
     */
    private String string = "";

    /**
     * 带长度限制的字符对象
     * @param size 字符长度限制
     */
    public StringType(int size) {
        super(size);
    }

    /**
     * 带长度限制的字符对象
     * @param str 字符串
     */
    public StringType(String str) {
        super(str.length());
        setValue(str);
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes;
        try {
            bytes = string.getBytes(MessageConstants.DEFAULT_CHARACTER_SET);
        } catch (UnsupportedEncodingException e) {
            throw new ParserException(String.format("字符串编码异常，字符串：%s,编码格式：%s", string, MessageConstants.DEFAULT_CHARACTER_SET), e);
        }
        if (bytes.length > getSize()) {
            return Arrays.copyOfRange(bytes, 0, getSize());
        } else {
            byte[] newByte = new byte[getSize()];
            for (int i = 0; i < bytes.length; i++) {
                newByte[i] = bytes[i];
            }
            return newByte;
        }
    }

    @Override
    public void setData(byte[] data) {
        try {
            this.string = new String(data, MessageConstants.DEFAULT_CHARACTER_SET);
            if (this.string.length() > getSize()) {
                this.string = this.string.substring(0, getSize());
            }
        } catch (UnsupportedEncodingException e) {
            throw new ParserException(String.format("字符串编码异常，字符串：%s,编码格式：%s", string, MessageConstants.DEFAULT_CHARACTER_SET), e);
        }
    }

    @Override
    public void setValue(String string) {
        this.string = string;
        if (this.string.length() > getSize()) {
            this.string = this.string.substring(0, getSize());
        }
    }

    /**
     * 获取字符串数据
     * @return 字符串数据
     */
    public String getString() {
        return string;
    }

    @Override
    public String toString() {
        return string.trim();
    }
}
